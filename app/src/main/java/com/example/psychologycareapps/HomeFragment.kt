package com.example.psychologycareapps

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.psychologycareapps.adapter.BarchartAdapter
import com.example.psychologycareapps.adapter.RecommendationAdapter
import com.example.psychologycareapps.model.ModelBarchart
import com.example.psychologycareapps.model.ModelRecommendation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import lecho.lib.hellocharts.model.Axis
import lecho.lib.hellocharts.model.AxisValue
import lecho.lib.hellocharts.model.Column
import lecho.lib.hellocharts.model.ColumnChartData
import lecho.lib.hellocharts.model.SubcolumnValue
import lecho.lib.hellocharts.util.ChartUtils
import lecho.lib.hellocharts.view.ColumnChartView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("UNUSED_CHANGED_VALUE")
class HomeFragment : Fragment() {
    lateinit var rvRecommendation : RecyclerView
    var adapterBarchart: BarchartAdapter? = null
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val current = SimpleDateFormat("MM-yyyy").format(Calendar.getInstance().time)
    val moodData = arrayListOf<Float>()
    val depresiData = arrayListOf<Float>()
    val stressData = arrayListOf<Float>()
    val anxietyData = arrayListOf<Float>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_panastest.setOnClickListener {
            val intent = Intent(this@HomeFragment.requireContext(), PanasActivity::class.java)
            startActivity(intent)
        }

        btn_dasstest.setOnClickListener {
            val intent = Intent(this@HomeFragment.requireContext(), DassActivity::class.java)
            startActivity(intent)
        }

        fetchMood()
        fetchDepresi()
        fetchStress()
        fetchAnxiety()

        //Recycle recomendation
        val lmRecommendation = LinearLayoutManager(activity)
        lmRecommendation.orientation = LinearLayoutManager.VERTICAL
        rvRecommendation = view.findViewById(R.id.rv_recommendation)!!

        val adapterRecommendation = RecommendationAdapter(ArrayRecommendation, activity)
        rvRecommendation.setHasFixedSize(true)
        rvRecommendation.layoutManager = lmRecommendation
        rvRecommendation.adapter = adapterRecommendation

    }
    fun initBarChart(dx: ArrayList<Float>, chart: ColumnChartView, title: String){
        val columns = arrayListOf<Column>()
        for (i in 0 until dx.size){
            val values = ArrayList<SubcolumnValue>()
//            values.add(SubcolumnValue(dx[i], ChartUtils.COLOR_RED))
            values.add(SubcolumnValue(dx[i], Color.BLACK))
            val c = Column(values)
            c.setHasLabels(true)
            columns.add(c)
        }

        val valX = arrayListOf<AxisValue>()

        for (i in 0 until dx.size){
            val label = AxisValue(i.toFloat())
            label.setLabel("Week ${i+1}")
            valX.add(label)
        }

        val axisY = Axis()
        val axisX = Axis()
        axisY.name = "Score"
        axisX.name = title
        axisY.textColor = Color.BLACK
        axisX.textColor = Color.BLACK
        axisX.values = valX

        val colData = ColumnChartData(columns)
        colData.axisYLeft = axisY
        colData.axisXBottom = axisX
        chart?.setColumnChartData(colData)
    }
    fun fetchMood() {
        val refMood = FirebaseFirestore.getInstance().collection("Tes Psikologi").document(uid!!).collection("mood").document("value").collection(current)

        refMood.get().addOnSuccessListener {documents ->
            for (document in documents) {
                var value = document.get("value").toString()
                val score = if (value == "positif") 2F else 1F
                moodData.add(score)
            }
            initBarChart(moodData, requireActivity().findViewById(R.id.chartMood), "Mood")
            Log.d(TAG, "fetchMood: data size: " + moodData.size)
        }.addOnFailureListener {exception ->
            Log.d(TAG, "fetchMood: fetch failed", exception)
        }
    }

    fun fetchDepresi() {
        val refDepresi = FirebaseFirestore.getInstance().collection("Tes Psikologi").document(uid!!).collection("Depresi").document("value").collection(current)

        refDepresi.get().addOnSuccessListener { documents ->
            for (document in documents) {
                var value = document.get("value").toString().toFloat()
//                Log.d(TAG, "depresi value ${document.get("value")}")
                var score = (value/20 * 100)
                depresiData.add(score)
            }
            initBarChart(depresiData, requireActivity().findViewById(R.id.chartDepresi), "Depresi")
            Log.d(TAG, "fetchDepresi: data size: " + depresiData.size)
            return@addOnSuccessListener
        }.addOnFailureListener {exception ->
            Log.d(TAG, "get failed data depresi", exception)
            Log.d(TAG, "fetchDepresi: fetch failed", exception)
        }
    }

    fun fetchStress() {
        val refStress = FirebaseFirestore.getInstance().collection("Tes Psikologi").document(uid!!).collection("Stress").document("value").collection(current)

        refStress.get().addOnSuccessListener {documents ->
            for (document in documents) {
                var value = document.get("value").toString().toFloat()
                var score = (value / 20 * 100 )
                stressData.add(score)

            }
            initBarChart(stressData, requireActivity().findViewById(R.id.chartStress), "Stress")
            Log.d(TAG, "fetchStress: data size: " + stressData.size)
        }.addOnFailureListener {exception ->
            Log.d(TAG, "fetchStress: fetch failed", exception)
        }
    }

    fun fetchAnxiety() {
        val refAnxiety = FirebaseFirestore.getInstance().collection("Tes Psikologi").document(uid!!).collection("Anxiety").document("value").collection(current)

        refAnxiety.get().addOnSuccessListener {documents ->
            for (document in documents) {
                var value = document.get("value").toString().toFloat()
                var score = (value / 20 * 100)
                anxietyData.add(score)
            }
            initBarChart(anxietyData, requireActivity().findViewById(R.id.chartAnxiety), "Anxiety")
            Log.d(TAG, "fetchAnxiety: data size: " + anxietyData.size)
        }.addOnFailureListener {exception ->
            Log.d(TAG, "fetchAnxiety: fetch failed", exception)
        }
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
    }

}