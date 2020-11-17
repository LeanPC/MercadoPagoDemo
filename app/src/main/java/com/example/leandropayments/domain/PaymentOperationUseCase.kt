package com.example.leandropayments.domain

import com.example.leandropayments.data.networking.InteractorCardsIssuers
import com.example.leandropayments.data.networking.InteractorInstallments
import com.example.leandropayments.data.networking.InteractorPaymentsMethods
import com.example.leandropayments.viewmodel.ProcessPaymentViewModel

class PaymentOperationUseCase {

    fun getListMethodsPayments(listener: ProcessPaymentViewModel){
        InteractorPaymentsMethods(listener).execute()
    }

    fun getListCardsIssuers(listener: ProcessPaymentViewModel, methodId: String){
        InteractorCardsIssuers(listener).execute(methodId)
    }

    fun getListInstallments(listener: ProcessPaymentViewModel, amount: String, methodId: String, issuerId: String){
        InteractorInstallments(listener).execute(amount ,methodId, issuerId)
    }
}