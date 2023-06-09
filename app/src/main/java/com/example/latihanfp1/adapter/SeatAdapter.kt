package com.example.latihanfp1.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanfp1.R
import com.example.latihanfp1.databinding.ItemSeatBinding
import com.example.latihanfp1.model.DataSeat

class SeatAdapter(private var listSeat : List<DataSeat>) : RecyclerView.Adapter<SeatAdapter.ViewHolder>() {
    var clickItemSeat : ((DataSeat)->Unit)? = null

    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    class ViewHolder(var binding : ItemSeatBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemSeatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listSeat.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val marginEnd = holder.itemView.context.resources.getDimensionPixelSize(R.dimen.marginEndSeat)

        setMargin(holder.binding.itemCard, listSeat[position].numSeat, marginEnd)

        if(listSeat[position].seatAvaiable){
            holder.binding.tvNumSeat.text = listSeat[position].numSeat.toString()

        }else{
            holder.binding.tvNumSeat.text = "X"
            holder.binding.itemSeat.setBackgroundColor(Color.parseColor("#D0D0D0"))
        }

        holder.binding.itemSeat.setOnClickListener {
            clickItemSeat?.invoke(listSeat[position])
        }

        val isSelected = position == selectedItemPosition

        if (isSelected) {
            // Ganti warna item yang dipilih
            holder.binding.itemSeat.setBackgroundColor(Color.parseColor("#7126B5")) // Ganti dengan warna yang diinginkan
        }
//        else {
//            // Ganti warna item yang tidak dipilih
//            holder.binding.itemSeat.setBackgroundColor(Color.WHITE) // Ganti dengan warna yang diinginkan
//        }


    }

    private fun addMarginEnd(itemView: View, marginEnd: Int) {
        val layoutParams = itemView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.marginEnd = marginEnd
        itemView.layoutParams = layoutParams
    }

    fun setMargin(cardView: CardView, position: Int, marginEnd: Int){
        when(position){
            3, 9, 15, 21, 27, 33, 39, 45, 51, 57, 62, 69 ->{
                addMarginEnd(cardView, marginEnd)
            }
        }
    }

    fun setListSeat(list: List<DataSeat>){
        listSeat = list
        notifyDataSetChanged()
    }

    fun setSelectedItem(position: Int) {
        selectedItemPosition = position
        notifyDataSetChanged()
    }
}