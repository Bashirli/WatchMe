package com.bashirli.watchme.ui.fragment.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.watchme.databinding.ItemSimpleBinding
import com.bashirli.watchme.model.moviedetails.Genre

class SimpleAdapter : RecyclerView.Adapter<SimpleAdapter.SimpleHolder>() {

    private val arrayList=ArrayList<Genre>()
    var onGenreItemClickListener:(Genre)->Unit={}

    inner class SimpleHolder(val binding:ItemSimpleBinding):RecyclerView.ViewHolder(binding.root){
        fun find(genre:Genre,onGenreItemClickListener:(Genre)->Unit={}){
            binding.chip.setOnClickListener {
                onGenreItemClickListener(genre)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleHolder {
        val layout=ItemSimpleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SimpleHolder(layout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: SimpleHolder, position: Int) {
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