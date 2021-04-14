package com.raedghazal.githubuserssearchapp.data.source.remote

import com.raedghazal.githubuserssearchapp.data.model.UserResponse
import com.raedghazal.githubuserssearchapp.domain.model.User
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("/search/users")
    fun getUsers(
        @Query("q") q: String,
        @Query("page") pageNumber:Int
    ): Single<UserResponse>
}