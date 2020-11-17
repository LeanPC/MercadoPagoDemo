package com.example.leandropayments.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leandropayments.domain.PaymentUseCase
import com.example.leandropayments.data.model.PaymentMethod
import com.example.leandropayments.data.networking.ResponseListener

class PaymentsMethodsViewModel(private val paymentUseCase: PaymentUseCase): ViewModel(), ResponseListener<PaymentMethod>{

    private val listData = MutableLiveData<List<PaymentMethod>>()

    fun setListData(listPaymentMethods: List<PaymentMethod>){
        listData.value = listPaymentMethods
    }

    fun getListMethodsPayments(){
        paymentUseCase.getListMethodsPayments(this)
    }

    fun getListMethodsPaymentsLiveData(): LiveData<List<PaymentMethod>>{
        return listData
    }

    override fun onResponse(response: List<PaymentMethod>) {
        setListData(response)
    }

    override fun onError(t: Throwable?) {
        Log.v("Error", "Error retrofit", t)
    }
}