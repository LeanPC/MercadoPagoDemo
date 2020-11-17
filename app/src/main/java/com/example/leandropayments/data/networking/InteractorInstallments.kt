package com.example.leandropayments.data.networking

import com.example.leandropayments.data.model.Installment
import retrofit2.Call
import retrofit2.Response

class InteractorInstallments(private val listener: ResponseListener<Installment>): BaseInteractorApiMP<Installment>() {

    fun execute(amount: String, methodId: String, issuerId: String){
        //mpApiClient.searchInstallmenstsByKey(key, amount, methodId, issuerId).enqueue(this)
        mpApiClient.searchInstallmenstsByKey(key, "3000", "visa", "288").enqueue(this)
    }

    override fun onResponse(call: Call<List<Installment>>, response: Response<List<Installment>>) {
        try {
            // Example using Non-Null Asserted Call to catch the exception in case of being a null reference
            if (response.isSuccessful && response.body() != null) {
                val installmentsDataModel = response.body()
                if (installmentsDataModel != null) {
                    listener.onResponse(installmentsDataModel)
                }
            }
        } catch (e: Exception) {
            listener.onError(e)
        }
    }

    override fun onFailure(call: Call<List<Installment>>, t: Throwable) {
        listener.onError(t)
    }
}
