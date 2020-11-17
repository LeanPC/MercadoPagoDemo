package com.example.leandropayments.ui


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.example.leandropayments.R
import com.example.leandropayments.viewmodel.ProcessPaymentViewModel
import com.example.leandropayments.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope


class MainActivity : BaseActivity(), SuccessErrorOperation, CoroutineScope {

    private lateinit var currentFragment: String
    private lateinit var model: ProcessPaymentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            model = ViewModelProviders.of(this, ViewModelFactory()).get(ProcessPaymentViewModel::class.java)
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

    override fun loadScreenPaymentMethods() {
        img_step2.setImageResource(R.drawable.ic_step_operation_image_complete)

        val frg = PaymentsMethodsFragment.newInstance()
        currentFragment = "paymentMethodFragment"

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, frg, currentFragment)
            .addToBackStack(currentFragment)
            .commit()
    }

    override fun loadScreenCards() {
        img_step3.setImageResource(R.drawable.ic_step_operation_image_complete)

        val frg = CardsIssuersFragment.newInstance()
        currentFragment = "cardsFragment"

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, frg, currentFragment)
            .addToBackStack(currentFragment)
            .commit()
    }

    override fun loadScreenInstallments() {
        img_step4.setImageResource(R.drawable.ic_step_operation_image_complete)
        currentFragment = "installmentsFragment"

        val frg = InstallmentsFragment.newInstance()
        frg.retainInstance = true

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, frg, currentFragment)
            .addToBackStack(currentFragment)
            .commit()
    }

    override fun loadScreenSuccess() {
        //como no hay servicio de transaccion OK de la operacion
        //se inicializa la pantalla de monto para que al volver este listo de nuevo de lo contrario se deberia manejar en el back de fragments
        initStepsImg()

        currentFragment = "amountFragment"
        //Limpia el stack de fragments volviendo al root
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, AmountFragment.newInstance(), currentFragment)
            .addToBackStack(currentFragment)
            .commit()

        //seguro que hay otra manera mejor de enviar datos como objetos parcelables o viewmodels (se mejorara en otra version)
        val intent = Intent(baseContext, VoucherActivity::class.java)
        intent.putExtra("AMOUNT", model.amountInput)
        intent.putExtra("PAYMENT_METHOD_NAME", model.paymentMethodSelected.name)
        intent.putExtra("IMG_CARD", model.cardIssuerSelected.secure_thumbnail)
        intent.putExtra("INSTALLMENT", model.payerCostSelected.installments)
        intent.putExtra("RATE", model.payerCostSelected.installment_rate)
        intent.putExtra("DESCRIPTION", model.payerCostSelected.recommended_message)
        startActivity(intent)

    }

    private fun initStepsImg() {
        img_step1.setImageResource(R.drawable.ic_step_operation_image_complete)
        img_step2.setImageResource(R.drawable.ic_step_operation_image)
        img_step3.setImageResource(R.drawable.ic_step_operation_image)
        img_step4.setImageResource(R.drawable.ic_step_operation_image)
    }

    override fun onBackPressed() {
        var index = supportFragmentManager.backStackEntryCount - 1
        var backEntry: FragmentManager.BackStackEntry = supportFragmentManager.getBackStackEntryAt(
            index);
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