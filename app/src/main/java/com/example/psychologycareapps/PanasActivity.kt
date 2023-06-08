package com.example.psychologycareapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.adapter.QuestionPanasAdapter
import com.example.psychologycareapps.databinding.ActivityPanasBinding
import com.example.psychologycareapps.model.ModelQuestionPanas
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PanasActivity : AppCompatActivity() {

    lateinit var binding: ActivityPanasBinding
    lateinit var rvQuestion : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPanasBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val lmQuestion = LinearLayoutManager(applicationContext)
        lmQuestion.orientation = LinearLayoutManager.VERTICAL
        rvQuestion = binding.rvQuestion

        val adapterQuestion = QuestionPanasAdapter(ArrayQuestion, this)
        rvQuestion.setHasFixedSize(true)
        rvQuestion.layoutManager = lmQuestion

        rvQuestion.adapter = adapterQuestion

        binding.btnSubmit.setOnClickListener {
            val data = adapterQuestion.getSelectedItemData()
            var allowCalculate = true
            // validation apakah pertanyaan sudah diisi seluruhnya?
            for (i in 1..data.size-1) {
                Log.d(TAG, "onCreate: q: " + data[i].question + " a: " + data[i].answer)
                if (data[i].answer == 0) {
                    Toast.makeText(this, "Mohon isi semua pertanyaan", Toast.LENGTH_SHORT).show()
                    allowCalculate = false
                    break
                }
            }

            //limit submit jawaban dengan menghitung jumlah data yang sudah ada di firebase
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat("MM-yyyy")
            val current = formatter.format(time)
            var counter = 0
            val ref = FirebaseFirestore.getInstance().collection("Panas").document(uid!!).collection("mood").document("value").collection(current)
            ref.get().addOnSuccessListener { documents ->
                counter = documents.count()

                if (counter >= 30) {
                    Toast.makeText(applicationContext, "Anda sudah mencapai batas test", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                   allowCalculate = false
                }
            }.addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with", exception)
                }

            if (allowCalculate) {
                calculate(data, uid, current)
            }

        }
    }



    //the logic
    private fun calculate(data: ArrayList<ModelQuestionPanas>, uid: String, current: String) {
        var positif = 0
        var negative = 0

        //total
        data.forEach {
            if (it.type == "positif") {
                positif += it.answer
            } else {
                negative += it.answer
            }
        }
        Log.d(TAG, "calculate: pos: " + positif)
        Log.d(TAG, "calculate: neg: " + negative)

        val ref =  FirebaseFirestore.getInstance().collection("Tes Psikologi").document(uid).collection("mood").document("value").collection(current).document()
        var result = mapOf<String, String>()

        if (positif > negative) {
            result = mapOf("value" to "positif")
            Log.d(TAG, "calculate: psitif disimpan")
        } else {
            result = mapOf("value" to "negatif")
            Log.d(TAG, "calculate: negatif disimpan")
        }

        //upload hasil ke firebase
        ref.set(result).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(applicationContext, "Data ada berhasil disimpan", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(applicationContext, "Data anda gagal disimpan coba lagi beberasaat", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val ArrayQuestion : ArrayList<ModelQuestionPanas>get() {
        val arrayquestion = ArrayList<ModelQuestionPanas>()

        val typePositif = "positif"
        val typeNegatif = "negatif"

        val question1 = ModelQuestionPanas()
        question1.id = "1"
        question1.question = "1. Tertarik"
        question1.type = typePositif
        arrayquestion.add(question1)

        val question2 = ModelQuestionPanas()
        question2.question = "2. Tertekan"
        question2.type = typeNegatif
        arrayquestion.add(question2)

        val question3 = ModelQuestionPanas()
        question3.question = "3. Bersemangat"
        question3.type = typePositif
        arrayquestion.add(question3)

        val question4 = ModelQuestionPanas()
        question4.question = "4. Gundah"
        question4.type = typeNegatif
        arrayquestion.add(question4)

        val question5 = ModelQuestionPanas()
        question5.question = "5. Kuat"
        question5.type = typePositif
        arrayquestion.add(question5)

        val question6 = ModelQuestionPanas()
        question6.question = "6. Bersalah"
        question6.type = typeNegatif
        arrayquestion.add(question6)

        val question7 = ModelQuestionPanas()
        question7.question = "7. Takut"
        question7.type = typeNegatif
        arrayquestion.add(question7)

        val question8 = ModelQuestionPanas()
        question8.question = "8. Agresif"
        question8.type = typeNegatif
        arrayquestion.add(question8)

        val question9 = ModelQuestionPanas()
        question9.question = "9. Antusias"
        question9.type = typePositif
        arrayquestion.add(question9)

        val question10 = ModelQuestionPanas()
        question10.question = "10. Bangga"
        question10.type = typePositif
        arrayquestion.add(question10)

        return arrayquestion
    }

    companion object{
        private const val TAG = "PanasActivity"
    }

}