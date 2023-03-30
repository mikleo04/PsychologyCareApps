package com.example.psychologycareapps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.psychologycareapps.adapter.EmotionAdapter
import com.example.psychologycareapps.adapter.RecommendationAdapter
import com.example.psychologycareapps.databinding.FragmentHomeBinding
import com.example.psychologycareapps.model.ModelEmotion
import com.example.psychologycareapps.model.ModelRecommendation
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private var binding : FragmentHomeBinding? = null
    lateinit var rvEmotion : RecyclerView
    lateinit var rvRecommendation : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)


        // Recycler view emotion
        val lm = LinearLayoutManager(activity)
        lm.orientation = LinearLayoutManager.HORIZONTAL
        rvEmotion = view.findViewById(R.id.rv_emotion)

        val adapterEmotion = EmotionAdapter(ArrayEmotion, activity)
        rvEmotion.setHasFixedSize(true)
        rvEmotion.layoutManager = lm
        rvEmotion.adapter = adapterEmotion

        //Recycle view musik
        val lmRecommendation = LinearLayoutManager(activity)
        lmRecommendation.orientation = LinearLayoutManager.VERTICAL
        rvRecommendation = view.findViewById(R.id.rv_recommendation)

        val adapterRecommendation = RecommendationAdapter(ArrayRecommendation, activity)
        rvRecommendation.setHasFixedSize(true)
        rvRecommendation.layoutManager = lmRecommendation
        rvRecommendation.adapter = adapterRecommendation


        return view
    }

    val ArrayEmotion :ArrayList<ModelEmotion>get() {
        val arrayemotion = ArrayList<ModelEmotion>()

        //1
        val emotionDepresi = ModelEmotion()
        emotionDepresi.emotion = "Depresi"
        emotionDepresi.emotionImage = R.drawable.depresi
        arrayemotion.add(emotionDepresi)
        //2
        val emotionStress = ModelEmotion()
        emotionStress.emotion = "Stress"
        emotionStress.emotionImage = R.drawable.stress
        arrayemotion.add(emotionStress)
        //3
        val emotionSedih = ModelEmotion()
        emotionSedih.emotion = "Sedih"
        emotionSedih.emotionImage = R.drawable.sedih
        arrayemotion.add(emotionSedih)
        //4
        val emotionBiasa = ModelEmotion()
        emotionBiasa.emotion = "Biasa"
        emotionBiasa.emotionImage = R.drawable.biasa
        arrayemotion.add(emotionBiasa)
        //5
        val emotionSenang = ModelEmotion()
        emotionSenang.emotion = "Senang"
        emotionSenang.emotionImage = R.drawable.senang
        arrayemotion.add(emotionSenang)
        //6
        val emotionBahagia = ModelEmotion()
        emotionBahagia.emotion = "Bahagia"
        emotionBahagia.emotionImage = R.drawable.bahagia
        arrayemotion.add(emotionBahagia)

        return arrayemotion
    }

    val ArrayRecommendation :ArrayList<ModelRecommendation>get() {
        val arrayrecommendation = ArrayList<ModelRecommendation>()

        //1
        val musik1 = ModelRecommendation()
        musik1.albumImage = R.drawable.cover_tulus
        musik1.judulMusik = "Langit Abu-Abu"
        musik1.penyanyi = "Tulus"
        arrayrecommendation.add(musik1)
        //1
        val musik2 = ModelRecommendation()
        musik2.albumImage = R.drawable.mahalini
        musik2.judulMusik = "Sial"
        musik2.penyanyi = "Mahalini"
        arrayrecommendation.add(musik2)
        //1
        val musik3 = ModelRecommendation()
        musik3.albumImage = R.drawable.rizky_febian
        musik3.judulMusik = "Tak Lagi Sama"
        musik3.penyanyi = "Rizky Febian"
        arrayrecommendation.add(musik3)
        //1
        val musik4 = ModelRecommendation()
        musik4.albumImage = R.drawable.yura_yunita
        musik4.judulMusik = "Tutur Batin"
        musik4.penyanyi = "Yura Yunita"
        arrayrecommendation.add(musik4)

        return arrayrecommendation
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}