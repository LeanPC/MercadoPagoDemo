package com.example.leandropayments.ui


import android.os.Bundle
import android.view.View
import com.example.leandropayments.R
import com.example.leandropayments.data.model.CardIssuer
import com.example.leandropayments.data.model.PaymentMethod
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope

class MainActivity : BaseActivity(), SuccessErrorOperation, CoroutineScope {

    private var amountValid: Double = 0.0
    private lateinit var paymentMethodSelected: PaymentMethod
    private lateinit var cardIssuerSelected: CardIssuer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            initTextsStates()
            loadAmountScreen()
        }
    }

    private fun initTextsStates(){
        tv_state_amount.visibility = View.VISIBLE
        tv_state_methods_payments.visibility = View.GONE
        tv_state_cards.visibility = View.GONE
        tv_state_installments.visibility = View.GONE
    }

    private fun loadAmountScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AmountFragment.newInstance(), "")
            .commit()
    }

    override fun loadScreenMethodsPayment(amount: Double) {
        tv_state_methods_payments.visibility = View.VISIBLE
        amountValid = amount
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PaymentsMethodsFragment.newInstance(), "")
            .addToBackStack(null)
            .commit()
    }

    override fun loadScreenCards(item: PaymentMethod) {
        paymentMethodSelected = item
        tv_state_cards.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CardsIssuersFragment.newInstance(), "")
            .addToBackStack(null)
            .commit()
    }

    override fun loadScreenInstallments(item: CardIssuer) {
        cardIssuerSelected = item
        tv_state_installments.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PaymentsMethodsFragment.newInstance(), "")
            .addToBackStack(null)
            .commit()
    }

    override fun loadScreenSuccess() {
        initTextsStates()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PaymentsMethodsFragment.newInstance(), "")
            .addToBackStack(null)
            .commit()
    }
}