package com.example.leandropayments.data.model

data class Installment(
    var payment_method_id: String,
    var payment_type_id: String,
    var issuer: Issuer,
    var processing_mode: String,
    var merchant_account_id: String,
    var payer_costs: List<PayerCost>,
    var agreements: String
)