package com.example.psychologycareapps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.adapter.MoodmusicAdapter
import com.example.psychologycareapps.adapter.RecommendationAdapter
import com.example.psychologycareapps.databinding.FragmentLibraryBinding
import com.example.psychologycareapps.model.ModelMoodmusic
import com.example.psychologycareapps.model.ModelRecommendation
import kotlinx.android.synthetic.main.fragment_library.*

class LibraryFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_library, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val anxiety : CardView = view.findViewById(R.id.cv_anxiety)
        anxiety.setOnClickListener(this)
        val stress : CardView = view.findViewById(R.id.cv_stress)
        stress.setOnClickListener(this)
        val depression : CardView = view.findViewById(R.id.cv_depression)
        depression.setOnClickListener(this)


    }

    override fun onClick(v : View) {
        if (v.id == R.id.cv_stress) {
            val stressFragment = StressFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(
                    R.id.fragment_container,
                    stressFragment,
                    "Stress_Fragment"
                )
                addToBackStack(null)
                commit()
                fragmentManager.executePendingTransactions();
            }
        } else if (v.id == R.id.cv_depression) {
            val depressionFragment = DepressionFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(
                    R.id.fragment_container,
                    depressionFragment,
                    "Depression_Fragment"
                )
                addToBackStack(null)
                commit()
            }
        } else if (v.id == R.id.cv_anxiety) {
            val anxietyFragment = AnxietyFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(
                    R.id.fragment_container,
                    anxietyFragment,
                    "Anxiety_Fragment"
                )
                addToBackStack(null)
                commit()
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            LibraryFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}