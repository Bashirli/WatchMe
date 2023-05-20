package com.bashirli.watchme.ui.fragment.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.watchme.IMAGE_BASE_URL
import com.bashirli.watchme.databinding.ItemMovieBinding
import com.bashirli.watchme.model.genre.item.Item
import com.bashirli.watchme.model.movie.Result
import com.bashirli.watchme.setImageURL
import com.bashirli.watchme.ui.fragment.trending.TrendingAdapter

class SearchMovieAdapter : RecyclerView.Adapter<SearchMovieAdapter.SearchHolder>() {


    private val arrayList=ArrayList<Result>()
    var onClickItemListener:(Result)->Unit={}

    inner class SearchHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun find(result: Result, onClickItemListener:(Item)->Unit){
            onClickItemListener(result)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val layout= ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchHolder(layout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val item=arrayList.get(position)
        with(holder.binding){
            imageView.setImageURL(IMAGE_BASE_URL +item.posterPath,holder.itemView.context)
        }
        holder.itemView.setOnClickListener {
            onClickItemListener(item)
        }
    }

    fun updateList(list:List<Result>){
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }


    fun addToList(list:List<Result>){
        arrayList.addAll(list)
        notifyDataSetChanged()
    }
}