package com.example.leandropayments.utils

class Utils {

    companion object {
        //      Si se implementa un formatter con . y , como separadores de miles y decimales
        //    fun formatterAmountDouble(amountString: String): Double{
        //        return amountString.toDouble()
        //    }

        fun isValidAmount(amount: Double): Boolean{
            return amount > 0
        }
    }
}