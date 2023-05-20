package com.bashirli.watchme.ui.fragment.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.watchme.IMAGE_BASE_URL
import com.bashirli.watchme.databinding.ItemDiscoverBinding
import com.bashirli.watchme.model.movie.Result
import com.bashirli.watchme.setImageURL

class DiscoverAdapter :RecyclerView.Adapter<DiscoverAdapter.DiscoverHolder>() {
    var onClickItemListener : (Result) -> Unit={}
    private val arrayList=ArrayList<Result>()


    inner class DiscoverHolder(val binding:ItemDiscoverBinding) :RecyclerView.ViewHolder(binding.root){
        fun find(item:Result,onClickItemListener:(Result)->Unit={}){
               binding.linearLayout.setOnClickListener {
                    onClickItemListener(item)
               }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverHolder {
        val layout=ItemDiscoverBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DiscoverHolder(layout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: DiscoverHolder, position: Int) {
           val item=arrayList.get(position)
            with(holder.binding){
                result=item
                imageView.setImageURL(IMAGE_BASE_URL+item.posterPath,holder.itemView.context)
            }
        holder.find(item,onClickItemListener)

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