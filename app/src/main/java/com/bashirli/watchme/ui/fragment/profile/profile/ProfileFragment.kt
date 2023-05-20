package com.bashirli.watchme.ui.fragment.profile.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bashirli.watchme.CustomProgressBar
import com.bashirli.watchme.R
import com.bashirli.watchme.base.BaseFragment
import com.bashirli.watchme.databinding.FragmentEditProfileBinding
import com.bashirli.watchme.databinding.FragmentProfileBinding
import com.bashirli.watchme.setImageURL
import com.bashirli.watchme.ui.fragment.profile.login.login.LoginMVVM
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {

    private lateinit var sp: SharedPreferences
    private lateinit var viewModel:ProfileMVVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(ProfileMVVM::class.java)
        checkStatus()

        setup()

    }

    private fun checkStatus(){
        sp=requireActivity().getSharedPreferences("currentUser", Context.MODE_PRIVATE)
        val token=sp.getString("token",null)
        if(token == null){
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLogRegFragment())
        }else{

            observeData()
        }
    }

    private fun setup(){

        with(binding){
            goBackButton.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_discoverFragment)
            }
            signOut.setOnClickListener {

            }
            editProfile.setOnClickListener {
                setProfileEdit()
            }
        }

    }

    private fun setProfileEdit(){
        val alert=MaterialAlertDialogBuilder(requireContext())
        val alertLayout=FragmentEditProfileBinding.inflate(layoutInflater)

        alert.setView(alertLayout.root).create().show()
    }

    private fun observeData(){
        val progressDialog=CustomProgressBar.progressDialog(requireContext())
        with(viewModel){
            loading.observe(viewLifecycleOwner){
                if(it){
                    progressDialog.show()
                }
            }

            }

        }
    }



