package com.mvvm.loginregistrationwithkotlin.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.mvvm.loginregistrationwithkotlin.network.RemoteDataSource
import com.mvvm.loginregistrationwithkotlin.network.UserApi
import com.mvvm.loginregistrationwithkotlin.preferences.UserPreferences
import com.mvvm.loginregistrationwithkotlin.repository.BaseRepository
import com.mvvm.loginregistrationwithkotlin.ui.auth.AuthActivity
import com.mvvm.loginregistrationwithkotlin.ui.auth.AuthViewModel
import com.mvvm.loginregistrationwithkotlin.util.startNewActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


abstract class BaseFragment<VM: BaseViewModel ,B: ViewBinding,R: BaseRepository> : Fragment() {

    protected lateinit var binding : B
    protected  val remoteDataSource = RemoteDataSource()
    protected lateinit var viewModel: VM
    protected lateinit var userPreferences: UserPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            userPreferences = UserPreferences(requireContext())
            binding = getFragmentBinding(inflater,container)
            val factory = ViewModelFactory(getFragmentRepository())
            viewModel = ViewModelProvider(this, factory).get(getViewModel())
            return binding.root
    }

    fun logout() = lifecycleScope.launch{
        val authtoken = userPreferences.authToken.first()
        val  api = remoteDataSource.buildApi(UserApi::class.java,authtoken)
        viewModel.logout(api)
        userPreferences.clear()
        requireActivity().startNewActivity(AuthActivity::class.java)
    }

    abstract fun getViewModel() : Class<VM>
    abstract fun getFragmentBinding(inflater: LayoutInflater,container: ViewGroup?): B
    abstract fun getFragmentRepository(): R
}
