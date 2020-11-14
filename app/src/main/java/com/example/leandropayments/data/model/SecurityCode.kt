package com.example.leandropayments.data.model

data class SecurityCode(
    val length: Int,
    val card_location: String,
    val mode: String
)
