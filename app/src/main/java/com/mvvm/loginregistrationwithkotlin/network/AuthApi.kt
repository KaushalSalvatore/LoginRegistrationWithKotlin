package com.mvvm.loginregistrationwithkotlin.network

import com.mvvm.loginregistrationwithkotlin.responces.LoginResponse
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email:String ,
        @Field("password") password:String
    ):LoginResponse


    @FormUrlEncoded
    @POST("auth/signup")
    suspend fun signup(
        @Field("name") name:String ,
        @Field("email") email:String ,
        @Field("password") password:String,
        @Field("password_confirmation") confirmpaswword:String

    ):ResponseBody


}