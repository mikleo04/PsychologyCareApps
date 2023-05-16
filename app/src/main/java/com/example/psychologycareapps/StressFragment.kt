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


class StressFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_stress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_playliststress)
        mAudioManager = requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        imageAndSoundModelArrayList = ArrayList()
        imageAndSoundModelArrayList!!.add(ModelMusic(R.drawable.samuel, R.raw.adagio, "Adagio for Strings"))
        imageAndSoundModelArrayList!!.add(ModelMusic(R.drawable.stanley, R.raw.cavatina, "Cavatina"))
        imageAndSoundModelArrayList!!.add(ModelMusic(R.drawable.claude, R.raw.clair, "Clair de Lune"))

        val adapter = MusicAdapter(imageAndSoundModelArrayList!!, requireActivity(), this, DepressionFragment(), AnxietyFragment())
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










//    private fun playAudio() {
//        val urlMusic = "https://drive.google.com/file/d/1DU2wyKFb5NZiteV1gXM9vWMfot-uYgCj/view?usp=share_link"
//        mediaPlayer = MediaPlayer()
//        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
//
//        try {
//            mediaPlayer!!.setDataSource(urlMusic)
//            mediaPlayer!!.prepare()
//            mediaPlayer!!.start()
//        }catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//        Toast.makeText(activity, "Music Berhasil Diputar", Toast.LENGTH_SHORT).show()
//    }
//
//    private fun pauseAudio() {
//        if (mediaPlayer!!.isPlaying) {
//            mediaPlayer!!.stop()
//            mediaPlayer!!.reset()
//            mediaPlayer!!.release()
//        }else {
//            Toast.makeText(activity, "Audio tidak sedang diputar", Toast.LENGTH_SHORT).show()
//        }
//    }

}