package com.bashirli.watchme.ui.fragment.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.watchme.R
import com.bashirli.watchme.Status
import com.bashirli.watchme.base.BaseFragment
import com.bashirli.watchme.databinding.FragmentSearchBinding
import com.bashirli.watchme.ui.fragment.search.adapter.GenreAdapter
import com.bashirli.watchme.ui.fragment.search.adapter.SearchMovieAdapter
import com.bashirli.watchme.ui.fragment.trending.TrendingAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class SearchFragment : BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::inflate
) {

    private lateinit var viewModel:SearchMVVM
    private  var genreAdapter=GenreAdapter()
    private var searchAdapter=SearchMovieAdapter()
    private var page=1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(SearchMVVM::class.java)
        setup()
    }

    private fun setup(){
        observeData()
        with(binding){
           searchText.addTextChangedListener { text ->
               lifecycleScope.launch {
                   delay(700)
                   if(text.toString().trim().isNotEmpty()){
                    viewModel.searchMovie(text.toString(),page)
                   }else{
                    viewModel.loadMoviePage(page)
                   }
               }
           }
            setRV()
        }
    }

    private fun setRV(){
        with(binding){
            val layoutManager=GridLayoutManager(requireContext(),2)

            rvGenre.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            rvMovie.layoutManager=layoutManager

            rvGenre.adapter=genreAdapter
            rvMovie.adapter=searchAdapter

            searchAdapter.onClickItemListener={
                findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(it.id))
            }

            genreAdapter.onGenreItemClickListener={
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToGenreFragment(it.id,it.name))
            }

        }
    }

    private fun observeData(){
        with(viewModel){
            with(binding){
                loading.observe(viewLifecycleOwner){
                    if(it){
                        linearProgress.visibility=View.VISIBLE
                        rvMovie.visibility=View.GONE
                    }
                }
                genres.observe(viewLifecycleOwner){
                    it?.let {
                        when(it.status){
                            Status.ERROR->{
                                linearProgress.visibility=View.GONE
                                rvGenre.visibility=View.GONE

                                Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                            }
                            Status.SUCCESS->{
                                rvGenre.visibility=View.VISIBLE
                                it.data?.let { genreModel->
                                    genreAdapter.updateList(genreModel.genres)
                                }
                            }
                            else->{
                                it.message?.let { message->
                                    linearProgress.visibility=View.GONE
                                    rvGenre.visibility=View.GONE
                                    Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
                movieData.observe(viewLifecycleOwner){
                    it?.let {
                        when(it.status){
                            Status.ERROR->{
                                linearProgress.visibility=View.GONE
                                rvMovie.visibility=View.GONE
                                Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                            }
                            Status.SUCCESS->{
                                linearProgress.visibility=View.GONE
                                rvMovie.visibility=View.VISIBLE
                                it.data?.let { data->
                                    searchAdapter.updateList(data.results)
                                }
                            }
                            else->{
                                it.message?.let { message->
                                    linearProgress.visibility=View.GONE
                                    rvMovie.visibility=View.GONE
                                    Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }



}