package com.example.leandropayments.ui

import com.example.leandropayments.data.model.CardIssuer
import com.example.leandropayments.data.model.PaymentMethod

interface SuccessErrorOperation {
    fun loadScreenMethodsPayment(amount: Double)
    fun loadScreenCards(item: PaymentMethod)
    fun loadScreenInstallments(item: CardIssuer)
    fun loadScreenSuccess()
    fun showErrorToast(message: String)
    fun showProgressIndicator(): Boolean
    fun hideProgressIndicator(): Boolean
}