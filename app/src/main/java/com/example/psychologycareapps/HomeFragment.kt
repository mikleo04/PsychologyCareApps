package com.example.psychologycareapps

import android.content.Intent
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.adapter.BarchartAdapter
import com.example.psychologycareapps.adapter.RecommendationAdapter
import com.example.psychologycareapps.model.ModelBarchart
import com.example.psychologycareapps.model.ModelRecommendation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    lateinit var rvRecommendation : RecyclerView
    lateinit var rvBarchart : RecyclerView
    var data = arrayListOf<Pair<String,Float>>()
    var dataDepresi = arrayListOf<Pair<String,Float>>()
    var dataStress = arrayListOf<Pair<String,Float>>()
    var dataAnxiety = arrayListOf<Pair<String,Float>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("MMMM-yyyy")
        val current = formatter.format(time)
        val refDepresi = FirebaseFirestore.getInstance().collection("Tes Psikologi").document(uid!!).collection("Depresi").document("value").collection(current)
        val refStress = FirebaseFirestore.getInstance().collection("Tes Psikologi").document(uid!!).collection("Stress").document("value").collection(current)
        val refAnxiety = FirebaseFirestore.getInstance().collection("Tes Psikologi").document(uid!!).collection("Anxiety").document("value").collection(current)
        val refMood = FirebaseFirestore.getInstance().collection("Tes Psikologi").document(uid!!).collection("mood").document("value").collection(current)

        // get data mood
        refMood.get().addOnSuccessListener {documents ->
            for (document in documents) {
                var tgl = 1
                var value = document.get("value").toString()
                Log.d(TAG, "mood value ${document.get("value")}")
                var score = if (value == "positif") 2F else 1F

                data.add(Pair(tgl.toString(), score))
                tgl++
            }
            if (data.size == 1) {
                data.add(Pair("", 0F))
            }

            Log.d(TAG, "onViewCreated: ${data.size}")
//            barChart.animation.duration = animationDuration
//            barChart.animate(data)
        }.addOnFailureListener {exception ->
            Log.d(TAG, "get failed data mood", exception)
        }

        // get data depresi
        refDepresi.get().addOnSuccessListener { documents ->
            for (document in documents) {
                var week = 1
                var value = document.get("value").toString().toInt()
                Log.d(TAG, "depresi value ${document.get("value")}")
                var score = value / 2
                dataDepresi.add(Pair(week.toString(), score.toFloat()))
                week++
            }
            if (dataDepresi.size == 1) {
                dataDepresi.add(Pair("", 0F))
            }
            Log.d(TAG, "onViewCreated: ${dataDepresi.size}")
            return@addOnSuccessListener
        } .addOnFailureListener {exception->
            Log.d(TAG, "get failed data depresi", exception)
        }

        //get data stress
        refStress.get().addOnSuccessListener { documents ->
            for (document in documents) {
                var week = 1
                var value = document.get("value").toString().toInt()
                Log.d(TAG, "stress value ${document.get("value")}")
                var score = value / 2
                dataStress.add(Pair(week.toString(), score.toFloat()))
                week++
            }
            if (dataStress.size == 1) {
                dataStress.add(Pair("", 0F))
            }
            Log.d(TAG, "onViewCreated: ${dataStress.size}")
        } .addOnFailureListener {exception->
            Log.d(TAG, "get failed data stress", exception)
        }

        //get data Anxiety
        refAnxiety.get().addOnSuccessListener { documents ->
            for (document in documents) {
                var week = 1
                var value = document.get("value").toString().toInt()
                Log.d(TAG, "anxiety value ${document.get("value")}")
                var score = value / 2
                dataAnxiety.add(Pair(week.toString(), score.toFloat()))
                week++
            }
            if (dataAnxiety.size == 1) {
                dataAnxiety.add(Pair("", 0F))
            }
            Log.d(TAG, "onViewCreated: ${dataAnxiety.size}")
        } .addOnFailureListener {exception->
            Log.d(TAG, "get failed data anxiety", exception)
        }


        btn_panastest.setOnClickListener {
            val intent = Intent(this@HomeFragment.requireContext(), PanasActivity::class.java)
            startActivity(intent)
        }

        btn_dasstest.setOnClickListener {
            val intent = Intent(this@HomeFragment.requireContext(), DassActivity::class.java)
            startActivity(intent)
        }

        //Recycle Barchart
        val lmBarchart = LinearLayoutManager(activity)
        lmBarchart.orientation = LinearLayoutManager.HORIZONTAL
        rvBarchart = view.findViewById(R.id.rv_barchart)

        val adapterBarchart = BarchartAdapter(ArrayBar, activity)
        rvBarchart.setHasFixedSize(true)
        rvBarchart.layoutManager = lmBarchart
        rvBarchart.adapter = adapterBarchart

        //Recycle recomendation
        val lmRecommendation = LinearLayoutManager(activity)
        lmRecommendation.orientation = LinearLayoutManager.VERTICAL
        rvRecommendation = view.findViewById(R.id.rv_recommendation)!!

        val adapterRecommendation = RecommendationAdapter(ArrayRecommendation, activity)
        rvRecommendation.setHasFixedSize(true)
        rvRecommendation.layoutManager = lmRecommendation
        rvRecommendation.adapter = adapterRecommendation

    }

    val ArrayBar : ArrayList<ModelBarchart>get() {
        val arraybar = ArrayList<ModelBarchart>()

        val moodBarchart = ModelBarchart()
        moodBarchart.title = "Mood"
        moodBarchart.dataChart = data
        arraybar.add(moodBarchart)
        Log.d(TAG, "aku mendapatkan data mood: ${data} ")

        val depresiBarchart = ModelBarchart()
        depresiBarchart.title = "Depresi"
        depresiBarchart.dataChart = dataDepresi
        Log.d(TAG, "aku mendapatkan data depresi: ${dataDepresi} ")

        val stressBarchart = ModelBarchart()
        stressBarchart.title = "Stress"
        stressBarchart.dataChart = dataStress
        Log.d(TAG, "aku mendapatkan data stress: ${dataStress} ")

        val anxietyBarchart = ModelBarchart()
        anxietyBarchart.title = "Anxiety"
        anxietyBarchart.dataChart = dataAnxiety
        Log.d(TAG, "aku mendapatkan data anxiety: ${dataAnxiety} ")

        return arraybar
    }

    val ArrayRecommendation :ArrayList<ModelRecommendation>get() {
        val arrayrecommendation = ArrayList<ModelRecommendation>()

        //1
        val activity1 = ModelRecommendation()
        activity1.imageActivity = R.drawable.meditiation
        activity1.judulActivity = "Meditasi"
        activity1.deskripsiActivity = "Dengan fokus pada pernapasan dan pikiran yang tenang, meditasi dapat membantu menenangkan pikiran dan tubuh"
        activity1.turotialActivity = "1.Temukan tempat yang tenang dan nyaman \n 2.Duduk dalam posisi yang nyaman \n 3.Fokus pada pernapasan \n 4.angan terganggu oleh pikiran \n 5.Gunakan mantram \n 6.Lakukan dengan rutin \n 7.Bersabarlah"
        arrayrecommendation.add(activity1)
        //2
        val activity2 = ModelRecommendation()
        activity2.imageActivity = R.drawable.yoga
        activity2.judulActivity = "Yoga"
        activity2.deskripsiActivity = "Latihan yoga termasuk gerakan yang dirancang untuk merilekskan tubuh dan membantu meningkatkan keseimbangan dan fleksibilitas"
        activity2.turotialActivity = "1.Pilih ruang yang tenang dan nyaman \n 2.Kenakan pakaian yang nyaman \n 3.Siapkan matras yoga \n 4.Fokus pada pernapasan \n 5.Mulai dengan gerakan-gerakan yang sederhana \n 6.Gerakan yang lancar \n 7.Tetap fokus pada diri sendiri \n 8.Selesaikan dengan relaksasi"
        arrayrecommendation.add(activity2)
        //1
        val activity3 = ModelRecommendation()
        activity3.imageActivity = R.drawable.pernapasan
        activity3.judulActivity = "Pernapasan dalam"
        activity3.deskripsiActivity = "Dengan mengambil napas dalam-dalam dan menghembuskan napas secara perlahan, dapat membantu merilekskan tubuh dan mengurangi stres"
        activity3.turotialActivity = "1.Pilih posisi yang nyaman \n 2.Fokus pada pernapasan \n 3.Letakkan tangan pada perut \n 4.Tarik napas lambat dan dalam \n 5.Tahan napas sejenak \n 6.Buang napas perlahan-lahan \n 7.Ulangi langkah-langkah ini \n 8.Bersabarlah"
        arrayrecommendation.add(activity3)
        //1
        val activity4 = ModelRecommendation()
        activity4.imageActivity = R.drawable.jogging
        activity4.judulActivity = "Olahraga"
        activity4.deskripsiActivity = "Berolahraga secara teratur dapat membantu merilekskan tubuh dan meningkatkan kesehatan secara keseluruhan"
        activity4.turotialActivity = "1.Pilih sepatu yang tepat \n 2.Pemanasan \n 3.Teknik lari \n 4.Atur nafas \n 5.Perlahan-lahan mulai \n 6.Jaga kestabilan dan keseimbangan \n 7.Hindari overtraining \n 8.Pendinginan"
        arrayrecommendation.add(activity4)

        return arrayrecommendation
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {}
            }

//            private val barSet = listOf(
//                "JAN" to 4F,
//                "FEB" to 7F,
//                "MAR" to 2F,
//                "MAY" to 2.3F,
//                "APR" to 5F,
//                "JUN" to 4F
//            )

            private const val animationDuration = 1000L

    }

}