package com.example.latihanfp1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.latihanfp1.R
import com.example.latihanfp1.adapter.SeatAdapter
import com.example.latihanfp1.databinding.FragmentPilihKursiBinding
import com.example.latihanfp1.model.DataSeat

class PilihKursiFragment : Fragment() {
    
    private lateinit var binding : FragmentPilihKursiBinding
    private lateinit var seatAdapter: SeatAdapter
    private var listSeatPassengers = mutableListOf<DataSeat>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPilihKursiBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        seatAdapter = SeatAdapter(ArrayList())

        setList()
        setRecycleView()
    }

    private fun setList() {
        listSeatPassengers = mutableListOf(
            DataSeat(1, true) ,
            DataSeat(2, false),
            DataSeat(3, true),
            DataSeat(4, true),
            DataSeat(5, false),
            DataSeat(6, false),
            DataSeat(7, true),
            DataSeat(8, false),
            DataSeat(9, false),
            DataSeat(10, true),
            DataSeat(11, true),
            DataSeat(12, false),
            DataSeat(13, false),
            DataSeat(14, true),
            DataSeat(15, true),
            DataSeat(16, true),
            DataSeat(17, false),
            DataSeat(18, true),
            DataSeat(19, true),
            DataSeat(20, false),
            DataSeat(21, true),
            DataSeat(22, false),
            DataSeat(23, false),
            DataSeat(24, true),
            DataSeat(25, true),
            DataSeat(26, true),
            DataSeat(27, true),
            DataSeat(28, false),
            DataSeat(29, false),
            DataSeat(30, true),
            DataSeat(31, false),
            DataSeat(32, false),
            DataSeat(33,true),
            DataSeat(34, false),
            DataSeat(35, true),
            DataSeat(36, true),
            DataSeat(37, false),
            DataSeat(38, false),
            DataSeat(39, true),
            DataSeat(40, true),
            DataSeat(41, false),
            DataSeat(42, true),
            DataSeat(43, false),
            DataSeat(44, true),
            DataSeat(45, false),
            DataSeat(46, false),
            DataSeat(47, true),
            DataSeat(48, true),
            DataSeat(49, false),
            DataSeat(50, true),
            DataSeat(51, false),
            DataSeat(52, true),
            DataSeat(53, true),
            DataSeat(54, false),
            DataSeat(55, true),
            DataSeat(56, true),
            DataSeat(57, true),
            DataSeat(58, false),
            DataSeat(59, false),
            DataSeat(60, true),
            DataSeat(61, true),
            DataSeat(62, false),
            DataSeat(63, false),
            DataSeat(64, true),
            DataSeat(65, true),
            DataSeat(66, true),
            DataSeat(67, false),
            DataSeat(68, true),
            DataSeat(69, false),
            DataSeat(70, false),
            DataSeat(71, true),
            DataSeat(72, true),
        )
    }

    private fun setRecycleView() {
        binding.rvSeat.apply {
            layoutManager = GridLayoutManager(context,6)
            adapter = seatAdapter
        }

        seatAdapter.setListSeat(listSeatPassengers)

        seatAdapter.clickItemSeat = {
            if(it.seatAvaiable){
                val clickedPosition = it.numSeat-1
                seatAdapter.setSelectedItem(clickedPosition)
                Toast.makeText(context, "KLIK $it",Toast.LENGTH_SHORT).show()
            }
        }

    }

}