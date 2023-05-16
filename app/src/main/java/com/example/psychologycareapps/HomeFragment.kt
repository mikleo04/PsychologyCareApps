package com.example.psychologycareapps

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.adapter.RecommendationAdapter
import com.example.psychologycareapps.databinding.FragmentHomeBinding
import com.example.psychologycareapps.model.ModelRecommendation
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    lateinit var rvRecommendation : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        barChart.animation.duration = animationDuration
        barChart.animate(barSet)


        //Recycle recomendation
        val lmRecommendation = LinearLayoutManager(activity)
        lmRecommendation.orientation = LinearLayoutManager.VERTICAL
        rvRecommendation = view.findViewById(R.id.rv_recommendation)!!

        val adapterRecommendation = RecommendationAdapter(ArrayRecommendation, activity)
        rvRecommendation.setHasFixedSize(true)
        rvRecommendation.layoutManager = lmRecommendation
        rvRecommendation.adapter = adapterRecommendation

    }


    val ArrayRecommendation :ArrayList<ModelRecommendation>get() {
        val arrayrecommendation = ArrayList<ModelRecommendation>()

        //1
        val activity1 = ModelRecommendation()
        activity1.imageActivity = R.drawable.meditiation
        activity1.judulActivity = "Meditasi"
        activity1.deskripsiActivity = "Dengan fokus pada pernapasan dan pikiran yang tenang, meditasi dapat membantu menenangkan pikiran dan tubuh"
        arrayrecommendation.add(activity1)
        //2
        val activity2 = ModelRecommendation()
        activity2.imageActivity = R.drawable.yoga
        activity2.judulActivity = "Yoga"
        activity2.deskripsiActivity = "Latihan yoga termasuk gerakan yang dirancang untuk merilekskan tubuh dan membantu meningkatkan keseimbangan dan fleksibilitas"
        arrayrecommendation.add(activity2)
        //1
        val activity3 = ModelRecommendation()
        activity3.imageActivity = R.drawable.pernapasan
        activity3.judulActivity = "Pernapasan dalam"
        activity3.deskripsiActivity = "Dengan mengambil napas dalam-dalam dan menghembuskan napas secara perlahan, dapat membantu merilekskan tubuh dan mengurangi stres"
        arrayrecommendation.add(activity3)
        //1
        val activity4 = ModelRecommendation()
        activity4.imageActivity = R.drawable.jogging
        activity4.judulActivity = "Olahraga"
        activity4.deskripsiActivity = "Berolahraga secara teratur dapat membantu merilekskan tubuh dan meningkatkan kesehatan secara keseluruhan"
        arrayrecommendation.add(activity4)

        return arrayrecommendation
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {}
            }

            private val barSet = listOf(
                "JAN" to 4F,
                "FEB" to 7F,
                "MAR" to 2F,
                "MAY" to 2.3F,
                "APR" to 5F,
                "JUN" to 4F
            )

            private const val animationDuration = 1000L

    }

}