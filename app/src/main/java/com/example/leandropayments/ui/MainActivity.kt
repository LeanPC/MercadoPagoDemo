package com.example.leandropayments.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.leandropayments.R
import com.example.leandropayments.data.model.CardIssuer
import com.example.leandropayments.data.model.PayerCost
import com.example.leandropayments.data.model.PaymentMethod
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope

class MainActivity : BaseActivity(), SuccessErrorOperation, CoroutineScope {

    private var amountValid: Double = 0.0
    private lateinit var paymentMethodSelected: PaymentMethod
    private lateinit var cardIssuerSelected: CardIssuer
    private lateinit var payerCostSelected: PayerCost
    private lateinit var currentFragment: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            initStepsImg()
            loadScreenAmount()
        }
    }

    private fun loadScreenAmount() {

        val frg = AmountFragment.newInstance()
        frg.retainInstance = true
        currentFragment = "amountFragment"

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, frg, currentFragment)
            .commit()
    }

    override fun loadScreenPaymentMethods(amount: Double, fragment: Fragment) {
        img_step2.setImageResource(R.drawable.ic_step_operation_image_complete)
        amountValid = amount

        val frg = PaymentsMethodsFragment.newInstance()
        frg.retainInstance = true
        currentFragment = "paymentMethodFragment"

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, frg, currentFragment)
            .addToBackStack(currentFragment)
            .commit()
    }

    override fun loadScreenCards(item: PaymentMethod, fragment: Fragment) {
        img_step3.setImageResource(R.drawable.ic_step_operation_image_complete)

        paymentMethodSelected = item
        val bundle = Bundle()
        bundle.putString("paymentMethodId", paymentMethodSelected.id)

        val frg = CardsIssuersFragment.newInstance(paymentMethodSelected.id)
        frg.retainInstance = true
        frg.arguments = bundle
        currentFragment = "cardsFragment"

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, frg, currentFragment)
            .addToBackStack(currentFragment)
            .commit()
    }

    override fun loadScreenInstallments(item: CardIssuer, fragment: Fragment) {
        img_step4.setImageResource(R.drawable.ic_step_operation_image_complete)
        cardIssuerSelected = item
        currentFragment = "installmentsFragment"

        val frg = InstallmentsFragment.newInstance()
        frg.retainInstance = true

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, frg, currentFragment)
            .addToBackStack(currentFragment)
            .commit()
    }

    override fun loadScreenSuccess(item: PayerCost, fragment: Fragment) {
        //como no hay servicio de transaccion OK de la operacion
        //se inicializa la pantalla de monto para que al volver este listo de nuevo de lo contrario se deberia manejar en el back de fragments
        payerCostSelected = item
        initStepsImg()

        currentFragment = "amountFragment"
        supportFragmentManager.popBackStack(currentFragment, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, AmountFragment.newInstance(), currentFragment)
            .addToBackStack(currentFragment)
            .commit()
    }

    private fun initStepsImg() {
        img_step1.setImageResource(R.drawable.ic_step_operation_image_complete)
        img_step2.setImageResource(R.drawable.ic_step_operation_image)
        img_step3.setImageResource(R.drawable.ic_step_operation_image)
        img_step4.setImageResource(R.drawable.ic_step_operation_image)
    }

    override fun onBackPressed() {
        var index = supportFragmentManager.backStackEntryCount - 1
        var backEntry: FragmentManager.BackStackEntry = supportFragmentManager.getBackStackEntryAt(index);
        val tag: String = backEntry.name.toString()

        if(tag?.compareTo("paymentMethodFragment") == 0){
            img_step2.setImageResource(R.drawable.ic_step_operation_image)
            currentFragment = "amountFragment"
            //if(paymentMethodSelected != null) paymentMethodSelected == null

        } else if(tag?.compareTo("cardsFragment") == 0){
            img_step3.setImageResource(R.drawable.ic_step_operation_image)
            currentFragment = "paymentMethodFragment"
            //if(cardIssuerSelected != null) cardIssuerSelected == null

        } else if(tag?.compareTo("installmentsFragment") == 0){
            img_step4.setImageResource(R.drawable.ic_step_operation_image)
            currentFragment = "cardsFragment"
            //if(payerCostSelected != null) payerCostSelected == null
        }

        super.onBackPressed()
    }
}