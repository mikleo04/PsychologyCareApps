package com.example.psychologycareapps.adapter

import android.annotation.SuppressLint
import android.app.FragmentManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.psychologycareapps.AnxietyFragment
import com.example.psychologycareapps.DepressionFragment
import com.example.psychologycareapps.R
import com.example.psychologycareapps.StressFragment
import com.example.psychologycareapps.model.ModelMusic
import androidx.fragment.app.FragmentManager as FragmentManager1

class MusicAdapter(private val modelArrayList: ArrayList<ModelMusic>, private val context: Context,
                   private val itemClickListener: StressFragment, private val itemClickListenerD: DepressionFragment, private val itemClickListenerA: AnxietyFragment
): RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_music, parent, false

        )
        return ViewHolder(view, itemClickListener, itemClickListenerD, itemClickListenerA)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.titleMusic.text = modelArrayList[position].getTitleMusic()

        if (modelArrayList[position].hasImage()) {
            Glide.with(context)
                .load(modelArrayList[position].getmImageResourceID())
                .circleCrop()
                .into(holder.imageView)
        }
        if (modelArrayList[position].hasImage()) {
            Glide.with(context)
                .load(modelArrayList[position].getmImageResourceID())
                .circleCrop()
                .into(holder.imageView)
            holder.imageView.visibility = View.VISIBLE
        } else {
            holder.imageView.visibility = View.GONE
        }

        val fragmentManager = itemClickListener.fragmentManager
        val fragmentManagerD = itemClickListenerD.fragmentManager
        val fragmentManagerA = itemClickListenerA.fragmentManager
        val stressFragment = fragmentManager?.findFragmentByTag("Stress_Fragment")
        val depressionFragment = fragmentManagerD?.findFragmentByTag("Depression_Fragment")
        val anxietyFragment = fragmentManagerA?.findFragmentByTag("Anxiety_Fragment")

        holder.play.setOnClickListener { view ->
            Toast.makeText(view.context, "playing", Toast.LENGTH_SHORT).show()

            if (stressFragment?.isVisible == true) {
                itemClickListener.itemClick(position)
            } else if (depressionFragment?.isVisible == true) {
                itemClickListenerD.itemClick(position)
            } else if (anxietyFragment?.isVisible == true) {
                itemClickListenerA.itemClick(position)
            } else {
                Toast.makeText(context, "fragment tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }
        holder.pauseButton.setOnClickListener {

            if (stressFragment?.isVisible == true) {
                itemClickListener.pauseButton()
            } else if (depressionFragment?.isVisible == true) {
                itemClickListenerD.pauseButton()
            } else if (anxietyFragment?.isVisible == true) {
                itemClickListenerA.pauseButton()
            } else {
                Toast.makeText(context, "fragment tidak ditemukan", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun getItemCount(): Int {
        return modelArrayList.size
    }


    inner class ViewHolder(itemView: View, itemClickListener: StressFragment, itemClickListenerD: DepressionFragment, itemClickListenerA: AnxietyFragment) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var imageView: ImageView
        var play: ImageView
        var pauseButton: ImageView
        var titleMusic: TextView
        var itemClickListener: ItemClickListener? = null

        init {
            imageView = itemView.findViewById(R.id.iv_album)
            play = itemView.findViewById(R.id.btn_play)
            pauseButton = itemView.findViewById(R.id.btn_pause)
            titleMusic = itemView.findViewById(R.id.tv_titlemusic)
        }

        override fun onClick(view: View) {}
    }

}