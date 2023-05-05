package com.example.psychologycareapps

import android.app.Activity
import android.os.Bundle
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.adapter.MoodmusicAdapter
import com.example.psychologycareapps.adapter.RecommendationAdapter
import com.example.psychologycareapps.databinding.FragmentLibraryBinding
import com.example.psychologycareapps.model.ModelMoodmusic
import com.example.psychologycareapps.model.ModelRecommendation

class LibraryFragment : Fragment() {
    private var binding : FragmentLibraryBinding? = null
    lateinit var rvPlaylist : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_library, container, false)

        //Recycle view playlist

        val moodPlaylist = LinearLayoutManager(activity)
        moodPlaylist.orientation = LinearLayoutManager.VERTICAL
        rvPlaylist = view.findViewById(R.id.rv_playlist)

        val adapterPlaylist = MoodmusicAdapter(ArrayPlaylist, activity)
        rvPlaylist.setHasFixedSize(true)
        rvPlaylist.layoutManager = moodPlaylist
        rvPlaylist.adapter = adapterPlaylist

        return view
    }

    val ArrayPlaylist : ArrayList<ModelMoodmusic>get() {
        val arrayplaylist = ArrayList<ModelMoodmusic>()

        //1
        val playList1 = ModelMoodmusic()
        playList1.backgroundMoodmusic = R.drawable.depression_cover
        playList1.moodMusic = "Depression"
        arrayplaylist.add(playList1)
        //2
        val playList2 = ModelMoodmusic()
        playList2.backgroundMoodmusic = R.drawable.stress
        playList2.moodMusic = "Stress"
        arrayplaylist.add(playList2)
        //3
        val playList3 = ModelMoodmusic()
        playList3.backgroundMoodmusic = R.drawable.sad
        playList3.moodMusic = "Sad"
        arrayplaylist.add(playList3)
        //4
        val playList4 = ModelMoodmusic()
        playList4.backgroundMoodmusic = R.drawable.happy
        playList4.moodMusic = "Happy"
        arrayplaylist.add(playList4)

        return arrayplaylist
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LibraryFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}