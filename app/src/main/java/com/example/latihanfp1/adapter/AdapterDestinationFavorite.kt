package com.example.latihanfp1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanfp1.databinding.ItemDestinationFavoriteBinding
import com.example.latihanfp1.model.DataDestinationFavorite

class AdapterDestinationFavorite(private var list : List<DataDestinationFavorite>) : RecyclerView.Adapter<AdapterDestinationFavorite.ViewHolder>() {
    class ViewHolder(var binding: ItemDestinationFavoriteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = ItemDestinationFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.binding.tvDestination.text = list[position].flightDestination
       holder.binding.tvMaskapai.text = list[position].maskapai
        holder.binding.tvDate.text = list[position].dateFlight
        holder.binding.tvPrice.text = list[position].price.toString()

    }
    fun setListDestinationFavorite(listDestination : List<DataDestinationFavorite>){
        list = listDestination
        notifyDataSetChanged()
    }
}