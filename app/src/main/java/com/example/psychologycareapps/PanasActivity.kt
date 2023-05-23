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
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

class PanasActivity : AppCompatActivity() {

    lateinit var binding: ActivityPanasBinding
    lateinit var rvQuestion : RecyclerView
    companion object{
        private const val TAG = "PanasActivity"
    }

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
            // check apakah sudah diisi seluruhnya?
            for(i in 1..data.size-1){
                Log.d(TAG, "onCreate: q: " + data[i].question + " a: " + data[i].answer)
                if(data[i].answer == 0){
                    Toast.makeText(this, "isi data bang", Toast.LENGTH_SHORT).show()
                    allowCalculate = false
                    break
                }
            }

            //ambil data dari database hitung jumlah yg sudah ada
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat("MMMM-yyyy")
            val current = formatter.format(time)
            var counter = 0
            val ref = FirebaseFirestore.getInstance()
                .collection("Panas")
                .document(uid!!)
                .collection(current)
                .get()
                .addOnSuccessListener {documents ->
                    counter = documents.count()

                    if (counter >= 4){
                        allowCalculate = false
                    }

                    if (allowCalculate){
                        calculate(data)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        }
    }

    private fun calculate(data: ArrayList<ModelQuestionPanas>) {
        var pos = 0
        var neg = 0

        data.forEach {
            if (it.type == "positif"){
                pos += it.answer
            }else{
                neg += it.answer
            }
        }
        // total
        Log.d(TAG, "calculate: pos: " + pos)
        Log.d(TAG, "calculate: neg: " + neg)
        val uid = FirebaseAuth.getInstance().uid
        val timeStamp = Timestamp.now()
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("MMMM-yyyy")
        val current = formatter.format(time)
        val ref = FirebaseFirestore.getInstance().collection("Panas").document(uid!!).collection(current).document()
        var result = mapOf<String, String>()



        Log.d(TAG, "onCreate: TIME: " + current.toString())


        if (pos > neg){
            result = mapOf("value" to "positif")

        }else{
            result = mapOf("value" to "negatif")
            Log.d(TAG, "calculate: upload neg")
        }

        ref.set(result).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(applicationContext, "berhasil disimpan " + result, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "gagal " + result, Toast.LENGTH_SHORT).show()
            }
        }
    }


    val ArrayQuestion : ArrayList<ModelQuestionPanas>get() {
        val arrayquestion = ArrayList<ModelQuestionPanas>()

        val question1 = ModelQuestionPanas()
        question1.id = "1"
        question1.question = "1. Tertarik"
        question1.type = "positif"
        arrayquestion.add(question1)

        val question2 = ModelQuestionPanas()
        question2.question = "2. Bersemangat"
        question2.type = "positif"
        arrayquestion.add(question2)

        val question3 = ModelQuestionPanas()
        question3.question = "3. Bangga"
        question3.type = "positif"
        arrayquestion.add(question3)

        val question4 = ModelQuestionPanas()
        question4.question = "4. Terinspirasi"
        question4.type = "positif"
        arrayquestion.add(question4)

        val question5 = ModelQuestionPanas()
        question5.question = "5. Penuh perhatian"
        question5.type = "positif"
        arrayquestion.add(question5)

        val question6 = ModelQuestionPanas()
        question6.question = "6. Kesal"
        question6.type = "negatif"
        arrayquestion.add(question6)

        val question7 = ModelQuestionPanas()
        question7.question = "7. Tertekan"
        question7.type = "negatif"
        arrayquestion.add(question7)

        val question8 = ModelQuestionPanas()
        question8.question = "8. Bersalah"
        question8.type = "negatif"
        arrayquestion.add(question8)

        val question9 = ModelQuestionPanas()
        question9.question = "9. Mudah marah"
        question9.type = "negatif"
        arrayquestion.add(question9)

        val question10 = ModelQuestionPanas()
        question10.question = "10. Gelisah"
        question10.type = "negatif"
        arrayquestion.add(question10)

        return arrayquestion
    }
}