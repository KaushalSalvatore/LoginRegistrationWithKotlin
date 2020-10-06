package com.mvvm.loginregistrationwithkotlin.ui.base

import androidx.lifecycle.ViewModel
import com.mvvm.loginregistrationwithkotlin.network.UserApi
import com.mvvm.loginregistrationwithkotlin.repository.BaseRepository

abstract class BaseViewModel(
    private val repository: BaseRepository
): ViewModel(){
suspend fun logout(api: UserApi)= repository.logout(api)
}