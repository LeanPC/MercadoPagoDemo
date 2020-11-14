package com.example.leandropayments.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.leandropayments.R
import com.example.leandropayments.data.model.PaymentMethod
import com.squareup.picasso.Picasso

class PaymentsMethodsRecyclerAdatper(
    private var data: MutableList<PaymentMethod>,
    private val context: Context?
): RecyclerView.Adapter<PaymentsMethodsRecyclerAdatper.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.method_payment_cardview,
            parent,
            false
        )
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: PaymentMethod = data[position]
        holder.initialize(item, context)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun update(items: MutableList<PaymentMethod>){
        data = items
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imagePaymentMethod = itemView.findViewById<ImageView>(R.id.image_item)!!
        private val descriptionPaymentMethod = itemView.findViewById<TextView>(R.id.tv_description)!!

        init {
            adapterPosition
        }
        fun initialize(item: PaymentMethod, context: Context?){
            itemView.setOnClickListener(View.OnClickListener {
                (context as OnClickItem<PaymentMethod>).onClickItemSelected(item)
            })

            Picasso.with(context)
                .load(item.secure_thumbnail)
                //Se implementara a futuro transformaciones
                //.transform(CircleTransform(0, 0))
                .centerInside()
                .fit()
                .into(imagePaymentMethod)

            descriptionPaymentMethod.text = item.name
        }
    }
}
