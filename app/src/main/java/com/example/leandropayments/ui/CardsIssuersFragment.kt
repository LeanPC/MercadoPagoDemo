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
import com.example.leandropayments.data.model.CardIssuer
import com.example.leandropayments.viewmodel.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CardsIssuersFragment(): Fragment(), CoroutineScope, OnClickItem<CardIssuer>{

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job
    private lateinit var adapter: CardsIssuersRecyclerAdatper
    private var listItems: List<CardIssuer> = emptyList()
    var dataPasser: SuccessErrorOperation? = null
    private lateinit var model: ProcessPaymentViewModel

    companion object {
        /**
         * @return A new instance of fragment CardsIssuersFragment.
         */
        fun newInstance() = CardsIssuersFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        tv_title.text = resources.getString(R.string.tv_title_cards)
        recycler_list.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        adapter = CardsIssuersRecyclerAdatper(listItems.toMutableList(), dataPasser as Context, this)
        recycler_list.adapter = adapter
        btn_continue.setOnClickListener(View.OnClickListener {
            // TODO Analizar como resolver en kotlin elementos aun no seteados(crash cuando no se selecciona un item de la lista)
            if(model.cardIssuerSelected != null) {
                dataPasser?.loadScreenInstallments()
                dataPasser?.hideProgressIndicator()
            } else {
                dataPasser?.showErrorToast(resources.getString(R.string.error_validate_cards))
            }
        })

        initObserver()
        getCardsIssuers()
    }

    private fun initObserver() {

        model = ViewModelProviders.of(requireActivity(), ViewModelFactory()).get(ProcessPaymentViewModel::class.java)
        val cardIssuerObserver = Observer<List<CardIssuer>> {
            loadListItems(it)
            dataPasser?.hideProgressIndicator()
        }
        model.getListCardsIssuersLiveData().observe(viewLifecycleOwner, cardIssuerObserver)
    }

    fun loadListItems(items: List<CardIssuer>) {
        adapter.update(items.toMutableList())
    }

    private fun getCardsIssuers() {
        dataPasser?.showProgressIndicator()
        launch {
            val success = withContext(Dispatchers.IO){
                model.getListCardsIssuers(model.paymentMethodSelected.id)
            }
        }
    }

    override fun onDetach() {
        job.cancel()
        super.onDetach()
    }

    override fun onClickItemSelected(item: CardIssuer) {
        model.cardIssuerSelected = item
    }
}