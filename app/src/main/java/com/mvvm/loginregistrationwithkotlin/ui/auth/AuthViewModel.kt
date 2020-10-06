package com.mvvm.loginregistrationwithkotlin.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvvm.loginregistrationwithkotlin.network.Resource
import com.mvvm.loginregistrationwithkotlin.repository.AuthRepository
import com.mvvm.loginregistrationwithkotlin.repository.BaseRepository
import com.mvvm.loginregistrationwithkotlin.responces.LoginResponse
import com.mvvm.loginregistrationwithkotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthViewModel(
    private  val repository: AuthRepository
) : BaseViewModel(repository) {
    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.loading
        _loginResponse.value = repository.login(email, password)
    }

   suspend fun saveAuthToken(token: String)  {
        repository.saveAuthToken(token)
    }

}