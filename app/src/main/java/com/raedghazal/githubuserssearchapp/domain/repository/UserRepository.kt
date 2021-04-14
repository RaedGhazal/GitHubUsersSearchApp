package com.raedghazal.githubuserssearchapp.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.raedghazal.githubuserssearchapp.data.model.UserResponse
import com.raedghazal.githubuserssearchapp.domain.model.User
import io.reactivex.Single

interface UserRepository {

    fun getUsersBySearchName(queryText: String, pageNumber: Int): Single<UserResponse>

    fun getSearchResults(queryText: String): LiveData<PagingData<User>>

    fun getFavoriteUsers(): List<User>

    fun addFavoriteUser(user: User)

    fun removeFavoriteUser(user: User)

    fun isFavorite(user: User): Boolean
}

