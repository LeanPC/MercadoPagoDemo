package com.example.leandropayments.ui

import androidx.fragment.app.Fragment
import com.example.leandropayments.data.model.CardIssuer
import com.example.leandropayments.data.model.Installment
import com.example.leandropayments.data.model.PayerCost
import com.example.leandropayments.data.model.PaymentMethod

interface SuccessErrorOperation {
    fun loadScreenPaymentMethods()
    fun loadScreenCards()
    fun loadScreenInstallments()
    fun loadScreenSuccess()
    fun showErrorToast(message: String)
    fun showProgressIndicator(): Boolean
    fun hideProgressIndicator(): Boolean
}