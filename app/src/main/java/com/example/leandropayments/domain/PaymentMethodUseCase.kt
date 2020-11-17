package com.example.leandropayments.domain

import com.example.leandropayments.data.model.PaymentMethod
import com.example.leandropayments.data.networking.InteractorPaymentsMethods
import com.example.leandropayments.data.networking.ResponseListener

class PaymentMethodUseCase {

    fun getListMethodsPayments(listener: ResponseListener<PaymentMethod>){
        InteractorPaymentsMethods(listener).execute()
    }
}