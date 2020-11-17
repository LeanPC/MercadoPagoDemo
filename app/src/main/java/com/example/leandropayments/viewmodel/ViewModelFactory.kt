package com.example.leandropayments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.*
import com.example.leandropayments.domain.CardIssuerCase
import com.example.leandropayments.domain.InstallmentsUseCase
import com.example.leandropayments.domain.PaymentMethodUseCase

class ViewModelFactory(): Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(PaymentsMethodsViewModel::class.java)) {
            return modelClass.getConstructor(PaymentMethodUseCase::class.java).newInstance(PaymentMethodUseCase())

        } else if(modelClass.isAssignableFrom(CardsIssuersViewModel::class.java)){
            return modelClass.getConstructor(CardIssuerCase::class.java).newInstance(CardIssuerCase())

        } else if(modelClass.isAssignableFrom(InstallmentsViewModel::class.java)){
            return modelClass.getConstructor(InstallmentsUseCase::class.java).newInstance(InstallmentsUseCase())
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }
}