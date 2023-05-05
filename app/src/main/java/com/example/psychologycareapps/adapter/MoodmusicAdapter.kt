package com.example.psychologycareapps.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.R
import com.example.psychologycareapps.model.ModelMoodmusic
import com.example.psychologycareapps.model.ModelRecommendation

class MoodmusicAdapter(var dataMood : ArrayList<ModelMoodmusic>, var context: Activity?) : RecyclerView.Adapter<MoodmusicAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val backgroundMoodmusic = view.findViewById<ConstraintLayout>(R.id.background_moodmusic)
        val moodMuic = view.findViewById<TextView>(R.id.moodmusic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_moodmusic, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataMood.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.backgroundMoodmusic.setBackgroundResource(dataMood[position].backgroundMoodmusic)
        holder.moodMuic.text = dataMood[position].moodMusic
    }
}