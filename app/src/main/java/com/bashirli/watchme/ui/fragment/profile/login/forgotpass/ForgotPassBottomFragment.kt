package com.bashirli.watchme.ui.fragment.profile.login.forgotpass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bashirli.watchme.CustomProgressBar
import com.bashirli.watchme.R
import com.bashirli.watchme.databinding.FragmentBottomForgotPassBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ForgotPassBottomFragment:BottomSheetDialogFragment() {

    private var _binding:FragmentBottomForgotPassBinding?=null
    val binding:FragmentBottomForgotPassBinding get()=_binding!!

    private lateinit var viewModel:ForgotPassMVVM


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         _binding= FragmentBottomForgotPassBinding.inflate(inflater,container,false)
        return binding.root
       }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(ForgotPassMVVM::class.java)
        setup()
    }

    private fun setup(){
        with(binding){
            buttonSendCode.setOnClickListener {
                sendCode()
            }
        }
    }

    private fun sendCode(){
        with(binding){
            val email = emailText.text.toString()

            if(email.trim().isEmpty()){
                Toast.makeText(requireContext(),R.string.fillTheGaps,Toast.LENGTH_SHORT).show()
                return
            }

            viewModel.sendCode(email)
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
                progressDialog.cancel()
                    when(it.status){
                        true->{
                            val alertDialog=MaterialAlertDialogBuilder(requireContext())
                            alertDialog.setCancelable(false).setTitle(R.string.attention).setMessage(R.string.resetInfo2)
                                .setPositiveButton(R.string.ok){_,_ ->
                                    onDestroyView()
                                }
                            val dialog=alertDialog.create()
                            dialog.show()

                        }
                        false->{Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_SHORT).show() }
                    }
                }
            }

        }


    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}