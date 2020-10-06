package com.mvvm.loginregistrationwithkotlin.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.mvvm.loginregistrationwithkotlin.R
import com.mvvm.loginregistrationwithkotlin.databinding.FragmentHomeBinding
import com.mvvm.loginregistrationwithkotlin.network.Resource
import com.mvvm.loginregistrationwithkotlin.network.UserApi
import com.mvvm.loginregistrationwithkotlin.repository.UserRepository
import com.mvvm.loginregistrationwithkotlin.responces.userdetail.User
import com.mvvm.loginregistrationwithkotlin.ui.base.BaseFragment
import com.mvvm.loginregistrationwithkotlin.util.visible
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeFragment : BaseFragment<HomeViewModel,FragmentHomeBinding,UserRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser()
        viewModel.user.observe(viewLifecycleOwner, Observer {
            when(it)
            {
                is Resource.Success->{
                    binding.progressCircular.visible(false)
                    updateUI(it.value.user)
                }
                is Resource.loading-> {
                        binding.progressCircular.visible(true)
                }
            }

        })
        binding.buttonLogout.setOnClickListener {
            logout()
        }
    }
    private fun updateUI(user : User)
    {
        with(binding)
        {
            textviewEmail.text = user.email
            textviewName.text = user.name
        }
    }
    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater,container,false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = remoteDataSource.buildApi(UserApi::class.java,token)
        return UserRepository(api)

    }


}