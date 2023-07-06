package com.example.firebasekotlin.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasekotlin.Models.DeliveryModel
import com.example.firebasekotlin.R

class DeliveryAdapter(private val deliveryList: ArrayList<DeliveryModel>) :
    RecyclerView.Adapter<DeliveryAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override  fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.delivery_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDelivery = deliveryList[position]
        holder.tvDeliveryAddress.text = currentDelivery.DeliveryAddress
    }

    override fun getItemCount(): Int {
        return deliveryList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvDeliveryAddress : TextView = itemView.findViewById(R.id.tvDeliveryAddress)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}
