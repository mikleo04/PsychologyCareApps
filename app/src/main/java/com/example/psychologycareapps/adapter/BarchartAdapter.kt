package com.example.psychologycareapps.adapter

import android.app.Activity
import android.graphics.Color
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
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.google.geo.type.Viewport
import lecho.lib.hellocharts.model.Axis
import lecho.lib.hellocharts.model.AxisValue
import lecho.lib.hellocharts.model.Column
import lecho.lib.hellocharts.model.ColumnChartData
import lecho.lib.hellocharts.model.SubcolumnValue
import lecho.lib.hellocharts.util.ChartUtils
import lecho.lib.hellocharts.view.ColumnChartView
import okhttp3.internal.Util

class BarchartAdapter(var data : ArrayList<ModelBarchart>, var context: Activity?) : RecyclerView.Adapter<BarchartAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleBar = view.findViewById<TextView>(R.id.tv_tittleBar)
//        val barChart = view.findViewById<BarChartView>(R.id.barChart)
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
//        holder.barChart.animate(data[position].dataChart)
//        holder.barChart.animation.duration = 1000L
        val dy = arrayListOf<Float>()
        data[position].dataChart.forEach {
            dy.add(it.second)
        }

        val dx = arrayListOf<Float>(
            3.33f,
            12.3f,
            19.2f,
            19.2f
        )

//        holder.

        val chart =  context?.findViewById<ColumnChartView>(R.id.chart)
        val columns = arrayListOf<Column>()
        for (i in 0 until dy.size){
            val values = ArrayList<SubcolumnValue>()
            values.add(SubcolumnValue(dy[i], ChartUtils.COLOR_GREEN))
            val c = Column(values)
            c.setHasLabels(true)
            columns.add(c)
        }

        val valX = arrayListOf<AxisValue>()

        for (i in 0 until dy.size){
            val label = AxisValue(i.toFloat())
            label.setLabel("Week ${i+1}")
            valX.add(label)
        }

        val axisY = Axis()
        val axisX = Axis()
        axisY.name = "Score"
        axisX.name = "Week"
        axisY.textColor = Color.BLACK
        axisX.textColor = Color.BLACK
        axisX.values = valX

        val colData = ColumnChartData(columns)
        colData.axisYLeft = axisY
        colData.axisXBottom = axisX
//        chart?.columnChartData = colData
        chart?.setColumnChartData(colData)

//        Log.d(TAG, "mendapatkan data chart: ${data[position].dataChart} ")
    }

    companion object {
        private const val TAG = "BarchartAdapter"
    }
}