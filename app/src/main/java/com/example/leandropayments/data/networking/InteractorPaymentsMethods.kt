package com.example.leandropayments.data.networking

import com.example.leandropayments.data.model.PaymentMethod
import com.example.leandropayments.viewmodel.ProcessPaymentViewModel
import retrofit2.Call
import retrofit2.Response

class InteractorPaymentsMethods(private val listener: ProcessPaymentViewModel): BaseInteractorApiMP<PaymentMethod>() {

    fun execute(){
        mpApiClient.searchMethodsPaymentsByKey(key).enqueue(this)
    }

    override fun onResponse(call: Call<List<PaymentMethod>>, response: Response<List<PaymentMethod>>) {
        try {
            // Example using Non-Null Asserted Call to catch the exception in case of being a null reference
            if (response.isSuccessful && response.body() != null) {
                val methodsPaymentsDataModel = response.body()?: emptyList()
                listener.setListDataPaymentMethod(methodsPaymentsDataModel)
            }
        } catch (e: Exception) {
            //listener.onError(e)
        }
    }

    override fun onFailure(call: Call<List<PaymentMethod>>, t: Throwable) {
        //listener.onError(t)
    }
}