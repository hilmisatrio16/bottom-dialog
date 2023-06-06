package com.example.latihanfp1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.latihanfp1.model.ResponseDataCity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationViewModel : ViewModel() {
    private var _dataDestinationTicket : MutableLiveData<List<ResponseDataCity>> = MutableLiveData()
    val dataDestinationTicket : LiveData<List<ResponseDataCity>> = _dataDestinationTicket

    fun getAllDataDestination(){
        ClientService.instance.getDataCity().enqueue(object : Callback<List<ResponseDataCity>>{
            override fun onResponse(
                call: Call<List<ResponseDataCity>>,
                response: Response<List<ResponseDataCity>>
            ) {
                if(response.isSuccessful){
                    _dataDestinationTicket.postValue(response.body())
                }else{
                    _dataDestinationTicket.postValue(null)
                }
            }

            override fun onFailure(call: Call<List<ResponseDataCity>>, t: Throwable) {
                _dataDestinationTicket.postValue(null)
            }

        })
    }
}