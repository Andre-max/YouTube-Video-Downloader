package com.github.kotvertolet.youtubejextractor.utils

import com.github.kotvertolet.youtubejextractor.exception.SignatureDecryptionException
import com.google.code.regexp.Matcher
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable
import java.util.*

class DecryptionUtils(private val playerJsCode: String, functionNameToExtract: String) {
    // Includes function declaration and it's assignment
    private val jsDecryptFunction: String

    // Includes only function body
    private val jsDecryptFunctionBody: String
    private var jsObjects: ArrayList<String>
    private val jsContext: Context
    private var scope: Scriptable? = null

    @Throws(SignatureDecryptionException::class)
    fun decryptSignature(encryptedSignature: String): String {
        val result = jsContext.evaluateString(
            scope,
            String.format("%s('%s')", jsDecryptFunction, encryptedSignature),
            "",
            0,
            null
        )
        return (result as? String)?.toString()
            ?: throw SignatureDecryptionException(
                """Decryption function returned no result, function was: 
$jsDecryptFunction
 parameter was: $encryptedSignature
js objects were: $jsObjects"""
            )
    }

    /**
     * Creates JS context with all objects and functions to execute decryption function in future
     *
     * @param jsObjects js objects that are referenced in a decryption function
     * @return JS context with all objects and functions to execute decryption function
     */
    private fun prepareJsContext(jsObjects: List<String>): Context {
        val jsContext = Context.enter()
        jsContext.optimizationLevel = -1
        scope = jsContext.initStandardObjects()
        for (jsObject in jsObjects) {
            jsContext.evaluateString(scope, jsObject, "", 0, null)
        }
        return jsContext
    }

    /**
     * Extracts decryption js function from youtube player code, later it is used to extract
     * it's arguments names, function body, etc
     *
     * @param funcName name of the function to extract
     * @return matcher that points to the required function
     */
    @Throws(SignatureDecryptionException::class)
    private fun extractJsFunction(funcName: String): Matcher? {
        val escapedFuncName = StringUtils.escapeRegExSpecialCharacters(funcName)
        val stringPattern = String.format(
            "(?x)(?:function\\s+%s|[{;,]\\s*%s\\s*=\\s*function|var" +
                    "\\s+%s\\s*=\\s*function)\\s*\\((?<args>[^)]*)\\)\\s*\\{(?<code>[^}]+)\\}",
            escapedFuncName, escapedFuncName, escapedFuncName
        )
        val matcher = CommonUtils.getMatcher(stringPattern, playerJsCode)
        return if (!matcher.find()) {
            throw SignatureDecryptionException("Could not find JS function with name $funcName")
        } else {
            matcher
        }
    }

    /**
     * Extracts whole function, including it's assignment and body
     *
     * @param matcher matcher that point to the function
     * @return returns function with it's assignment, for example: var a = function(b) {...}
     */
    private fun extractFunctionWithAssignment(matcher: Matcher?): String {
        var extractedFunction = matcher!!.group()
        if (extractedFunction.startsWith(";\n")) {
            extractedFunction = extractedFunction.replace(";\n", "")
        }
        return extractedFunction
    }

    /**
     * Extracts arguments names from the function
     *
     * @param matcher matcher that point to the function
     * @return returns list with args names
     */
    private fun extractFunctionArgs(matcher: Matcher?): List<String> {
        return Arrays.asList(*matcher!!.group("args").split(",".toRegex()).toTypedArray())
    }

    /**
     * Extracts function body (everything inside of {})
     *
     * @param matcher matcher that point to the function
     * @return function body
     */
    private fun extractFunctionBody(matcher: Matcher?): String {
        return matcher!!.group("code")
    }

    /**
     * Js decryption function contains method calls from other objects inside of the player code,
     * this method is meant to extract them in order to use decryption function
     *
     * @param functionCode Decrypt function extracted from player code
     * @param argNames     Decrypt function args names
     * @return List of extracted objects
     */
    @Throws(SignatureDecryptionException::class)
    private fun extractJsObjectsIfAny(
        functionCode: String,
        argNames: List<String>
    ): ArrayList<String> {
        var matcher: Matcher?
        val expressionsArr = functionCode.split(";".toRegex()).toTypedArray()
        val objectFieldsAndStatements = HashMap<String?, String>()
        val charsAndDigitsMask = "[a-zA-Z_$][a-zA-Z_$0-9]*"
        val stringPattern = String.format(
            "(?<var>%s)(?:\\.(?<member>[^(]+)|" +
                    "\\[(?<member2>[^]]+)\\])\\s*", charsAndDigitsMask
        )
        for (expression in expressionsArr) {
            matcher = CommonUtils.getMatcher(stringPattern, expression)
            if (matcher.find()) {
                val variable = matcher.group("var")
                var member = matcher.group("member")
                if (member == null) {
                    member = matcher.group("member2")
                }
                if (argNames.contains(variable)) {
                    continue
                } else {
                    if (!objectFieldsAndStatements.containsKey(member)) {
                        objectFieldsAndStatements.putAll(extractJsObject(variable))
                    }
                }
            }
        }
        return jsObjects
    }

    /**
     * Extracts js object by it's name from player code
     *
     * @param objectName name of the object to extract
     * @return map where key is object's field name and value is field's value
     */
    @Throws(SignatureDecryptionException::class)
    private fun extractJsObject(objectName: String): HashMap<String?, String> {
        val obj = HashMap<String?, String>()
        jsObjects = ArrayList()
        val funcNamePattern = "(?:[a-zA-Z$0-9]+|\"[a-zA-Z$0-9]+\"|'[a-zA-Z$0-9]+')"
        var stringPattern = String.format(
            "(?x)(?<!this\\.)%s\\s*=\\s*\\{\\s*" +
                    "(?<fields>(%s\\s*:\\s*function\\s*(.*?)\\s*\\{.*?\\}(?:,\\s*)?)*)\\}\\s*;",
            StringUtils.escapeRegExSpecialCharacters(objectName), funcNamePattern
        )
        var matcher = CommonUtils.getMatcher(stringPattern, playerJsCode)
        return if (matcher!!.find()) {
            jsObjects.add(matcher.group())
            val fields = matcher.group("fields")
            stringPattern = String.format(
                "(?x)(?<key>%s)\\s*:\\s*function\\s*" +
                        "\\((?<args>[a-z,]+)\\)\\{(?<code>[^}]+)\\}", funcNamePattern
            )
            matcher = CommonUtils.getMatcher(stringPattern, fields)
            while (matcher.find()) {
                obj[matcher.group("key")] = matcher.group("code")
            }
            obj
        } else {
            throw SignatureDecryptionException(
                String.format(
                    "Js object with name '%s' wasn't found",
                    objectName
                )
            )
        }
    }

    init {
        val rawExtractedFunction = extractJsFunction(functionNameToExtract)
        jsDecryptFunction = extractFunctionWithAssignment(rawExtractedFunction)
        jsDecryptFunctionBody = extractFunctionBody(rawExtractedFunction)
        val jsDecryptFunctionArgumentsNames = extractFunctionArgs(rawExtractedFunction)
        jsObjects = extractJsObjectsIfAny(jsDecryptFunctionBody, jsDecryptFunctionArgumentsNames)
        jsContext = prepareJsContext(jsObjects)
    }
}