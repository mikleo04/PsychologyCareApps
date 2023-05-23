package com.example.psychologycareapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.adapter.QuestionPanasAdapter
import com.example.psychologycareapps.databinding.ActivityPanasBinding
import com.example.psychologycareapps.model.ModelQuestionPanas

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
    }

    val ArrayQuestion : ArrayList<ModelQuestionPanas>get() {
        val arrayquestion = ArrayList<ModelQuestionPanas>()

        val question1 = ModelQuestionPanas()
        question1.question = "1. Tertarik"
        arrayquestion.add(question1)

        val question2 = ModelQuestionPanas()
        question2.question = "2. Bersemangat"
        arrayquestion.add(question2)

        val question3 = ModelQuestionPanas()
        question3.question = "3. Bangga"
        arrayquestion.add(question3)

        val question4 = ModelQuestionPanas()
        question4.question = "4. Terinspirasi"
        arrayquestion.add(question4)

        val question5 = ModelQuestionPanas()
        question5.question = "5. Penuh perhatian"
        arrayquestion.add(question5)

        val question6 = ModelQuestionPanas()
        question6.question = "6. Kesal"
        arrayquestion.add(question6)

        val question7 = ModelQuestionPanas()
        question7.question = "7. Tertekan"
        arrayquestion.add(question7)

        val question8 = ModelQuestionPanas()
        question8.question = "8. Bersalah"
        arrayquestion.add(question8)

        val question9 = ModelQuestionPanas()
        question9.question = "9. Mudah marah"
        arrayquestion.add(question9)

        val question10 = ModelQuestionPanas()
        question10.question = "10. Gelisah"
        arrayquestion.add(question10)

        return arrayquestion
    }
}