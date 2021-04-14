package com.raedghazal.githubuserssearchapp

import com.raedghazal.githubuserssearchapp.presentation.users.UsersPagingSource
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class UsersPagingSourceTest {

    @After
    fun reset() {
        UsersPagingSource.startingSecond = -1
    }

    @Test
    fun `test starting second true`() {
        UsersPagingSource.calculateDelay(19)
        val result = UsersPagingSource.startingSecond
        val expectedResult = 19
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `test Delay Calculator true`() {
        val listOfRequestSeconds = listOf<Long>(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
        val expectedListOfResults = listOf<Long>(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 51)
        val listOfResults = mutableListOf<Long>()
        listOfRequestSeconds.forEach {
            listOfResults.add(UsersPagingSource.calculateDelay(it))
        }
        assertThat(listOfResults).isEqualTo(expectedListOfResults)
    }
}