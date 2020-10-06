package com.mvvm.loginregistrationwithkotlin.repository

import com.mvvm.loginregistrationwithkotlin.network.AuthApi
import com.mvvm.loginregistrationwithkotlin.preferences.UserPreferences

class  AuthRepository(
    private val api : AuthApi,
    private val preferences: UserPreferences
):BaseRepository(){
    suspend fun login(
        email : String ,
        password: String
    )= safeApiCall { api.login(email,password) }


    suspend fun saveAuthToken(token:String)
    {
        preferences.saveAuthToken(token)
    }
}