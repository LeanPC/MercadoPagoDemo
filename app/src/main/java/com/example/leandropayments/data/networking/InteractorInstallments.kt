package com.example.leandropayments.data.networking

import com.example.leandropayments.data.model.Installment
import com.example.leandropayments.viewmodel.ProcessPaymentViewModel
import retrofit2.Call
import retrofit2.Response

class InteractorInstallments(private val listener: ProcessPaymentViewModel): BaseInteractorApiMP<Installment>() {

    fun execute(amount: String, methodId: String, issuerId: String){
        mpApiClient.searchInstallmenstsByKey(key, amount, methodId, issuerId).enqueue(this)
    }

    override fun onResponse(call: Call<List<Installment>>, response: Response<List<Installment>>) {
        try {
            // Example using Non-Null Asserted Call to catch the exception in case of being a null reference
            if (response.isSuccessful && response.body() != null) {
                val installmentsDataModel = response.body()
                if (installmentsDataModel != null) {
                    listener.setListDataInstallments(installmentsDataModel)
                }
            }
        } catch (e: Exception) {
            //listener.onError(e)
        }
    }

    override fun onFailure(call: Call<List<Installment>>, t: Throwable) {
        //listener.onError(t)
    }
}
