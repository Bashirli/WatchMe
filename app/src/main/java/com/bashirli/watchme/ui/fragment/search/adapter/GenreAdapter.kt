package com.bashirli.watchme.ui.fragment.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.watchme.databinding.ItemSimpleBinding
import com.bashirli.watchme.model.genre.list.Genre
import com.bashirli.watchme.ui.fragment.details.adapters.SimpleAdapter

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenreHolder>() {

    private val arrayList=ArrayList<Genre>()
    var onGenreItemClickListener:(Genre)->Unit={}

    inner class GenreHolder(val binding: ItemSimpleBinding): RecyclerView.ViewHolder(binding.root){
        fun find(item:Genre,onGenreItemCLickListener:(Genre)->Unit={}){
            binding.chip.setOnClickListener {
                onGenreItemCLickListener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreHolder {
        val layout= ItemSimpleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GenreHolder(layout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: GenreHolder, position: Int) {
        val item=arrayList.get(position)
        holder.binding.chip.setText(item.name)
        holder.find(item,onGenreItemClickListener)
    }

    fun updateList(list:List<Genre>){
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }

}