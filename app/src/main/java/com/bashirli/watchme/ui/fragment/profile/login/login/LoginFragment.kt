package com.bashirli.watchme.ui.fragment.profile.login.login

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
import com.bashirli.watchme.databinding.FragmentLoginBinding
import com.bashirli.watchme.ui.fragment.profile.login.forgotpass.ForgotPassBottomFragment
import com.bashirli.watchme.ui.fragment.profile.register.RegisterFragmentDirections

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {

    private  lateinit var viewModel: LoginMVVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val anim = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
        sharedElementEnterTransition=anim
        sharedElementReturnTransition=anim
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(LoginMVVM::class.java)
        setup()
    }

    private fun setup(){
        with(binding){
            val goBackButtonAnim=AnimationUtils.loadAnimation(requireContext(),R.anim.goback_anim)
            buttonGoBack.animation=goBackButtonAnim
            buttonGoBack.setOnClickListener {
                findNavController().popBackStack()
            }

            buttonLogin.setOnClickListener {
            login()
            }

            buttonForgotPass.setOnClickListener {
                val bottomFragment=ForgotPassBottomFragment()
                bottomFragment.show(requireActivity().supportFragmentManager,"forgotPassFragment")
            }

        }
    }

    private fun login(){
        with(binding){
            val email=emailText.text.toString()
            val pass=passText.text.toString()
            if(validateError(email.trim(),pass.trim())){
                return
            }
            viewModel.login(email, pass)
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
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment())
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
    private fun validateError(email:String,pass:String):Boolean{
        if(email.isEmpty()||pass.isEmpty()){
            Toast.makeText(requireContext(),R.string.fillTheGaps,Toast.LENGTH_SHORT).show()
            return true
        }
        if(pass.length<6){
            Toast.makeText(requireContext(),R.string.passMin,Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

}
