package com.example.psychologycareapps.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.R
import com.example.psychologycareapps.model.ModelDass
import com.example.psychologycareapps.model.ModelQuestionPanas

class DassAdapter(var data : ArrayList<ModelDass>, var context: Activity?) : RecyclerView.Adapter<DassAdapter.MyViewHolder>() {
    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val statement =view.findViewById<TextView>(R.id.tv_statemant)
        val groupAnswer = view.findViewById<RadioGroup>(R.id.rg_answer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_dass, parent, false)
        return DassAdapter.MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.statement.text = data[position].statement
        holder.groupAnswer.setOnCheckedChangeListener{group, checkedId ->
            val ans = context?.findViewById<RadioButton>(checkedId)

            data.forEach {
                data[position].answer = when(ans?.text) {
                    "Tidak Pernah" -> 0
                    "kadang-kadang" -> 1
                    "Sering" -> 2
                    "Hampir Selalu" -> 3
                    else -> {
                        Log.d(TAG, "Jawaban tidak diketahui")}
                }
            }
        }

        val check = when (data[position].answer) {
            0 -> R.id.rb_tpdaas
            1 -> R.id.rb_kddass
            2 -> R.id.rb_srdass
            3 -> R.id.rb_hsdass
            else -> -1
        }
        holder.groupAnswer.check(check)
    }

    fun getSelectedItemData() : ArrayList<ModelDass> {
        return data
    }

    companion object {
        private const val TAG = "DassAdapter"
    }

}