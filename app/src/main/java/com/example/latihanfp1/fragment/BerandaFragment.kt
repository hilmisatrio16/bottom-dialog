package com.example.latihanfp1.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanfp1.DestinationViewModel
import com.example.latihanfp1.R
import com.example.latihanfp1.adapter.AdapterDestinationFavorite
import com.example.latihanfp1.adapter.DestinationAdapter
import com.example.latihanfp1.databinding.DateDialogLayoutBinding
import com.example.latihanfp1.databinding.FragmentBerandaBinding
import com.example.latihanfp1.databinding.PassangerDialogLayoutBinding
import com.example.latihanfp1.databinding.SearchDialogLayoutBinding
import com.example.latihanfp1.databinding.SeatclassDialogLayoutBinding
import com.example.latihanfp1.model.DataDestinationFavorite
import com.example.latihanfp1.model.ResponseDataCity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.timessquare.CalendarPickerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BerandaFragment : Fragment() {

    private lateinit var binding : FragmentBerandaBinding
    private lateinit var adapterDestinationFavorite: AdapterDestinationFavorite
    private lateinit var destinationViewModel: DestinationViewModel
    private var listDestination = mutableListOf<ResponseDataCity>()
    private lateinit var destinationAdapter : DestinationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBerandaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterDestinationFavorite = AdapterDestinationFavorite(setListDestinationFavorite())
        destinationAdapter = DestinationAdapter(ArrayList())
        destinationViewModel = ViewModelProvider(this).get(DestinationViewModel::class.java)
        setRecycleviewDestinationFavorite()

        binding.setDeparture.setOnClickListener {
            setLocationFlight("departure")
        }
        
        binding.setArrival.setOnClickListener {
            setLocationFlight("arrival")
        }

        binding.btnChange.setOnClickListener {
            val animBtn = AnimationUtils.loadAnimation(context, R.anim.anim_rotate)
            binding.btnChange.startAnimation(animBtn)
            setChangePosition()
        }

        binding.optionFlight.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                binding.setDateReturn.visibility = View.VISIBLE
            }else{
                binding.setDateReturn.visibility = View.GONE
            }
        }

        binding.setPassangers.setOnClickListener {
            setPassengers()
        }

        binding.setSeatClass.setOnClickListener {
            setSeatClassPassengers()
        }

        binding.setDateDeparture.setOnClickListener {
            setDateFlight(binding.optionFlight.isChecked)
        }

        binding.setDateReturn.setOnClickListener {
            setDateFlight(binding.optionFlight.isChecked)
        }

    }

    private fun setDateFlight(checked : Boolean) {
        //nanti dikasih kondisi apakah tanggal pulang pergi atau single flight
        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.date_dialog_layout)
        val bindingDialog = DateDialogLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        dateFlight(bindingDialog, checked)

        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
        }
        bindingDialog.btnSaveDate.setOnClickListener {
            binding.tvDateDeparture.text = bindingDialog.tvDepartureDate.text
            binding.tvDateReturn.text = bindingDialog.tvReturnDate.text
            binding.tvDateReturn.setTextColor(Color.BLACK)
            dialog.dismiss()
        }
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);
    }

    private fun dateFlight(bindingDialog: DateDialogLayoutBinding, checked: Boolean) {
        val startDate = Date()
        val endDate = Date()
        val nextYear = Calendar.getInstance()
        nextYear.add(Calendar.YEAR, 1)

        if(checked){
            bindingDialog.dateFlight.init(startDate,nextYear.time)
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(startDate)
        }else{
            bindingDialog.layoutDateReturn.visibility = View.GONE
            bindingDialog.dateFlight.init(startDate,nextYear.time)
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(startDate)
        }

        bindingDialog.dateFlight.setOnDateSelectedListener(object : CalendarPickerView.OnDateSelectedListener {
            override fun onDateSelected(date: Date) {
                val selectedDates = bindingDialog.dateFlight.selectedDates
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                if (selectedDates.size >= 2) {
                    val dateDeparture = selectedDates[0]
                    val dateReturn = selectedDates[selectedDates.size - 1]
                    bindingDialog.tvDepartureDate.text = convertDateFormatID(dateFormat.format(dateDeparture).toString())
                    bindingDialog.tvReturnDate.text = convertDateFormatID(dateFormat.format(dateReturn).toString())
                }else{

                    bindingDialog.tvDepartureDate.text = convertDateFormatID(dateFormat.format(date).toString())
                }
            }

            override fun onDateUnselected(date: Date) {
                // Tindakan saat tanggal yang dipilih dibatalkan (tidak digunakan dalam mode selection range)
            }
        })

    }
    fun convertDateFormatID(dateString: String): String {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formatDateID = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        val date: Date = inputFormat.parse(dateString) as Date
        return formatDateID.format(date)
    }

    private fun setSeatClassPassengers() {

        //ketika klik set seat masih tidak menyimpan pilihan sementara di antara 4 pilihan tersebut

        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.seatclass_dialog_layout)
        val bindingDialog = SeatclassDialogLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        var seatClass = "Economy"
        //
        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
        }
        bindingDialog.layoutEconomy.setOnClickListener {
            setChoiceClass(0, bindingDialog)
            seatClass = "Economy"
        }

        bindingDialog.layoutPremiumEconomy.setOnClickListener {
            setChoiceClass(1, bindingDialog)
            seatClass = "Premium Economy"
        }
        bindingDialog.layoutBusiness.setOnClickListener {
            setChoiceClass(2, bindingDialog)
            seatClass = "Business"
        }
        bindingDialog.layoutFirstClass.setOnClickListener {
            setChoiceClass(3, bindingDialog)
            seatClass = "First Class"
        }

        bindingDialog.btnSaveClass.setOnClickListener {
            binding.tvSeatClass.text = seatClass
            dialog.dismiss()
        }
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);
    }
    private fun setChoiceClass(i: Int, bindingDialog : SeatclassDialogLayoutBinding)  {

        val listLayout : List<RelativeLayout> = listOf(
            bindingDialog.layoutEconomy, bindingDialog.layoutPremiumEconomy, bindingDialog.layoutBusiness, bindingDialog.layoutFirstClass
        )
        val listTvClass : List<TextView> = listOf(
            bindingDialog.tvEconomy, bindingDialog.tvPremiumEconomy, bindingDialog.tvBusiness, bindingDialog.tvFirstClass
        )
        val listTvPrice : List<TextView> = listOf(
            bindingDialog.tvPriceEconomy, bindingDialog.tvPricePremiumEconomy, bindingDialog.tvPriceBusiness, bindingDialog.tvPriceFristClass
        )
        val listImgCeklis : List<ImageView> = listOf(
            bindingDialog.icCeklisEconomy, bindingDialog.icCeklisPremiumEconomy, bindingDialog.icCeklisBusiness, bindingDialog.icCeklisFirstClass
        )

        for (index in listLayout.indices){
            if(i == index){
                listLayout[index].setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary_purple_5))
                listTvClass[index].setTextColor(Color.WHITE)
                listTvPrice[index].setTextColor(Color.WHITE)
                listImgCeklis[index].visibility = View.VISIBLE
            }else{
                listLayout[index].setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                listTvClass[index].setTextColor(Color.BLACK)
                listTvPrice[index].setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_purple_4))
                listImgCeklis[index].visibility = View.GONE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setPassengers() {
        //bottom sheet
        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.passanger_dialog_layout)
        val bindingDialog = PassangerDialogLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)
        //
        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        setTotalPassengers(bindingDialog)


        bindingDialog.btnSaveSeatPassenger.setOnClickListener {
            //apakah baby dihitung ???
            //simpan hasil total dan komposisi jumlah penumpang dengan livedata<List> di viewmodel
            val totalPassenger = countPassengers(bindingDialog.tvPassangerAdult,bindingDialog.tvPassangerChild,bindingDialog.tvPassangerBaby)
            binding.tvPassengers.text = "${totalPassenger.toString()} Penumpang"
            Log.d("OK","SAVE")
            dialog.dismiss()
        }
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);
    }

    private fun setTotalPassengers(bindingDialog: PassangerDialogLayoutBinding) {
        bindingDialog.addPassangerAdult.setOnClickListener {
            val totalAdult = bindingDialog.tvPassangerAdult.text.toString().toInt() + 1
            bindingDialog.tvPassangerAdult.text = totalAdult.toString()
        }

        bindingDialog.addPassangerChild.setOnClickListener {
            val totalChild = bindingDialog.tvPassangerChild.text.toString().toInt() + 1
            bindingDialog.tvPassangerChild.text = totalChild.toString()
        }
        bindingDialog.addPassangerBaby.setOnClickListener {
            val totalBaby = bindingDialog.tvPassangerBaby.text.toString().toInt() + 1
            bindingDialog.tvPassangerBaby.text = totalBaby.toString()
        }

        bindingDialog.decPassangerAdult.setOnClickListener {
            var totalAdult = bindingDialog.tvPassangerAdult.text.toString().toInt()
            if(totalAdult > 1){
                totalAdult -= 1
            }
            bindingDialog.tvPassangerAdult.text = totalAdult.toString()
        }

        bindingDialog.decPassangerChild.setOnClickListener {
            var totalChild = bindingDialog.tvPassangerChild.text.toString().toInt()
            if(totalChild > 1){
                totalChild -= 1
            }
            bindingDialog.tvPassangerChild.text = totalChild.toString()
        }
        bindingDialog.decPassangerBaby.setOnClickListener {
            var totalBaby = bindingDialog.tvPassangerBaby.text.toString().toInt()
            if(totalBaby > 1){
                totalBaby -= 1
            }
            bindingDialog.tvPassangerBaby.text = totalBaby.toString()
        }


    }

    private fun countPassengers(vararg passangers : TextView) : Int{
        var count = 0
        for(item in passangers){
            count += item.text.toString().toInt()
        }
        return count
    }

    private fun setChangePosition() {
        val departure = binding.tvDeparture.text
        val arrival = binding.tvArrival.text

        binding.tvDeparture.text = arrival
        binding.tvArrival.text = departure
    }

    private fun setLocationFlight(action: String) {

        //nanti hasil dari set di beranda ini disimpan di viewmodel sehingga ketika ada perubahan state tidak hilang datanya

        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.search_dialog_layout)
        val bindingDialog = SearchDialogLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        bindingDialog.rvDestination.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
            adapter = destinationAdapter
        }

        destinationViewModel.getAllDataDestination()
        destinationViewModel.dataDestinationTicket.observe(viewLifecycleOwner){
            if(it != null){
                listDestination.clear()
                destinationAdapter.setDataDestination(it)
                listDestination.addAll(it)
            }
        }

        bindingDialog.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterDestination(newText)
                return true
            }

        })

        destinationAdapter.onClickDestination = {
            if (action == "departure"){
                binding.tvDeparture.text = it.destination.toString()
            }else{
                binding.tvArrival.text = it.destination.toString()
            }
            dialog.dismiss()
        }
        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);

    }

    private fun setRecycleviewDestinationFavorite() {
        binding.rvFavoriteDestination.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterDestinationFavorite
        }

    }

    private fun setListDestinationFavorite(): List<DataDestinationFavorite> {
        return mutableListOf(
            DataDestinationFavorite(1, "Jakarat -> Bandung", "Batik Air", "23 Mei 2023", 1200000),
            DataDestinationFavorite(1, "Makassar -> Bandung", "Citilink", "23 Mei 2023", 1200000),
            DataDestinationFavorite(1, "Surabaya -> Palembang", "Superjet", "23 Mei 2023", 1200000),
            DataDestinationFavorite(1, "Semarang -> Bandung", "Lion Air", "23 Mei 2023", 1200000),
            DataDestinationFavorite(1, "Jakarat -> Surabaya", "Garuda Indonesia", "23 Mei 2023", 1200000),
            DataDestinationFavorite(1, "Jakarat -> Bali", "Air Asia", "23 Mei 2023", 1200000),
        )
    }

    private fun filterDestination(newText: String?) {
        val listSearchDestination = mutableListOf<ResponseDataCity>()
        for(item in listDestination){
            if(item.destination.toLowerCase().contains(newText!!.toLowerCase())){
                listSearchDestination.add(item)
            }
        }

        if(listSearchDestination.isNotEmpty()){
            destinationAdapter.setDataDestination(listSearchDestination)
        }
    }


}