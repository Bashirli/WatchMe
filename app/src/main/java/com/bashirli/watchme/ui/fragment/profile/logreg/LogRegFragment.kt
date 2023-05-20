package com.bashirli.watchme.ui.fragment.profile.logreg

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.bashirli.watchme.CustomProgressBar
import com.bashirli.watchme.R
import com.bashirli.watchme.base.BaseFragment
import com.bashirli.watchme.databinding.FragmentLogRegBinding


class LogRegFragment : BaseFragment<FragmentLogRegBinding>(
    FragmentLogRegBinding::inflate
) {

    private lateinit var viewModel:LogRegMVVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(LogRegMVVM::class.java)
        setup()

    }


    private fun setup(){
        with(binding){
            buttonLogin.setOnClickListener {
                val extras= FragmentNavigatorExtras(buttonLogin to "loginButton")
                findNavController().navigate(LogRegFragmentDirections.actionLogRegFragmentToLoginFragment(),extras)
            }
            buttonRegister.setOnClickListener {
                val extras= FragmentNavigatorExtras(buttonRegister to "registerButton")
                findNavController().navigate(LogRegFragmentDirections.actionLogRegFragmentToRegisterFragment(),extras)
            }
            buttonGuest.setOnClickListener {
                    viewModel.contAsAGuest()
                observeData()
            }
        }


    }


    private fun observeData(){
        val progressDialog=CustomProgressBar.progressDialog(requireContext())
        with(viewModel){
            loading.observe(viewLifecycleOwner){
                if(it){
                    progressDialog.show()
                }
            }
            authData.observe(viewLifecycleOwner){
                it?.let {
                    progressDialog.cancel()
                    when(it.status){
                        true->{
                            setSP()
                            findNavController().navigate(LogRegFragmentDirections.actionLogRegFragmentToDiscoverFragment())}
                        false->{Toast.makeText(requireContext(),it.message?:"Error",Toast.LENGTH_SHORT).show()}
                    }
                }
            }
        }
    }


    private fun setSP(){
        val sp=requireActivity().getSharedPreferences("currentUser", Context.MODE_PRIVATE)
        sp.edit().putString("token",null).apply()
    }

}