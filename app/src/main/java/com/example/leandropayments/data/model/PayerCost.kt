package com.example.leandropayments.data.model

data class PayerCost(
    var installments: Int,
    var installment_rate: Double,
    var discount_rate: Double,
    var reimbursement_rate: Double,
    var labels: List<String>,
    var installment_rate_collector: List<String>,
    var min_allowed_amount: Double,
    var max_allowed_amount: Double,
    var recommended_message: String,
    var installment_amount: Double,
    var total_amount: Double,
    var payment_method_option_id: String
)
