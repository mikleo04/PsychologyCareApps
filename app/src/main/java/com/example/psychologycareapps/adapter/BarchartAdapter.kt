package com.example.psychologycareapps.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.db.williamchart.view.BarChartView
import com.example.psychologycareapps.R
import com.example.psychologycareapps.model.ModelBarchart
import com.example.psychologycareapps.model.ModelRecommendation

class BarchartAdapter(var data : ArrayList<ModelBarchart>, var context: Activity?) : RecyclerView.Adapter<BarchartAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleBar = view.findViewById<TextView>(R.id.tv_tittleBar)
        val barChart = view.findViewById<BarChartView>(R.id.barChart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_barchart, parent, false)
        return BarchartAdapter.MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.titleBar.text = data[position].title
        holder.barChart.animate(data[position].dataChart)
        holder.barChart.animation.duration = 1000L

        Log.d(TAG, "mendapatkan data chart: ${data[position].dataChart} ")
    }

    companion object {
        private const val TAG = "BarchartAdapter"
    }
}