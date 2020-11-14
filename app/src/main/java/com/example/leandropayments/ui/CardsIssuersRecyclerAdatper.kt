package com.example.leandropayments.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.leandropayments.R
import com.example.leandropayments.data.model.CardIssuer
import com.squareup.picasso.Picasso

class CardsIssuersRecyclerAdatper(
    private var data: MutableList<CardIssuer>,
    private val context: Context?
): RecyclerView.Adapter<CardsIssuersRecyclerAdatper.ViewHolder>() {

    private lateinit var currentItem: CardIssuer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.method_payment_cardview,
            parent,
            false
        )
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: CardIssuer = data.get(position)

        Picasso.with(context)
            .load(item.secure_thumbnail)
            //Se implementara a futuro transformaciones
            //.transform(CircleTransform(0, 0))
            .centerInside()
            .fit()
            .into(holder.imageCardIssuer)

        holder.descriptionCardIssuer.text = item.name
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun update(items: MutableList<CardIssuer>){
        data = items
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageCardIssuer = itemView.findViewById<ImageView>(R.id.image_item)
        val descriptionCardIssuer = itemView.findViewById<TextView>(R.id.tv_description)

        fun initialize(item: CardIssuer, position: Int, context: Context?){
            itemView.setOnClickListener(View.OnClickListener {
                (context as OnClickItem<CardIssuer>).onClickItemSelected(item)
            })

            Picasso.with(context)
                .load(item.secure_thumbnail)
                //Se implementara a futuro transformaciones
                //.transform(CircleTransform(0, 0))
                .centerInside()
                .fit()
                .into(imageCardIssuer)

            descriptionCardIssuer.text = item.name
        }
    }
}
