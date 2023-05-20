package com.bashirli.watchme.ui.fragment.genre

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bashirli.watchme.CustomProgressBar
import com.bashirli.watchme.IMAGE_BASE_URL
import com.bashirli.watchme.R
import com.bashirli.watchme.Status
import com.bashirli.watchme.base.BaseFragment
import com.bashirli.watchme.databinding.FragmentGenreBinding
import com.bashirli.watchme.setImageURL

class GenreFragment : BaseFragment<FragmentGenreBinding>(
    FragmentGenreBinding::inflate
) {

    private lateinit var viewModel:GenreMVVM
    private val args:GenreFragmentArgs by navArgs()
    private var _genreId=0
    private val adapter=GenreAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(GenreMVVM::class.java)
        setup()

    }


    private fun setup(){
        _genreId=args.genreId
        viewModel.loadData(_genreId)
        observeData()
        with(binding){
            genreName.text=args.genreName
            rvGenreItems.layoutManager=LinearLayoutManager(requireContext())
            rvGenreItems.adapter=adapter

            goBackButton.setOnClickListener {
                findNavController().popBackStack()
            }

            adapter.onClickItemListener={
                findNavController().navigate(GenreFragmentDirections.actionGenreFragmentToDetailsFragment(it.id))
            }
        }
    }

        private fun observeData(){
            with(viewModel){
                with(binding){
                    val progressDialog=CustomProgressBar.progressDialog(requireContext())
                    loading.observe(viewLifecycleOwner){
                        if(it){
                            progressDialog.show()
                        }
                    }

                    genreData.observe(viewLifecycleOwner){
                        it?.let {

                            when(it.status){
                                Status.SUCCESS->{
                                    it.data?.let {
                                        adapter.updateList(it.items)
                                        genreImage.setImageURL(IMAGE_BASE_URL+it.posterPath,requireContext())
                                        genreDescription.setText(it.description)
                                    }
                                    progressDialog.cancel()
                                }
                                Status.ERROR->{
                                    Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                                    progressDialog.cancel()
                                }
                                else->{

                                    it.message?.let { message->
                                        progressDialog.cancel()
                                        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }



}