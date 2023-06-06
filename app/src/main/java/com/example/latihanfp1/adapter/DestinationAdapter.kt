package com.example.latihanfp1.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanfp1.model.ResponseDataCity
import com.example.latihanfp1.databinding.ItemDestinationBinding

class DestinationAdapter(private var list : List<ResponseDataCity>) : RecyclerView.Adapter<DestinationAdapter.ViewHolder>() {

    var onClickDestination: ((ResponseDataCity) -> Unit)? = null

    class ViewHolder(var binding: ItemDestinationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvDestination.text = list[position].destination

        holder.binding.itemList.setOnClickListener {
            onClickDestination?.invoke(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataDestination(listDestination: List<ResponseDataCity>) {
        list = listDestination
        notifyDataSetChanged()
    }
}