package com.example.leandropayments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.*
import com.example.leandropayments.domain.CardIssuerCase
import com.example.leandropayments.domain.PaymentUseCase

class ViewModelFactory(): Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(PaymentsMethodsViewModel::class.java)) {
            return modelClass.getConstructor(PaymentUseCase::class.java).newInstance(PaymentUseCase())

        } else if(modelClass.isAssignableFrom(CardsIssuersViewModel::class.java)){
            return modelClass.getConstructor(CardIssuerCase::class.java).newInstance(CardIssuerCase())
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }
}