package com.example.leandropayments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.leandropayments.domain.PaymentOperationUseCase

class ViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ProcessPaymentViewModel::class.java)) {
            return modelClass.getConstructor(PaymentOperationUseCase::class.java).newInstance(PaymentOperationUseCase())
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }
}