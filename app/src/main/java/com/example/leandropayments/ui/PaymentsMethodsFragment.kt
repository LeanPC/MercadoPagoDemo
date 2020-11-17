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
import com.example.leandropayments.viewmodel.ViewModelFactory
import com.example.leandropayments.viewmodel.ProcessPaymentViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class PaymentsMethodsFragment(): Fragment(), CoroutineScope, OnClickItem<PaymentMethod>{

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job
    private lateinit var adapter: PaymentsMethodsRecyclerAdapter
    private var dataPasser: SuccessErrorOperation? = null
    private var listItems: List<PaymentMethod> = emptyList()
    private lateinit var model: ProcessPaymentViewModel

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

            // TODO Analizar como resolver en kotlin elementos aun no seteados(crash cuando no se selecciona un item de la lista)
            if(model.paymentMethodSelected != null) {
                dataPasser?.loadScreenCards()
            } else {
                dataPasser?.showErrorToast(resources.getString(R.string.error_validate_payment_method))
            }
        })

        initObserver()
        getMethodsPayments()
    }

    private fun initObserver() {

        model = ViewModelProviders.of(requireActivity(), ViewModelFactory()).get(ProcessPaymentViewModel::class.java)
        val methodPaymentObserver = Observer<List<PaymentMethod>> {
            loadListItems(it)
            dataPasser?.hideProgressIndicator()
        }
        model.getListMethodsPaymentsLiveData().observe(viewLifecycleOwner, methodPaymentObserver)
    }

    fun loadListItems(items: List<PaymentMethod>) {
        listItems = items
        adapter.update(items.toMutableList())
    }

    private fun getMethodsPayments() {
        dataPasser?.showProgressIndicator()
        launch {
            val success = withContext(Dispatchers.IO){
                model.getListMethodsPayments()
            }
        }
    }

    override fun onDetach() {
        job.cancel()
        super.onDetach()
    }

    override fun onClickItemSelected(item: PaymentMethod) {
        model.paymentMethodSelected = item
    }
}