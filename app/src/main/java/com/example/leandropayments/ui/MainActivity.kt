package com.example.leandropayments.ui


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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
            loadScreenAmount()
        }
    }

    private fun initTextsStates(){
        tv_state_amount.visibility = View.VISIBLE
        tv_state_methods_payments.visibility = View.GONE
        tv_state_cards.visibility = View.GONE
        tv_state_installments.visibility = View.GONE
    }

    private fun loadScreenAmount() {
        val frg = AmountFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, AmountFragment.newInstance(), frg::class.java.name)
            .commit()
    }

    override fun loadScreenPaymentMethods(amount: Double, fragment: Fragment) {
        tv_state_methods_payments.visibility = View.VISIBLE
        amountValid = amount

        val frg = PaymentsMethodsFragment.newInstance()
        frg.retainInstance = true

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PaymentsMethodsFragment.newInstance(), frg::class.java.name)
            //Remember Previous fragment
            .addToBackStack(fragment::class.java.name)
            .commit()
    }

    override fun loadScreenCards(item: PaymentMethod, fragment: Fragment) {
        paymentMethodSelected = item
        val bundle = Bundle()
        bundle.putString("paymentMethodId", paymentMethodSelected.id)
        tv_state_cards.visibility = View.VISIBLE

        val frg = CardsIssuersFragment.newInstance(paymentMethodSelected.id)
        frg.retainInstance = true
        frg.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, frg, "")
            //Remember Previous fragment
            .addToBackStack(fragment::class.java.name)
            .commit()
    }

    override fun loadScreenInstallments(item: CardIssuer, fragment: Fragment) {
        cardIssuerSelected = item
        tv_state_installments.visibility = View.VISIBLE

        val frg = PaymentsMethodsFragment.newInstance()
        frg.retainInstance = true

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PaymentsMethodsFragment.newInstance(), frg::class.java.name)
            //Remember Previous fragment
            .addToBackStack(fragment::class.java.name)
            .commit()
    }

    override fun loadScreenSuccess() {
        initTextsStates()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, PaymentsMethodsFragment.newInstance(), "")
            .addToBackStack(null)
            .commit()
    }
}