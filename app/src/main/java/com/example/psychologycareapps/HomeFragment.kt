package com.example.psychologycareapps

import android.content.Intent
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {
    lateinit var rvRecommendation : RecyclerView
    // null biar bisa di check sebelum akses
    var adapterBarchart: BarchartAdapter? = null

    //    lateinit var rvBarchart : RecyclerView
//    var data = arrayListOf<Pair<String,Float>>() // nama var membuat gagal paham
//    var dataDepresi = arrayListOf<Pair<String,Float>>()
//    var dataStress = arrayListOf<Pair<String,Float>>()
//    var dataAnxiety = arrayListOf<Pair<String,Float>>()

    val uid = FirebaseAuth.getInstance().currentUser?.uid

    //no idea nama var yg cocok :v, aku pribadi lebih prefer yg ga berulang di inline kan, lebih mudah di baca
    val current = SimpleDateFormat("MMMM-yyyy").format(Calendar.getInstance().time)

    val moodData = arrayListOf<Pair<String, Float>>()
    val depresiData = arrayListOf<Pair<String, Float>>()
    val stressData = arrayListOf<Pair<String, Float>>()
    val anxietyData = arrayListOf<Pair<String, Float>>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init button, and basic functionality
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
        initBarChartRv(view)
        //Recycle recomendation
        val lmRecommendation = LinearLayoutManager(activity)
        lmRecommendation.orientation = LinearLayoutManager.VERTICAL
        rvRecommendation = view.findViewById(R.id.rv_recommendation)!!

        val adapterRecommendation = RecommendationAdapter(ArrayRecommendation, activity)
        rvRecommendation.setHasFixedSize(true)
        rvRecommendation.layoutManager = lmRecommendation
        rvRecommendation.adapter = adapterRecommendation

    }



    // pecah pengambilan data menjadi fungsi spesifik tertentu,
    // teknik ini mempermudah untuk membuat action reload, retry, dll [contoh: reloadBtn->onClick->call_fetchMood()]
    fun fetchMood(){
        val refMood = FirebaseFirestore.getInstance().collection("Tes Psikologi").document(uid!!).collection("mood").document("value").collection(current)

        // get data mood
        refMood.get().addOnSuccessListener {documents ->
            var tgl = 1
            for (document in documents) {
                var value = document.get("value").toString()
                Log.d(TAG, "mood value ${document.get("value")}")
                var score = if (value == "positif") 2F else 1F

                moodData.add(Pair(tgl.toString(), score))
                tgl++
            }
            if (moodData.size == 1) {
                moodData.add(Pair("", 0F))
            }
            updateBarChart()
            Log.d(TAG, "fetchMood: data size: " + moodData.size)
        }.addOnFailureListener {exception ->
            Log.d(TAG, "fetchMood: fetch failed", exception)

        }
    }
    fun fetchDepresi(){
        val refDepresi = FirebaseFirestore.getInstance().collection("Tes Psikologi").document(uid!!).collection("Depresi").document("value").collection(current)

        // get data depresi
        refDepresi.get().addOnSuccessListener { documents ->
            var week = 1
            for (document in documents) {
                var value = document.get("value").toString().toInt()
                Log.d(TAG, "depresi value ${document.get("value")}")
                var score = value / 2
                depresiData.add(Pair(week.toString(), score.toFloat()))
                week++
            }
            if (depresiData.size == 1) {
                depresiData.add(Pair("", 0F))
            }
            updateBarChart()
            Log.d(TAG, "fetchDepresi: data size: " + depresiData.size)
            return@addOnSuccessListener
        } .addOnFailureListener {exception->
            Log.d(TAG, "fetchDepresi: fetch failed", exception)

        }
    }
    fun fetchStress(){
        val refStress = FirebaseFirestore.getInstance().collection("Tes Psikologi").document(uid!!).collection("Stress").document("value").collection(current)

        //get data stress
        refStress.get().addOnSuccessListener { documents ->
            var week = 1
            for (document in documents) {
                var value = document.get("value").toString().toInt()
                Log.d(TAG, "stress value ${document.get("value")}")
                var score = value / 2
                stressData.add(Pair(week.toString(), score.toFloat()))
                week++
            }
            if (stressData.size == 1) {
                stressData.add(Pair("", 0F))
            }
            updateBarChart()
            Log.d(TAG, "fetchStress: data size: " + stressData.size)

        } .addOnFailureListener {exception->
            Log.d(TAG, "fetchStress: fetch failed", exception)
        }
    }
    fun fetchAnxiety(){
        val refAnxiety = FirebaseFirestore.getInstance().collection("Tes Psikologi").document(uid!!).collection("Anxiety").document("value").collection(current)

        //get data Anxiety
        refAnxiety.get().addOnSuccessListener { documents ->
            var week = 1
            for (document in documents) {
                var value = document.get("value").toString().toInt()
                Log.d(TAG, "anxiety value ${document.get("value")}")
                var score = value / 2
                anxietyData.add(Pair(week.toString(), score.toFloat()))
                week++
            }
            if (anxietyData.size == 1) {
                anxietyData.add(Pair("", 0F))
            }
            updateBarChart()
            Log.d(TAG, "fetchAnxiety: data size: " + anxietyData.size)

        } .addOnFailureListener {exception->
            Log.d(TAG, "fetchAnxiety: fetch failed", exception)
        }
    }
    // alternatif: view ga bisa diakses langsung dari function ini,
    // jadi pass view ke showBarChart atau pindahkan semua kode showBarChart di akhir
    // addOnSuccessListener [terlalu repetisi]
    fun initBarChartRv(view: View){
        //Recycle Barchart
        val lmBarchart = LinearLayoutManager(activity)
        lmBarchart.orientation = LinearLayoutManager.HORIZONTAL
        val rvBarchart: RecyclerView = view.findViewById(R.id.rv_barchart)
        val chartData = arrayListOf<ModelBarchart>(
            ModelBarchart("Mood", moodData),
            ModelBarchart("Depresi", depresiData),
            ModelBarchart("Stress", stressData),
            ModelBarchart("Anxiety", anxietyData)
        )
        /*Snap*/
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvBarchart)
        /*End of Snap*/
        adapterBarchart = BarchartAdapter(chartData, requireActivity())
        rvBarchart.setHasFixedSize(true)
        rvBarchart.layoutManager = lmBarchart
        rvBarchart.adapter = adapterBarchart
    }

    fun updateBarChart(){
        // si bambang di bawah ini untuk memberitahu adapter bahwa telah
        // terjadi perubahan data. ini kurang efisien, tapi yg paling joss dan simple
        // btw function wrapper ini supaya mudah dibaca aja ("oh ternyata mengupdate tampilan bar chart")
        adapterBarchart?.notifyDataSetChanged() // check if null ala kotlin
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
        private const val animationDuration = 1000L
    }

}