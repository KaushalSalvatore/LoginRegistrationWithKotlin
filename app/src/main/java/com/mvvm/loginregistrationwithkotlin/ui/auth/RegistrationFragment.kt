package com.mvvm.loginregistrationwithkotlin.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mvvm.loginregistrationwithkotlin.R
import com.mvvm.loginregistrationwithkotlin.databinding.FragmentRegistrationBinding
import com.mvvm.loginregistrationwithkotlin.network.AuthApi
import com.mvvm.loginregistrationwithkotlin.network.Resource
import com.mvvm.loginregistrationwithkotlin.repository.AuthRepository
import com.mvvm.loginregistrationwithkotlin.repository.RegistrationRepository
import com.mvvm.loginregistrationwithkotlin.ui.base.BaseFragment
import com.mvvm.loginregistrationwithkotlin.util.visible
import kotlinx.coroutines.launch


class RegistrationFragment : BaseFragment<RegistrationViewModel, FragmentRegistrationBinding, RegistrationRepository>(){

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.signupResponse.observe(viewLifecycleOwner, Observer {
            binding.progressCircular.visible(it is Resource.loading)
        when(it)
        {
            is Resource.Success ->{
                    findNavController().navigate(R.id.loginFragment)
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()

            }

            is Resource.Failure ->{
                Toast.makeText(requireContext(), "Somthing Went Wrong", Toast.LENGTH_SHORT).show()

            }

        }
        })

        binding.buttonLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)

        }
        binding.buttonSignup.setOnClickListener {
                signup()
        }
    }

    private fun signup(){
        val name = binding.eidttextName.text.toString().trim()
        val email = binding.eidttextEmail.text.toString().trim()
        val password = binding.eidttextPassword.text.toString().trim()
        val cpassword = binding.eidttextCpassword.text.toString().trim()
        viewModel.signup(name,email,password,cpassword)

    }

    override fun getViewModel()  = RegistrationViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentRegistrationBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = RegistrationRepository(remoteDataSource.buildApi(AuthApi::class.java))


}