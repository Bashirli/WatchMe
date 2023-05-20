package com.bashirli.watchme.ui.fragment.trending

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.watchme.IMAGE_BASE_URL
import com.bashirli.watchme.databinding.ItemMovieBinding
import com.bashirli.watchme.model.movie.MovieModel
import com.bashirli.watchme.model.movie.Result
import com.bashirli.watchme.setImageURL

class TrendingAdapter : RecyclerView.Adapter<TrendingAdapter.TrendingHolder>() {


    private val arrayList=ArrayList<Result>()
    var onClickItemListener:(Result)->Unit={}

    inner class TrendingHolder(val binding:ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun find(result: Result,onClickItemListener:(Result)->Unit){
            onClickItemListener(result)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingHolder {
        val layout=ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TrendingHolder(layout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: TrendingHolder, position: Int) {
       val item=arrayList.get(position)
        with(holder.binding){
            result=item
            imageView.setImageURL(IMAGE_BASE_URL+item.posterPath,holder.itemView.context)
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