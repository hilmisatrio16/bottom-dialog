package com.example.latihanfp1

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanfp1.adapter.DestinationAdapter
import com.example.latihanfp1.databinding.ActivityMainBinding
import com.example.latihanfp1.databinding.DateDialogLayoutBinding
import com.example.latihanfp1.databinding.PassangerDialogLayoutBinding
import com.example.latihanfp1.databinding.SearchDialogLayoutBinding
import com.example.latihanfp1.databinding.SeatclassDialogLayoutBinding
import com.example.latihanfp1.model.ResponseDataCity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.MaterialDatePicker

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var destinationAdapter: DestinationAdapter
    private lateinit var destinationViewModel : DestinationViewModel
    private var listDestination = mutableListOf<ResponseDataCity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        destinationAdapter = DestinationAdapter(ArrayList())

        destinationViewModel = ViewModelProvider(this).get(DestinationViewModel::class.java)

        binding.btnKlik1.setOnClickListener {
            showDialogSearchDestination()
        }

        binding.btnKlik2.setOnClickListener {
            showDataPicker()
            //https://github.com/material-components/material-components-android/blob/master/docs/components/DatePicker.md

        }

        binding.btnKlik3.setOnClickListener {
            showDialogPassenger()
        }

        binding.btnKlik4.setOnClickListener {
            showDialogClass()
        }
    }

    private fun showDialogClass() {
        val dialog = BottomSheetDialog(this)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.seatclass_dialog_layout)
        val bindingDialog = SeatclassDialogLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)
        //
        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
        }
        bindingDialog.layoutEconomy.setOnClickListener {
            setChoiceClass(0, bindingDialog)
//            bindingDialog.layoutEconomy.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_purple_5))
//            bindingDialog.tvEconomy.setTextColor(Color.WHITE)
//            bindingDialog.tvPriceEconomy.setTextColor(Color.WHITE)
//            bindingDialog.icCeklisEconomy.visibility = View.VISIBLE
        }

        bindingDialog.layoutPremiumEconomy.setOnClickListener {
            setChoiceClass(1, bindingDialog)
        }
        bindingDialog.layoutBusiness.setOnClickListener {
            setChoiceClass(2, bindingDialog)
        }
        bindingDialog.layoutFirstClass.setOnClickListener {
            setChoiceClass(3, bindingDialog)
        }
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);

    }

    private fun setChoiceClass(i: Int, bindingDialog : SeatclassDialogLayoutBinding) {
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
                listLayout[index].setBackgroundColor(ContextCompat.getColor(this, R.color.primary_purple_5))
                listTvClass[index].setTextColor(Color.WHITE)
                listTvPrice[index].setTextColor(Color.WHITE)
                listImgCeklis[index].visibility = View.VISIBLE
            }else{
                listLayout[index].setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                listTvClass[index].setTextColor(Color.BLACK)
                listTvPrice[index].setTextColor(ContextCompat.getColor(this, R.color.primary_purple_4))
                listImgCeklis[index].visibility = View.GONE
            }
        }
    }

    private fun showDialogPassenger() {

        //bottom sheet
        val dialog = BottomSheetDialog(this)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.passanger_dialog_layout)
        val bindingDialog = PassangerDialogLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)
        //
        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
        }


        bindingDialog.btnSaveSeatPassenger.setOnClickListener {
//            Snackbar.make(bindingDialog.passLayout,"Active", Snackbar.LENGTH_SHORT)
//                .setTextColor(Color.WHITE)
//                .setBackgroundTint(Color.BLUE)
//                .show()
            Toast.makeText(this, "SAVE", Toast.LENGTH_SHORT).show()

            Log.d("OK","SAVE")
        }
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);
    }

    private fun showDataPicker() {
        val dialog = Dialog(this)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.date_dialog_layout)
        val bindingDialog = DateDialogLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        //

        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setSelection(
                    Pair.create(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds())
                )
                .build()

        dateRangePicker.show(supportFragmentManager,dateRangePicker.toString())

        //
        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);

    }

    private fun showDialogSearchDestination() {

        //dialog
        val dialog = Dialog(this)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.search_dialog_layout)
        val bindingDialog = SearchDialogLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        bindingDialog.rvDestination.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
            adapter = destinationAdapter
        }

        destinationViewModel.getAllDataDestination()
        destinationViewModel.dataDestinationTicket.observe(this){
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
            Toast.makeText(this, "Pilih Destination ${it.destination}", Toast.LENGTH_SHORT).show()
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