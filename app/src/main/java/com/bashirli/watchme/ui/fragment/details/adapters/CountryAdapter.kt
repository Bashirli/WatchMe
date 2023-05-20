package com.bashirli.watchme.ui.fragment.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.watchme.databinding.ItemChipBinding
import com.bashirli.watchme.databinding.ItemSimpleBinding
import com.bashirli.watchme.model.moviedetails.ProductionCountry

class CountryAdapter :RecyclerView.Adapter<CountryAdapter.CountryHolder>() {

    private val arrayList=ArrayList<ProductionCountry>()

    inner class CountryHolder(val binding:ItemSimpleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val layout=ItemSimpleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryHolder(layout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        val item=arrayList.get(position)
        holder.binding.chip.setText(item.name)
    }

    fun updateList(list:List<ProductionCountry>){
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }

}