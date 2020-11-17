package com.example.leandropayments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leandropayments.data.model.CardIssuer
import com.example.leandropayments.data.model.Installment
import com.example.leandropayments.data.model.PayerCost
import com.example.leandropayments.data.model.PaymentMethod
import com.example.leandropayments.domain.PaymentOperationUseCase

class ProcessPaymentViewModel(private val operationUseCase: PaymentOperationUseCase): ViewModel() {

    private val paymentsMethodsListData = MutableLiveData<List<PaymentMethod>>()
    private val cardsIssuersListData = MutableLiveData<List<CardIssuer>>()
    private val istallmentsListData = MutableLiveData<List<Installment>>()
    lateinit var amountInput: String
    lateinit var paymentMethodSelected: PaymentMethod
    lateinit var cardIssuerSelected: CardIssuer
    lateinit var payerCostSelected: PayerCost

    fun setListDataPaymentMethod(listPaymentMethods: List<PaymentMethod>){
        paymentsMethodsListData.value = listPaymentMethods
    }
    fun getListMethodsPayments(){
        operationUseCase.getListMethodsPayments(this)
    }
    fun getListMethodsPaymentsLiveData(): LiveData<List<PaymentMethod>> {
        return paymentsMethodsListData
    }

    fun setListDataCardsIssuers(listCardsIssuers: List<CardIssuer>){
        cardsIssuersListData.value = listCardsIssuers
    }
    fun getListCardsIssuers(methodId: String){
        operationUseCase.getListCardsIssuers(this, methodId)
    }
    fun getListCardsIssuersLiveData(): LiveData<List<CardIssuer>> {
        return cardsIssuersListData
    }

    fun setListDataInstallments(listCardsIssuers: List<Installment>){
        istallmentsListData.value = listCardsIssuers
    }
    fun getListInstallments(amount: String, methodId: String, issuerId: String){
        operationUseCase.getListInstallments(this, amount, methodId, issuerId)
    }
    fun getListInstallmentsLiveData(): LiveData<List<Installment>> {
        return istallmentsListData
    }
}