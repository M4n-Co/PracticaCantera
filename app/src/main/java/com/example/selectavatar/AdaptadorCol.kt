package com.example.selectavatar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.collections.ArrayList

class AdaptadorCol(private val onClickListener: (AvatarColor) -> Unit) : RecyclerView.Adapter<AdaptadorCol.ViewHolder>() {

    var colores : MutableList<AvatarColor> = ArrayList()
    var posicionMarcada = 0
    var context : Context? = null

    @SuppressLint("NotConstructor")
    fun AdaptadorCol (lista: MutableList<AvatarColor>){
        this.colores = lista
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        return ViewHolder(layoutInflater.inflate(R.layout.elemento_rv, parent, false))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = colores[position]
        val pos = position
        holder.bind(color)
        val cvAvatar = holder.itemView.findViewById<CardView>(R.id.cv)
        cvAvatar.setOnClickListener{
            posicionMarcada=pos
            notifyDataSetChanged()
            onClickListener(color)
        }
        if(posicionMarcada == position){
            cvAvatar.setCardBackgroundColor(ContextCompat.getColor(context!!,R.color.selector_color))
        }else{
            cvAvatar.setCardBackgroundColor(ContextCompat.getColor(context!!,R.color.white))
        }
    }

    override fun getItemCount(): Int {
        return colores.size
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        private val imColor : CircleImageView = view.findViewById(R.id.ivElemento)
        val params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(135,135)
        fun bind(color: AvatarColor) {
            imColor.layoutParams = params
            imColor.setImageResource(color.imagen)
        }
    }
}