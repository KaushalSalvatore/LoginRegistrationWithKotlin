package com.mvvm.loginregistrationwithkotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvvm.loginregistrationwithkotlin.network.Resource
import com.mvvm.loginregistrationwithkotlin.repository.UserRepository
import com.mvvm.loginregistrationwithkotlin.responces.userdetail.UserResponse
import com.mvvm.loginregistrationwithkotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(private val repository : UserRepository):BaseViewModel(repository) {

    private  val _user : MutableLiveData<Resource<UserResponse>> = MutableLiveData()
    val user : LiveData<Resource<UserResponse>>
    get() = _user

    fun getUser() = viewModelScope.launch {
        _user.value = Resource.loading
        _user.value = repository.getUser()
    }
}