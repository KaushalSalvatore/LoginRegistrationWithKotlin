package com.mvvm.loginregistrationwithkotlin.repository

import com.mvvm.loginregistrationwithkotlin.network.AuthApi

class RegistrationRepository(
    private val api : AuthApi
):BaseRepository() {
    suspend fun signup(
        name: String,
        email: String,
        password: String,
        confirmpassword: String
    )=safeApiCall { api.signup(name,email,password,confirmpassword) }

}