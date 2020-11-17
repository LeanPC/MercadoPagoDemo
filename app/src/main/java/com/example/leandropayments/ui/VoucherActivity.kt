package com.example.leandropayments.ui

import android.os.Bundle
import android.view.View
import com.example.leandropayments.R
import kotlinx.android.synthetic.main.activity_voucher.*

class VoucherActivity: BaseActivity() {

    var amountValue: String = "0.00"
    var paymentMethodValue: String = "-"
    var imgCardValue: String = "-"
    var installmentValue: String = "-"
    var rateValue: String = "-"
    var descriptionValue: String = "-"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voucher)

        amountValue = intent.getStringExtra("AMOUNT")
        paymentMethodValue = intent.getStringExtra("PAYMENT_METHOD_NAME")
        imgCardValue = intent.getStringExtra("IMG_CARD")
        installmentValue = intent.getIntExtra("INSTALLMENT", 0).toString()
        rateValue = intent.getDoubleExtra("RATE", 0.0).toString()
        descriptionValue = intent.getStringExtra("DESCRIPTION")

        initFields()
    }

    private fun initFields() {
        value_amount.text = amountValue
        value_payment.text = paymentMethodValue
        value_installment.text = installmentValue
        value_rate.text = rateValue + "%"
        value_description.text = descriptionValue

        btn_end.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

}