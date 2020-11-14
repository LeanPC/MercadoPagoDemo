package com.example.leandropayments.data.model

data class PaymentMethod(
    val id: String,
    val name: String,
    val payment_type_id: String,
    val status: String,
    val secure_thumbnail: String,
    val thumbnail: String,
    val deferred_capture: String,
    val settings: List<Setting>,
    val additional_info_needed: List<String>,
    val min_allowed_amount: Double,
    val max_allowed_amount: Double,
    val accreditation_time: Double,
    val financial_institutions: List<String>,
    val processing_modes: List<String>
)