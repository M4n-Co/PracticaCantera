package com.example.selectavatar

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView


class AdaptadorAva (private val onClickListener: (AvatarColor) -> Unit): RecyclerView.Adapter<AdaptadorAva.ViewHolder>() {
    var avatars : MutableList<AvatarColor> = ArrayList()
    var posicionMarcada = 0
    var context : Context? = null

    @SuppressLint("NotConstructor")
    fun AdaptadorAva (lista: MutableList<AvatarColor>){
        this.avatars = lista
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        return ViewHolder(layoutInflater.inflate(R.layout.elemento_rv, parent, false))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val avatar = avatars[position]
        val pos = position
        holder.bind(avatar)
        val cvAvatar = holder.itemView.findViewById<CardView>(R.id.cv)
        holder.itemView.setOnClickListener{
            posicionMarcada=pos
            notifyDataSetChanged()
            onClickListener(avatar)
        }
        if(posicionMarcada == position){
            cvAvatar.setCardBackgroundColor(ContextCompat.getColor(context!!,R.color.selector_color))
        }else{
            cvAvatar.setCardBackgroundColor(ContextCompat.getColor(context!!,R.color.white))
        }
    }

    override fun getItemCount(): Int {
        return avatars.size
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        private val imAvatar : CircleImageView = view.findViewById(R.id.ivElemento)
        fun bind(avatar: AvatarColor) {
            imAvatar.setImageResource(avatar.imagen)
            imAvatar.setCircleBackgroundColorResource(R.color.white)

        }
    }
}