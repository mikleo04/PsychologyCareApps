package com.example.psychologycareapps.adapter

import android.app.Activity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.AddnoteActivity
import com.example.psychologycareapps.R
import com.example.psychologycareapps.model.ModelNote
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import java.util.*

class NoteAdapter(options: FirestoreRecyclerOptions<ModelNote?>, var context: Activity) :
    FirestoreRecyclerAdapter<ModelNote, NoteAdapter.NoteViewHolder>(options) {

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int, note: ModelNote) {

        holder.titleTextView.text = note.getTitle()
        holder.contentTextView.text = note.getContent()
        holder.timestampTextView.text = SimpleDateFormat("MM/dd/yyyy", Locale.US).format(note.getTimestamp()?.toDate())
        holder.itemView.setOnClickListener { v: View? ->
            val docId = this.snapshots.getSnapshot(position).id

//            val addNoteFragment = AddNoteFragment()
//            val bundle = Bundle()
//            bundle.putString(AddNoteFragment.EXTRA_TITLE, note.getTitle())
//            bundle.putString(AddNoteFragment.EXTRA_CONTENT, note.getContent())
//            bundle.putString(AddNoteFragment.EXTRA_ID, docId)
//            addNoteFragment.arguments = bundle
            val intent = Intent(context, AddnoteActivity::class.java)
            intent.putExtra("title", note.getTitle())
            intent.putExtra("content", note.getContent())

            intent.putExtra("docId", docId)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }


    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView
        var contentTextView: TextView
        var timestampTextView: TextView

        init {
            titleTextView = itemView.findViewById(R.id.tv_notetitle)
            contentTextView = itemView.findViewById(R.id.tv_notecontent)
            timestampTextView = itemView.findViewById(R.id.tv_timestamp)
        }
    }
}