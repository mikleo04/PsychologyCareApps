package com.example.psychologycareapps.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.R
import com.example.psychologycareapps.model.ModelQuestionPanas

class QuestionPanasAdapter (var data : ArrayList<ModelQuestionPanas>, var context: Activity?) : RecyclerView.Adapter<QuestionPanasAdapter.MyViewHolder>() {
    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val question = view.findViewById<TextView>(R.id.tv_question)
        val groupAnswer = view.findViewById<RadioGroup>(R.id.rg_answer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val question = data.get(position)

        holder.question.text = data[position].question
        holder.groupAnswer.setOnClickListener{
            
        }
    }
}