package com.example.psychologycareapps

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.adapter.MusicAdapter
import com.example.psychologycareapps.model.ModelMusic

class DepressionFragment : Fragment() {

    private var mMediaplayer: MediaPlayer? = null
    private var mAudioManager: AudioManager? = null

    var imageAndSoundModelArrayList: ArrayList<ModelMusic>? = null
    private val mOnAudioFocusChangeListener =
        AudioManager.OnAudioFocusChangeListener { focusChange ->
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaplayer!!.pause()
                mMediaplayer!!.seekTo(0)
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaplayer!!.start()
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaplayer()
            }
        }

    private val mCompletionListener = MediaPlayer.OnCompletionListener { releaseMediaplayer() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_depression, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_playlistdepression)
        mAudioManager = requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        imageAndSoundModelArrayList = ArrayList()
        imageAndSoundModelArrayList!!.add(ModelMusic(R.drawable.justin_timberlake, R.raw.canot_stop, "Can't Stop the Feeling"))
        imageAndSoundModelArrayList!!.add(ModelMusic(R.drawable.pharrell_williams, R.raw.happy, "Happy"))
        imageAndSoundModelArrayList!!.add(ModelMusic(R.drawable.the_black_eyed_peas, R.raw.i_gotta_feeling, "I Gotta Feeling"))

        val adapter = MusicAdapter(imageAndSoundModelArrayList!!, requireActivity(), StressFragment() ,this, AnxietyFragment())
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

    }

    override fun onStop() {
        super.onStop()
        releaseMediaplayer()
    }

    private fun releaseMediaplayer() {
        if (mMediaplayer != null) {
            mMediaplayer!!.release()
            mMediaplayer = null
            mAudioManager!!.abandonAudioFocus(mOnAudioFocusChangeListener)
        }
    }

    fun itemClick(position: Int) {
        releaseMediaplayer()
        val imageAndSoundModel = imageAndSoundModelArrayList!![position]
        val result = mAudioManager!!.requestAudioFocus(
            mOnAudioFocusChangeListener,
            AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
        )
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mMediaplayer = MediaPlayer.create(
                activity,
                imageAndSoundModel.getmAudioResourceID()
            )
            mMediaplayer!!.start()
            mMediaplayer!!.setOnCompletionListener(mCompletionListener)
        }
    }

    fun pauseButton() {
        if (mMediaplayer == null) {
            Toast.makeText(activity, "There's nothing to play", Toast.LENGTH_SHORT).show()
        }
        if (mMediaplayer != null && mMediaplayer!!.isPlaying) {
            mMediaplayer!!.pause()
            Toast.makeText(activity, "Pause", Toast.LENGTH_SHORT).show()
        } else if (mMediaplayer != null) {
            mMediaplayer!!.seekTo(0)
            Toast.makeText(activity, "There's nothing to play", Toast.LENGTH_SHORT).show()
        }
    }


}