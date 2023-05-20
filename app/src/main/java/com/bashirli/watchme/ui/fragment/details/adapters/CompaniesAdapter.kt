package com.bashirli.watchme.ui.fragment.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.watchme.IMAGE_BASE_URL
import com.bashirli.watchme.databinding.ItemCompaniesBinding
import com.bashirli.watchme.databinding.ItemSimpleBinding
import com.bashirli.watchme.model.moviedetails.Genre
import com.bashirli.watchme.model.moviedetails.ProductionCompany
import com.bashirli.watchme.setImageURL

class CompaniesAdapter : RecyclerView.Adapter<CompaniesAdapter.CompaniesHolder>() {

    private val arrayList=ArrayList<ProductionCompany>()

    inner class CompaniesHolder(val binding: ItemCompaniesBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompaniesHolder {
        val layout= ItemCompaniesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CompaniesHolder(layout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: CompaniesHolder, position: Int) {
        val item=arrayList.get(position)
         holder.binding.imageView.setImageURL(IMAGE_BASE_URL+item.logoPath,holder.itemView.context)
        holder.binding.companyName.setText(item.name)
    }

    fun updateList(list:List<ProductionCompany>){
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }

}