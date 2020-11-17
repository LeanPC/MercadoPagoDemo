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
    private val context: Context?,
    private val listener: OnClickItem<CardIssuer>
): RecyclerView.Adapter<CardsIssuersRecyclerAdatper.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.cards_issuer_cardview,
            parent,
            false
        )
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: CardIssuer = data[position]
        holder.initialize(item, context, listener)
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

        init {
            adapterPosition
        }
        fun initialize(item: CardIssuer, context: Context?, listener: OnClickItem<CardIssuer>){
            itemView.setOnClickListener(View.OnClickListener {
                // TODO agregar un background color al seleccionar el item
                listener.onClickItemSelected(item)
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
