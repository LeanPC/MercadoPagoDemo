package com.example.leandropayments.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.leandropayments.R
import com.example.leandropayments.utils.Utils
import kotlinx.android.synthetic.main.fragment_amount.*

class AmountFragment(): Fragment(){

    var amount = 0.0
    var dataPasser: SuccessErrorOperation? = null

    companion object {
        /**
         * @return A new instance of fragment MethodsPaymentsFragment.
         */
        fun newInstance() = AmountFragment()
    }

    override fun onAttach(context: Context) {
        if (context != null) {
            super.onAttach(context)
        }
        dataPasser = context as SuccessErrorOperation?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_amount, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_continue.setOnClickListener(View.OnClickListener {
            if(etv_amount. text.toString() != "") {
                amount = etv_amount.text.toString().toDouble()
                if (Utils.isValidAmount(amount)) {
                    dataPasser?.loadScreenMethodsPayment(amount)
                } else {
                    dataPasser?.showErrorToast(resources.getString(R.string.amount_invalid_error))
                }
            } else {
                dataPasser?.showErrorToast(resources.getString(R.string.amount_null_error))
            }
        })
    }
}