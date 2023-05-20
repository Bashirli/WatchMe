package com.bashirli.watchme.ui.fragment.discover

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.watchme.Status
import com.bashirli.watchme.base.BaseFragment
import com.bashirli.watchme.databinding.FragmentDiscoverBinding


class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>(
    FragmentDiscoverBinding::inflate
) {
    private lateinit var viewModel:DiscoverMVVM
    private var adapter=DiscoverAdapter()
    private var page=1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(DiscoverMVVM::class.java)
        setup()
        observeData()
    }

    private fun setup(){
        page=1
        setRV()
        adapter.onClickItemListener={
            findNavController().navigate(DiscoverFragmentDirections.actionDiscoverFragmentToDetailsFragment(it.id))
        }

    }

    private fun setRV(){
        with(binding){
        val layoutManager=LinearLayoutManager(requireContext())
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=adapter
        recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastItem=layoutManager.findLastVisibleItemPosition()
                if(lastItem>adapter.itemCount*0.7){
                    page+=1
                    viewModel.sendLoadMovies(page)
                }
            }
        })
        }
    }

    private fun observeData(){
        viewModel.loadMovies(page)
        with(viewModel){
            with(binding){

                loading.observe(viewLifecycleOwner){
                    if(it){
                        recyclerView.visibility=View.GONE
                        progressBar.visibility=View.VISIBLE
                    }
                }
                movieResponse.observe(viewLifecycleOwner){
                    when(it.status){
                        Status.SUCCESS->{
                            it.data?.let {
                                adapter.updateList(it.results)
                            }
                            progressBar.visibility=View.GONE
                            recyclerView.visibility=View.VISIBLE

                        }
                        Status.ERROR->{
                            progressBar.visibility=View.GONE
                            recyclerView.visibility=View.GONE
                            Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                        }
                        else->{
                            it.message?.let { message->
                                Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                moviePageResponse.observe(viewLifecycleOwner){
                    when(it.status){
                        Status.SUCCESS->{
                            it.data?.let {
                                adapter.addToList(it.results)
                            }
                        }
                        Status.ERROR->{
                            Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                        }
                        else->{
                            it.message?.let { message->
                                Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

            }
        }
    }

}