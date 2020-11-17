package com.example.leandropayments.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.leandropayments.R
import com.example.leandropayments.data.model.PayerCost

class InstallmentsRecyclerAdapter(
    private var data: MutableList<PayerCost>,
    private val context: Context?,
    private val listener: OnClickItem<PayerCost>
): RecyclerView.Adapter<InstallmentsRecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.item_installment_view,
            parent,
            false
        )
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: PayerCost = data[position]
        holder.initialize(item, context, listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun update(items: MutableList<PayerCost>){
        data = items
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val installment = itemView.findViewById<TextView>(R.id.tv_installment_value)!!
        private val rate = itemView.findViewById<TextView>(R.id.tv_rate_value)!!
        private val description = itemView.findViewById<TextView>(R.id.tv_description)!!
        private val cfttea = itemView.findViewById<TextView>(R.id.tv_cft_tea)!!

        init {
            adapterPosition
        }
        fun initialize(item: PayerCost, context: Context?, listener: OnClickItem<PayerCost>){
            itemView.setOnClickListener(View.OnClickListener {
                listener.onClickItemSelected(item)
            })

            installment.text = item.installments.toString()
            rate.text = item.installment_rate.toString() + "%"
            description.text = item.recommended_message
            cfttea.text =  item.labels[0]
        }
    }
}
