package com.raedghazal.githubuserssearchapp.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.raedghazal.githubuserssearchapp.data.model.UserResponse
import com.raedghazal.githubuserssearchapp.data.source.Database
import com.raedghazal.githubuserssearchapp.data.source.remote.RetrofitApi
import com.raedghazal.githubuserssearchapp.domain.model.User
import com.raedghazal.githubuserssearchapp.domain.repository.UserRepository
import com.raedghazal.githubuserssearchapp.presentation.users.UsersPagingSource
import io.reactivex.Single

class UserRepositoryImpl(
    private val retrofitApi: RetrofitApi,
    private val database: Database?,
    private val context: Context
) : UserRepository {

    override fun getUsersBySearchName(queryText: String, pageNumber: Int): Single<UserResponse> =
        retrofitApi.getUsers(queryText, pageNumber)


    override fun getSearchResults(queryText: String): LiveData<PagingData<User>> {
        if (queryText.isEmpty())
            return MutableLiveData()
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 1030,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UsersPagingSource(
                    retrofitApi = retrofitApi,
                    queryText = queryText,
                    context = context
                )
            }
        ).liveData
    }

    private fun checkNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        activeNetwork?.let {
            return activeNetwork.isConnected
        }
        return false
    }

    override fun getFavoriteUsers(): List<User> {
//        return database.dao.getAllUsers()
        TODO()
    }

    override fun addFavoriteUser(user: User) {
        TODO("Not yet implemented")
    }

    override fun removeFavoriteUser(user: User) {
        TODO("Not yet implemented")
    }

    override fun isFavorite(user: User): Boolean {
        TODO("Not yet implemented")
    }
}