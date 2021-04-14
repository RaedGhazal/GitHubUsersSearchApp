package com.raedghazal.githubuserssearchapp.domain.usecase

import com.raedghazal.githubuserssearchapp.data.model.UserResponse
import com.raedghazal.githubuserssearchapp.domain.model.User
import com.raedghazal.githubuserssearchapp.domain.repository.UserRepository
import com.raedghazal.githubuserssearchapp.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: UserRepository) :
    SingleUseCase<UserResponse>() {

    override fun buildUseCaseSingle(queryText: String, pageNumber: Int): Single<UserResponse> =
        repository.getUsersBySearchName(queryText, pageNumber)


    fun getUsersSearchResult(queryText: String) = repository.getSearchResults(queryText)

    fun getFavoriteUsers(): List<User> = repository.getFavoriteUsers()

    fun addFavoriteUser(user: User) {
        repository.addFavoriteUser(user)
    }

    fun removeFavoriteUser(user: User) {
        repository.removeFavoriteUser(user)
    }

    fun isFavorite(user: User) {
        repository.isFavorite(user)
    }

}