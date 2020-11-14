package com.example.leandropayments.domain

import com.example.leandropayments.data.model.Installment
import com.example.leandropayments.data.networking.InteractorInstallments
import com.example.leandropayments.data.networking.ResponseListener

class InstallmentsUseCase {
    fun getListInstallments(listener: ResponseListener<Installment>, amount: String, methodId: String, issuerId: String){
        InteractorInstallments(listener).execute(amount ,methodId, issuerId)
    }
}
