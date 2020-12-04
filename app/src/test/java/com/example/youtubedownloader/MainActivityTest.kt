package com.example.youtubedownloader

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainActivityTest {

    @Test
    fun getVideoId_returnsId() {
        assertThat("HeLyHoJO-mI", `is`(("https://www.youtube.com/watch?v=HeLyHoJO-mI").getVideoId()))
    }

}