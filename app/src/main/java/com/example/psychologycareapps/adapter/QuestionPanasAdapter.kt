package com.example.psychologycareapps.adapter

import android.app.Activity
import android.nfc.Tag
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
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

        holder.question.text = data[position].question
        holder.groupAnswer.setOnCheckedChangeListener{ group, checkedId ->
            val ans = context?.findViewById<RadioButton>(checkedId)

            data.forEach {
                data[position].answer = when(ans?.text) {
                    "Hampir Tidak Pernah" -> 1
                    "Jarang" -> 2
                    "Kadang - Kadang" -> 3
                    "Sering" -> 4
                    "Hampir Selalu" -> 5
                    else -> 0
                }
            }
        }

        val check = when(data[position].answer){
            1 -> R.id.rb_htp
            2 -> R.id.rb_jr
            3 -> R.id.rb_kd
            4 -> R.id.rb_sr
            5 -> R.id.rb_hsl
            else -> -1
        }
        holder.groupAnswer.check(check)
    }

    fun getSelectedItemData() : ArrayList<ModelQuestionPanas> {
        return data
    }

    companion object{
        private const val TAG = "QuestionPanasAdapter"
    }
}