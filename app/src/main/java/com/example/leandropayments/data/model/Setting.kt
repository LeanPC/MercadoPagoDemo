package com.example.leandropayments.data.model

data class Setting(
    val card_number: CardNumber,
    val bin: Bin,
    val security_code: SecurityCode
)
