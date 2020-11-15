package com.example.leandropayments.ui

import androidx.fragment.app.Fragment
import com.example.leandropayments.data.model.CardIssuer
import com.example.leandropayments.data.model.PaymentMethod

interface SuccessErrorOperation {
    fun loadScreenPaymentMethods(amount: Double, fragment: Fragment)
    fun loadScreenCards(item: PaymentMethod, fragment: Fragment)
    fun loadScreenInstallments(item: CardIssuer, fragment: Fragment)
    fun loadScreenSuccess()
    fun showErrorToast(message: String)
    fun showProgressIndicator(): Boolean
    fun hideProgressIndicator(): Boolean
}