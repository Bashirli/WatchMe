package com.bashirli.watchme.ui.fragment.profile.register

import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bashirli.watchme.CustomProgressBar
import com.bashirli.watchme.R
import com.bashirli.watchme.base.BaseFragment
import com.bashirli.watchme.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
){

    private lateinit var viewModel:RegisterMVVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val anim= TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition=anim
        sharedElementReturnTransition=anim

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(RegisterMVVM::class.java)
        setup()
    }

    private fun setup(){
        val anim=AnimationUtils.loadAnimation(requireContext(), R.anim.goback_anim)
        with(binding){
            buttonGoBack.animation=anim
            buttonGoBack.setOnClickListener {
                findNavController().popBackStack()
            }
            buttonRegister.setOnClickListener {
                register()
            }
        }
    }

    private fun register(){
        with(binding){
            val email = emailText.text.toString()
            val nickname= nicknameText.text.toString()
            val pass=passText.text.toString()
            val repass=repassText.text.toString()
            if(checkError(email.trim(), nickname.trim(), pass.trim(), repass.trim())){
                return
            }

            viewModel.register(email,nickname,pass)
            observeData()
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
                        when(it.status){
                            true->{
                                setSP()
                                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToProfileFragment())
                            }
                            false->{Toast.makeText(requireContext(),it.message?:"Error",Toast.LENGTH_SHORT).show()}
                        }
                    }
                }

            }
    }
    private fun setSP(){
        val sp=requireActivity().getSharedPreferences("currentUser", Context.MODE_PRIVATE)
        val token=viewModel.getToken()
        sp.edit().putString("token",token).apply()
    }


    private fun checkError(email:String,nickname:String,pass:String,repass:String):Boolean{
        if(email.isEmpty()||nickname.isEmpty()||pass.isEmpty()||repass.isEmpty()){
            Toast.makeText(requireContext(), R.string.fillTheGaps, Toast.LENGTH_SHORT).show()
            return true
        }

        if(nickname.length>25){
            Toast.makeText(requireContext(), R.string.nickLength, Toast.LENGTH_SHORT).show()
            return true
        }

        if(pass.length<6){
            Toast.makeText(requireContext(), R.string.passMin, Toast.LENGTH_SHORT).show()
            return true
        }

        if(!pass.equals(repass)){
            Toast.makeText(requireContext(), R.string.passmatch, Toast.LENGTH_SHORT).show()
            return true
        }

        return false
    }


}