package com.example.psychologycareapps.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.R
import com.example.psychologycareapps.model.ModelRecommendation

class RecommendationAdapter (var data : ArrayList<ModelRecommendation>, var context: Activity?) : RecyclerView.Adapter<RecommendationAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageActivity = view.findViewById<ImageView>(R.id.iv_activity)
        val judulActivity = view.findViewById<TextView>(R.id.tv_judulactivity)
        val deskripsiActivity = view.findViewById<TextView>(R.id.tv_deskripsi)
        val tutorialActivity = view.findViewById<TextView>(R.id.tv_tutorial)
        val linearLayout = view.findViewById<LinearLayout>(R.id.ly_card)

        fun collapseExpandedView() {
            tutorialActivity.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_recommendation, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val listData = data[position]
        holder.imageActivity.setImageResource(listData.imageActivity)
        holder.judulActivity.text = listData.judulActivity
        holder.deskripsiActivity.text = listData.deskripsiActivity
        holder.tutorialActivity.text = listData.turotialActivity

        val isExpandable : Boolean = listData.isExpandable
        holder.tutorialActivity.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.linearLayout.setOnClickListener {
            isAnyItemExpanded(position)
            listData.isExpandable = !listData.isExpandable
            notifyItemChanged(position, Unit)
        }

    }

    private fun isAnyItemExpanded(position: Int) {
        val temp = data.indexOfFirst {
            it.isExpandable
        }

        if (temp >= 0  && temp != position) {
            data[temp].isExpandable =false
            notifyItemChanged(temp,0)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, payloads: MutableList<Any>) {

        if (payloads.isNotEmpty() && payloads[0] == 0) {
            holder.collapseExpandedView()
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


}