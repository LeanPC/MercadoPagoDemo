package com.example.leandropayments.data.networking

import com.example.leandropayments.data.model.CardIssuer
import com.example.leandropayments.data.model.Installment
import com.example.leandropayments.data.model.PaymentMethod
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServicesMP {

    @GET("payment_methods")
    fun searchMethodsPaymentsByKey(
        @Query("public_key") key: String
    ): Call<List<PaymentMethod>>

    @GET("payment_methods/card_issuers")
    fun searchCardsByKey(
        @Query("public_key") key: String,
        @Query("payment_method_id") method_id: String
    ): Call<List<CardIssuer>>

    @GET("payment_methods/installments")
    fun searchInstallmenstsByKey(
        @Query("public_key") username: String,
        @Query("amount") amount: String,
        @Query("payment_method_id") method_id: String,
        @Query("issuer.id") issuer_id: String
    ): Call<List<Installment>>
}