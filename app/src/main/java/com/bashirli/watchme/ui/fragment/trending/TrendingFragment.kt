package com.bashirli.watchme.ui.fragment.trending

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.watchme.R
import com.bashirli.watchme.Status
import com.bashirli.watchme.base.BaseFragment
import com.bashirli.watchme.databinding.FragmentTrendingBinding

class TrendingFragment : BaseFragment<FragmentTrendingBinding>(
    FragmentTrendingBinding::inflate
) {
    private var page=1
    private lateinit var viewModel:TrendingMVVM
    private val adapter=TrendingAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(TrendingMVVM::class.java)
        setup()
        observeData()
    }

    private fun setup(){
        page=1
        with(binding){
                val layoutManager=GridLayoutManager(
                    requireContext(),2
                    ,GridLayoutManager.VERTICAL,false)
                rvTrending.layoutManager=layoutManager
                rvTrending.adapter=adapter

            rvTrending.addOnScrollListener(object:RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val position=layoutManager.findLastVisibleItemPosition()
                    if(position>adapter.itemCount*0.7){
                        page++
                        viewModel.updateList(page)
                    }
                }
            })

        }

        adapter.onClickItemListener={
            findNavController().navigate(TrendingFragmentDirections
                .actionTrendingFragmentToDetailsFragment(it.id))
        }

    }

    private fun observeData(){
       with(viewModel){
           loadMovies(page)
           with(binding){
               loading.observe(viewLifecycleOwner){
                   if(it){
                       linearLayout.visibility=View.GONE
                        progressBar.visibility=View.VISIBLE
                   }
               }
               movieData.observe(viewLifecycleOwner){
                   when(it.status){
                       Status.SUCCESS->{

                           it.data?.let {
                               adapter.updateList(it.results)
                           }
                           progressBar.visibility=View.GONE
                           linearLayout.visibility=View.VISIBLE

                       }
                       Status.ERROR->{
                           progressBar.visibility=View.GONE
                           linearLayout.visibility=View.GONE
                           Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                       }
                       else->{
                           it.message?.let { message->
                               Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
                           }
                       }
                   }
               }
                movieUpdateData.observe(viewLifecycleOwner){
                    it?.let {
                        when(it.status){
                            Status.SUCCESS->{
                                it.data?.let {
                                    adapter.addToList(it.results)
                                }
                            }
                            Status.ERROR->{
                                Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                            }
                            else->{
                                it.message?.let { message->
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