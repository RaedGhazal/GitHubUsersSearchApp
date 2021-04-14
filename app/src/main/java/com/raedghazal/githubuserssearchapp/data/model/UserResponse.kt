package com.raedghazal.githubuserssearchapp.data.model

import com.raedghazal.githubuserssearchapp.domain.model.User

class UserResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<User>
) {

}