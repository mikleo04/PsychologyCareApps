package com.example.psychologycareapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.adapter.DassAdapter
import com.example.psychologycareapps.adapter.QuestionPanasAdapter
import com.example.psychologycareapps.databinding.ActivityDassBinding
import com.example.psychologycareapps.model.ModelDass
import com.example.psychologycareapps.model.ModelQuestionPanas

class DassActivity : AppCompatActivity() {

    lateinit var binding : ActivityDassBinding
    lateinit var rv_statement :RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDassBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val lmStatement = LinearLayoutManager(applicationContext)
        lmStatement.orientation = LinearLayoutManager.VERTICAL
        rv_statement = binding.rvStatemant

        val adapterStatement = DassAdapter(ArrayStatement, this)
        rv_statement.setHasFixedSize(true)
        rv_statement.layoutManager = lmStatement
        rv_statement.adapter = adapterStatement

    }

    val ArrayStatement : ArrayList<ModelDass>get() {
        val arraystatement = ArrayList<ModelDass>()

        val statement1 = ModelDass()
        statement1.statemant = "1. Saya merasa sedih dan murung."
        arraystatement.add(statement1)
        val statement2 = ModelDass()
        statement2.statemant = "2. Saya merasa tidak menarik atau kehilangan minat pada hal-hal yang biasanya saya nikmati."
        arraystatement.add(statement2)
        val statement3 = ModelDass()
        statement3.statemant = "3. Saya merasa tidak berharga atau tidak berguna."
        arraystatement.add(statement3)
        val statement4 = ModelDass()
        statement4.statemant = "4. Saya merasa tidak bersemangat atau tanpa harapan."
        arraystatement.add(statement4)
        val statement5 = ModelDass()
        statement5.statemant = "5. Saya merasa cemas atau tertekan."
        arraystatement.add(statement5)
        val statement6 = ModelDass()
        statement6.statemant = "6. Saya merasa sedih dan murung."
        arraystatement.add(statement6)
        val statement7 = ModelDass()
        statement7.statemant = "7. Saya merasa tidak menarik atau kehilangan minat pada hal-hal yang biasanya saya nikmati."
        arraystatement.add(statement7)
        val statement8 = ModelDass()
        statement8.statemant = "8. Saya merasa tidak berharga atau tidak berguna."
        arraystatement.add(statement8)
        val statement9 = ModelDass()
        statement9.statemant = "9. Saya merasa tidak bersemangat atau tanpa harapan."
        arraystatement.add(statement9)
        val statement10 = ModelDass()
        statement10.statemant = "10. Saya merasa cemas atau tertekan."
        arraystatement.add(statement10)
        val statement11 = ModelDass()
        statement11.statemant = "11. Saya merasa sedih dan murung."
        arraystatement.add(statement11)
        val statement12 = ModelDass()
        statement12.statemant = "12. Saya merasa tidak menarik atau kehilangan minat pada hal-hal yang biasanya saya nikmati."
        arraystatement.add(statement12)
        val statement13 = ModelDass()
        statement13.statemant = "13. Saya merasa tidak berharga atau tidak berguna."
        arraystatement.add(statement13)
        val statement14 = ModelDass()
        statement14.statemant = "14. Saya merasa tidak bersemangat atau tanpa harapan."
        arraystatement.add(statement14)
        val statement15 = ModelDass()
        statement15.statemant = "15. Saya merasa cemas atau tertekan."
        arraystatement.add(statement15)

        return arraystatement
    }

}