package com.bashirli.watchme.ui.fragment.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.watchme.IMAGE_BASE_URL
import com.bashirli.watchme.databinding.ItemDiscoverBinding
import com.bashirli.watchme.databinding.ItemGenreBinding
import com.bashirli.watchme.model.genre.item.Item
import com.bashirli.watchme.model.movie.Result
import com.bashirli.watchme.setImageURL
import com.bashirli.watchme.ui.fragment.discover.DiscoverAdapter

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenreHolder>() {
    var onClickItemListener : (Item) -> Unit={}
    private val arrayList=ArrayList<Item>()


    inner class GenreHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root){
        fun find(item: Item, onClickItemListener:(Item)->Unit={}){
            binding.linearLayout.setOnClickListener {
                onClickItemListener(item)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreHolder {
        val layout= ItemGenreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GenreHolder(layout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: GenreHolder, position: Int) {
        val item=arrayList.get(position)
        with(holder.binding){
            title.text=item.title
            description.text=item.overview
            releaseDate.text=item.releaseDate
            imageView.setImageURL(IMAGE_BASE_URL +item.posterPath,holder.itemView.context)
        }
        holder.find(item,onClickItemListener)

    }

    fun updateList(list:List<Item>){
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }



}