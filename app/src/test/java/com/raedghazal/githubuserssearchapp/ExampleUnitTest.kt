package com.raedghazal.githubuserssearchapp

import com.google.common.truth.Truth.assertThat
import com.raedghazal.githubuserssearchapp.presentation.userdetails.UserDetailsFragment
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun shit(){
        assertThat("https://api.github.com/users/RaedGhazal/following/other_user".dropLast( "/other_user".length))
            .isEqualTo("https://api.github.com/users/RaedGhazal/following")
    }
}