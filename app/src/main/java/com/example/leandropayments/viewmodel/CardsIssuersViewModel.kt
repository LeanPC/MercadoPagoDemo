package com.example.leandropayments.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leandropayments.data.model.CardIssuer
import com.example.leandropayments.data.networking.ResponseListener
import com.example.leandropayments.domain.CardIssuerCase

class CardsIssuersViewModel(private val cardIssuerUseCase: CardIssuerCase): ViewModel(),
    ResponseListener<CardIssuer> {

    private val listData = MutableLiveData<List<CardIssuer>>()

    fun setListData(listCardsIssuers:List<CardIssuer>){
        listData.value = listCardsIssuers
    }

    fun getListCardsIssuers(methodId: String){
        cardIssuerUseCase.getListCardsIssuers(this, methodId)
    }

    fun getListCardsIssuersLiveData(): LiveData<List<CardIssuer>> {
        return listData
    }

    override fun onResponse(response: List<CardIssuer>) {
        setListData(response)
    }

    override fun onError(t: Throwable?) {
        Log.v("Error", "Error retrofit", t)
    }
}