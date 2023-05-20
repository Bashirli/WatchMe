package com.bashirli.watchme.ui.fragment.details

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bashirli.watchme.ui.fragment.details.adapters.SimpleAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bashirli.watchme.CustomProgressBar
import com.bashirli.watchme.IMAGE_BASE_URL
import com.bashirli.watchme.OnBackPressedFragment
import com.bashirli.watchme.R
import com.bashirli.watchme.Status
import com.bashirli.watchme.base.BaseFragment
import com.bashirli.watchme.databinding.FragmentDetailsBinding
import com.bashirli.watchme.setImageURL
import com.bashirli.watchme.ui.fragment.details.adapters.CompaniesAdapter
import com.bashirli.watchme.ui.fragment.details.adapters.CountryAdapter
import com.bashirli.watchme.ui.fragment.details.adapters.RecommendationAdapter
import com.bashirli.watchme.ui.fragment.trending.TrendingAdapter

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(
    FragmentDetailsBinding::inflate
) {

    private val args:DetailsFragmentArgs by navArgs()
    private var movieId=0
    private lateinit var viewModel:DetailsMVVM

    private val genreAdapter= SimpleAdapter()
    private val companyAdapter= CompaniesAdapter()
    private val countryAdapter= CountryAdapter()
    private val recommendationAdapter=RecommendationAdapter()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        viewModel=ViewModelProvider(requireActivity()).get(DetailsMVVM::class.java)
        observeData()
    }


    private fun setup(){

        movieId=args.movieId
        setRv()

        with(binding){
            with(recommendationLayout){
               recommendationButton.setOnClickListener {
                   if(visibility==View.GONE){
                       observeRecommended()
                       visibility=View.VISIBLE
                       iconButton.setImageResource(R.drawable.baseline_arrow_drop_down_24)
                   }else{
                       visibility=View.GONE
                       iconButton.setImageResource(R.drawable.baseline_arrow_right_24)
                   }
               }
            }

            goBackButton.setOnClickListener {
                findNavController().popBackStack()
            }

            genreAdapter.onGenreItemClickListener={
                findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToGenreFragment(it.id,it.name))
            }

        }
    }

    private fun setRv(){
        with(binding){
            val genreLayoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            val productionLayoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            val countryLayoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            val recommendationsManager=GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            rvGenres.layoutManager=genreLayoutManager
            rvProductionCompanies.layoutManager=productionLayoutManager
            rvProductionCountries.layoutManager=countryLayoutManager
            rvRecommendations.layoutManager=recommendationsManager

            rvGenres.adapter=genreAdapter
            rvProductionCompanies.adapter=companyAdapter
            rvProductionCountries.adapter=countryAdapter
            rvRecommendations.adapter=recommendationAdapter


            recommendationAdapter.onClickItemListener={
                findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentSelf(it.id))
            }

        }
    }

    private fun observeData(){
        with(viewModel){
            val progressDialog=CustomProgressBar.progressDialog(requireContext())
            loadData(movieId)

            loading.observe(viewLifecycleOwner){
                if(it){
                    progressDialog.show()
                }
            }

            movieData.observe(viewLifecycleOwner){
                it?.let {
                    when(it.status){
                        Status.SUCCESS->{
                            it.data?.let {data->
                                progressDialog.cancel()
                                binding.movieData=data
                                binding.moviePosterImage.setImageURL(IMAGE_BASE_URL+data.backdropPath,requireContext())
                                genreAdapter.updateList(data.genres)
                                companyAdapter.updateList(data.productionCompanies)
                                countryAdapter.updateList(data.productionCountries)
                            }
                        }
                        Status.ERROR->{
                            progressDialog.cancel()
                            Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                        }
                        else->{

                            it.message?.let { message->
                                progressDialog.cancel()
                                Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
    }
}


    private fun observeRecommended(){
        viewModel.loadRecommendations(movieId)
        binding.progressBarRV.visibility=View.VISIBLE
        binding.rvRecommendations.visibility=View.GONE
        viewModel.recommendData.observe(viewLifecycleOwner){
            it?.let {
                when(it.status){
                    Status.SUCCESS->{
                        it.data?.let {data->
                            binding.progressBarRV.visibility=View.GONE
                            binding.rvRecommendations.visibility=View.VISIBLE
                        recommendationAdapter.updateList(data.results)
                        }
                    }
                    Status.ERROR->{
                        binding.progressBarRV.visibility=View.GONE
                        binding.rvRecommendations.visibility=View.GONE
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    }
                    else->{
                        it.message?.let { message->
                            binding.progressBarRV.visibility=View.GONE
                            binding.rvRecommendations.visibility=View.GONE
                            Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }


    }


}

