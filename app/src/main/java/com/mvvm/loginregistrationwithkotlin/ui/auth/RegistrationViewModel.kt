package com.mvvm.loginregistrationwithkotlin.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mvvm.loginregistrationwithkotlin.network.Resource
import com.mvvm.loginregistrationwithkotlin.repository.RegistrationRepository
import com.mvvm.loginregistrationwithkotlin.responces.LoginResponse
import com.mvvm.loginregistrationwithkotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class RegistrationViewModel(
    private val repository: RegistrationRepository
): BaseViewModel(repository) {
    private val _signupResponse: MutableLiveData<Resource<ResponseBody>> = MutableLiveData()
    val signupResponse: LiveData<Resource<ResponseBody>>
        get() = _signupResponse

    fun signup(
        name: String,
        email: String,
        password: String,
        confirmpassword:String
    ) = viewModelScope.launch {
        _signupResponse.value = Resource.loading
        _signupResponse.value = repository.signup(name,email, password,confirmpassword)
    }
}