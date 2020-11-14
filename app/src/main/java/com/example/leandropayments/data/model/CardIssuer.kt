package com.example.leandropayments.data.model

data class CardIssuer(
    val id: String,
    val name: String,
    val secure_thumbnail: String,
    val thumbnail: String,
    val processing_mode: String,
    val merchant_account_id: String
)