package com.mvvm.loginregistrationwithkotlin.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mvvm.loginregistrationwithkotlin.R
import com.mvvm.loginregistrationwithkotlin.databinding.FragmentLoginBinding
import com.mvvm.loginregistrationwithkotlin.network.AuthApi
import com.mvvm.loginregistrationwithkotlin.network.Resource
import com.mvvm.loginregistrationwithkotlin.repository.AuthRepository
import com.mvvm.loginregistrationwithkotlin.ui.base.BaseFragment
import com.mvvm.loginregistrationwithkotlin.ui.home.HomeActivity
import com.mvvm.loginregistrationwithkotlin.util.enable
import com.mvvm.loginregistrationwithkotlin.util.handleApiError
import com.mvvm.loginregistrationwithkotlin.util.startNewActivity
import com.mvvm.loginregistrationwithkotlin.util.visible
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.buttonSignin.enable(false)
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.loading)

            when (it) {
                is Resource.Success -> {
                        lifecycleScope.launch {
                            viewModel.saveAuthToken(it.value.user.access_token)
                            requireActivity().startNewActivity(HomeActivity::class.java)
                        }
                }
                is Resource.Failure -> handleApiError(it){login()}
            }
        })

        binding.eidttextPassword.addTextChangedListener {
            val email = binding.eidttextEmail.text.toString().trim()
            binding.buttonSignin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }



        binding.buttonSignin.setOnClickListener {
            login()
        }

        binding.buttonSignup.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }


    }

    private fun login(){
        val email = binding.eidttextEmail.text.toString().trim()
        val password = binding.eidttextPassword.text.toString().trim()
        viewModel.login(email, password)
    }
    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java),userPreferences)

}