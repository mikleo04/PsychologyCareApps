package com.example.psychologycareapps.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.R
import com.example.psychologycareapps.model.ModelRecommendation

class RecommendationAdapter (var data : ArrayList<ModelRecommendation>, var context: Activity?) : RecyclerView.Adapter<RecommendationAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageActivity = view.findViewById<ImageView>(R.id.iv_activity)
        val judulActivity = view.findViewById<TextView>(R.id.tv_judulactivity)
        val deskripsiActivity = view.findViewById<TextView>(R.id.tv_deskripsi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_recommendation, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.imageActivity.setImageResource(data[position].imageActivity)
        holder.judulActivity.text = data[position].judulActivity
        holder.deskripsiActivity.text = data[position].deskripsiActivity
    }

    override fun getItemCount(): Int {
        return data.size
    }


}