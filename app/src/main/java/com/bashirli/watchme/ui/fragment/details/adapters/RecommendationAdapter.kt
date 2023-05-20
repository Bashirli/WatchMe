package com.bashirli.watchme.ui.fragment.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.watchme.IMAGE_BASE_URL
import com.bashirli.watchme.databinding.ItemDiscoverBinding
import com.bashirli.watchme.databinding.ItemMovieBinding
import com.bashirli.watchme.databinding.ItemSimpleBinding
import com.bashirli.watchme.setImageURL
import com.bashirli.watchme.model.moviedetails.recommendations.Result


class RecommendationAdapter : RecyclerView.Adapter<RecommendationAdapter.RecommendationHolder>() {
    var onClickItemListener : (Result) -> Unit={}
    private val arrayList=ArrayList<Result>()


    inner class RecommendationHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun find(item: Result, onClickItemListener:(Result)->Unit={}){
            binding.cardView.setOnClickListener {
                onClickItemListener(item)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationHolder {
        val layout= ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecommendationHolder(layout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: RecommendationHolder, position: Int) {
        val item=arrayList.get(position)
        with(holder.binding){
            imageView.setImageURL(IMAGE_BASE_URL +item.posterPath,holder.itemView.context)
        }
        holder.find(item,onClickItemListener)

    }

    fun updateList(list:List<Result>){
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }



}