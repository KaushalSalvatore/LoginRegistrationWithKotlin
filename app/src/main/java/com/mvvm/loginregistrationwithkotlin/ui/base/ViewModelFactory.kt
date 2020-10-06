package com.mvvm.loginregistrationwithkotlin.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvvm.loginregistrationwithkotlin.repository.AuthRepository
import com.mvvm.loginregistrationwithkotlin.repository.BaseRepository
import com.mvvm.loginregistrationwithkotlin.repository.RegistrationRepository
import com.mvvm.loginregistrationwithkotlin.repository.UserRepository
import com.mvvm.loginregistrationwithkotlin.ui.auth.AuthViewModel
import com.mvvm.loginregistrationwithkotlin.ui.auth.RegistrationViewModel
import com.mvvm.loginregistrationwithkotlin.ui.home.HomeViewModel

class ViewModelFactory(
    private val repository: BaseRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository  as AuthRepository) as T
            modelClass.isAssignableFrom(RegistrationViewModel::class.java) -> RegistrationViewModel(repository  as RegistrationRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository as UserRepository) as T
           else -> throw IllegalArgumentException("View Model Nt found")
        }
    }

}