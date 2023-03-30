package com.example.psychologycareapps.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.R
import com.example.psychologycareapps.model.ModelEmotion

class EmotionAdapter (var data : ArrayList<ModelEmotion>, var context: Activity?) : RecyclerView.Adapter<EmotionAdapter.MyViewHolder>() {

    class MyViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val emotion = view.findViewById<TextView>(R.id.tv_emotion)
        val emotionImage = view.findViewById<ImageView>(R.id.iv_emotion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View =  LayoutInflater.from(parent.context).inflate(R.layout.item_emotion, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.emotion.text = data[position].emotion
        holder.emotionImage.setImageResource(data[position].emotionImage)
    }

    override fun getItemCount(): Int {
        return data.size
    }


}