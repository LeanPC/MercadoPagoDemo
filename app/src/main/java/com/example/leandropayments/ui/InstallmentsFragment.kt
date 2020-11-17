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
import com.example.leandropayments.data.model.Installment
import com.example.leandropayments.data.model.PayerCost
import com.example.leandropayments.viewmodel.InstallmentsViewModel
import com.example.leandropayments.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class InstallmentsFragment(): Fragment(), CoroutineScope, OnClickItem<PayerCost>{

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job
    private lateinit var adapter: InstallmentsRecyclerAdapter
    private lateinit var installmentsViewModel: InstallmentsViewModel
    private var amount: Double = 0.0
    private var paymentMethodId = ""
    private var issuerId = ""
    private var dataPasser: SuccessErrorOperation? = null
    private lateinit var payerCostSelected: PayerCost

    companion object {
        /**
         * @return A new instance of fragment MethodsPaymentsFragment.
         */
        fun newInstance() = InstallmentsFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context != null) {
            super.onAttach(context)
        }
        dataPasser = context as SuccessErrorOperation?
        job = SupervisorJob()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_title.text = resources.getString(R.string.tv_title_installments)
        recycler_list.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        adapter = InstallmentsRecyclerAdapter(emptyList<PayerCost>().toMutableList(), dataPasser as Context, this)
        recycler_list.adapter = adapter
        btn_continue.setOnClickListener(View.OnClickListener {
            if(payerCostSelected != null) {
                dataPasser?.loadScreenSuccess(payerCostSelected, this)
            } else {
                dataPasser?.showErrorToast(resources.getString(R.string.error_validate_installments))
            }
        })

        initObserver()
        geInstallments()
    }

    private fun initObserver() {

        installmentsViewModel = ViewModelProviders.of(this, ViewModelFactory()).get(
            InstallmentsViewModel::class.java)
        val installmentObserver = Observer<List<Installment>> {
            //Siempre llega un item
            loadItem(it[0])
            dataPasser?.hideProgressIndicator()
        }
        installmentsViewModel.getListInstallmentsLiveData().observe(viewLifecycleOwner, installmentObserver)
    }

    fun loadItem(item: Installment) {
        adapter.update(items = item.payer_costs.toMutableList())
    }

    private fun geInstallments() {
        dataPasser?.showProgressIndicator()
        launch {
            val success = withContext(Dispatchers.IO){
                installmentsViewModel.getListInstallments(amount.toString(), paymentMethodId, issuerId)
            }
        }
    }

    override fun onDetach() {
        job.cancel()
        super.onDetach()
    }

    override fun onClickItemSelected(item: PayerCost) {
        payerCostSelected = item
    }
}