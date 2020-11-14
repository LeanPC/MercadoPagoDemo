package com.example.leandropayments.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leandropayments.data.model.Installment
import com.example.leandropayments.data.networking.ResponseListener
import com.example.leandropayments.domain.InstallmentsUseCase

class InstallmentsViewModel(private val installmentsUserCase: InstallmentsUseCase): ViewModel(),
    ResponseListener<Installment> {
    private val listData = MutableLiveData<List<Installment>>()

    fun setListData(listCardsIssuers:List<Installment>){
        listData.value = listCardsIssuers
    }

    fun getListInstallments(amount: String, methodId: String, issuerId: String){
        installmentsUserCase.getListInstallments(this, amount, methodId, issuerId)
    }

    fun getListInstallmentsLiveData(): LiveData<List<Installment>> {
        return listData
    }

    override fun onResponse(response: List<Installment>) {
        setListData(response)
    }

    override fun onError(t: Throwable?) {
        Log.v("Error", "Error retrofit", t)
    }
}