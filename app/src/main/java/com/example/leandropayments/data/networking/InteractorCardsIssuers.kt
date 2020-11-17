package com.example.leandropayments.data.networking

import com.example.leandropayments.data.model.CardIssuer
import com.example.leandropayments.viewmodel.ProcessPaymentViewModel
import retrofit2.Call
import retrofit2.Response

class InteractorCardsIssuers(private val listener: ProcessPaymentViewModel): BaseInteractorApiMP<CardIssuer>() {

    fun execute(methodId: String){
        mpApiClient.searchCardsByKey(key, methodId).enqueue(this)
    }

    override fun onResponse(call: Call<List<CardIssuer>>, response: Response<List<CardIssuer>>) {
        try {
            // Example using Non-Null Asserted Call to catch the exception in case of being a null reference
            if (response.isSuccessful && response.body() != null) {
                val cardsIssuersDataModel = response.body()?: emptyList()
                    listener.setListDataCardsIssuers(cardsIssuersDataModel)
            }
        } catch (e: Exception) {
            //listener.onError(e)
        }
    }

    override fun onFailure(call: Call<List<CardIssuer>>, t: Throwable) {
        //listener.onError(t)
    }
}
