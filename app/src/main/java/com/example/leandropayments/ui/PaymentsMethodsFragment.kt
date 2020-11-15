package com.example.leandropayments.ui
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leandropayments.R
import com.example.leandropayments.data.model.PaymentMethod
import com.example.leandropayments.viewmodel.PaymentsMethodsViewModel
import com.example.leandropayments.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class PaymentsMethodsFragment(): Fragment(), CoroutineScope, OnClickItem<PaymentMethod>{

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job
    private lateinit var adapter: PaymentsMethodsRecyclerAdapter
    private lateinit var paymentMethodSelected: PaymentMethod
    private lateinit var paymentsMethodsViewModel: PaymentsMethodsViewModel
    private var dataPasser: SuccessErrorOperation? = null
    private var listItems: List<PaymentMethod> = emptyList()

    companion object {
        /**
         * @return A new instance of fragment MethodsPaymentsFragment.
         */
        fun newInstance() = PaymentsMethodsFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context != null) {
            super.onAttach(context)
        }
        dataPasser = context as SuccessErrorOperation?
        job = SupervisorJob()
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_title.text = resources.getString(R.string.tv_title_methods_payments)
        recycler_list.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        adapter = PaymentsMethodsRecyclerAdapter(listItems.toMutableList(), dataPasser as Context, this)
        recycler_list.adapter = adapter
        btn_continue.setOnClickListener(View.OnClickListener {
            dataPasser?.loadScreenCards(paymentMethodSelected, this)
        })

        initObserver()
        getMethodsPayments()
    }

    private fun initObserver() {

        paymentsMethodsViewModel = ViewModelProviders.of(this, ViewModelFactory()).get(PaymentsMethodsViewModel::class.java)
        val methodPaymentObserver = Observer<List<PaymentMethod>> {
            loadListItems(it)
        }
        paymentsMethodsViewModel.getListMethodsPaymentsLiveData().observe(viewLifecycleOwner, methodPaymentObserver)
    }

    fun loadListItems(items: List<PaymentMethod>) {
        listItems = items
        adapter.update(items.toMutableList())
    }

    private fun getMethodsPayments() {
        dataPasser?.showProgressIndicator()
        launch {
            val success = withContext(Dispatchers.IO){
                paymentsMethodsViewModel.getListMethodsPayments()
            }
            dataPasser?.hideProgressIndicator()
//            if(success.isEmpty){
//                dataPasser?.showErrorToast(resources.getString(R.string.service_error))
//            }
        }
       // dataPasser?.hideProgressIndicator()
        //dataPasser?.hideProgressIndicator()

    }

    override fun onDetach() {
        job.cancel()
        super.onDetach()
    }

    override fun onClickItemSelected(item: PaymentMethod) {
        paymentMethodSelected = item
    }
}