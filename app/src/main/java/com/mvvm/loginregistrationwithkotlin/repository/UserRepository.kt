package com.mvvm.loginregistrationwithkotlin.repository

import com.mvvm.loginregistrationwithkotlin.network.UserApi

class UserRepository(
    private val api : UserApi
) : BaseRepository(){
    suspend fun getUser() = safeApiCall {
        api.getUser()
    }

}