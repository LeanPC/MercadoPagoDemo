package com.example.leandropayments.data.networking

import com.example.leandropayments.data.model.PaymentMethod
import com.example.leandropayments.data.networking.BaseInteractorApiMP
import com.example.leandropayments.data.networking.ResponseListener
import retrofit2.Call
import retrofit2.Response

class InteractorPaymentsMethods(private val listener: ResponseListener<PaymentMethod>): BaseInteractorApiMP<PaymentMethod>() {

    fun execute(){
        mpApiClient.searchMethodsPaymentsByKey(key).enqueue(this)
    }

    override fun onResponse(call: Call<List<PaymentMethod>>, response: Response<List<PaymentMethod>>) {
        try {
            // Example using Non-Null Asserted Call to catch the exception in case of being a null reference
            if (response.isSuccessful && response.body() != null) {
                val methodsPaymentsDataModel = response.body()?: emptyList()
                listener.onResponse(methodsPaymentsDataModel)
            }
        } catch (e: Exception) {
            listener.onError(e)
        }
    }

    override fun onFailure(call: Call<List<PaymentMethod>>, t: Throwable) {
        listener.onError(t)
    }
}