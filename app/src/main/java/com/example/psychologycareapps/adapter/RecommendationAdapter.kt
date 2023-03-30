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
        val albumImage = view.findViewById<ImageView>(R.id.iv_album)
        val judulMusik = view.findViewById<TextView>(R.id.tv_judulmusik)
        val penyanyi = view.findViewById<TextView>(R.id.tv_penyanyi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_recommendation, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.albumImage.setImageResource(data[position].albumImage)
        holder.judulMusik.text = data[position].judulMusik
        holder.penyanyi.text = data[position].penyanyi
    }

    override fun getItemCount(): Int {
        return data.size
    }


}