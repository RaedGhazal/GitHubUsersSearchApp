package com.raedghazal.githubuserssearchapp.presentation.users

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raedghazal.githubuserssearchapp.domain.model.User
import com.raedghazal.githubuserssearchapp.domain.usecase.GetUsersUseCase
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    getUsersUseCase: GetUsersUseCase,
    state: SavedStateHandle
) :
    ViewModel() {

    companion object {
        private const val QUERY_TEXT = "query_text"
        private const val DEFAULT_QUERY = ""
    }

    private var _queryText: MutableLiveData<String> = state.getLiveData(QUERY_TEXT, DEFAULT_QUERY)

    private lateinit var usersList: MutableLiveData<List<User>>

    init {
        if (!this::usersList.isInitialized)
            usersList = MutableLiveData<List<User>>()
    }


    val users = _queryText.switchMap {
        getUsersUseCase.getUsersSearchResult(it).cachedIn(viewModelScope)
    }

    fun searchUsers(queryText: String) {
        _queryText.value = queryText
    }


}