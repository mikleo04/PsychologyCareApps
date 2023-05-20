package com.example.psychologycareapps

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.adapter.NoteAdapter
import com.example.psychologycareapps.model.ModelNote
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class NotesFragment : Fragment(), View.OnClickListener {

     var recyclerView : RecyclerView? = null
     var noteAdapter: NoteAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rv_note)!!
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val ref = FirebaseFirestore.getInstance().collection("notes").document(uid!!).collection("my_notes").document()
        val query = ref.getParent().orderBy("timestamp", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<ModelNote>().setQuery(query, ModelNote::class.java).build()
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        noteAdapter = activity?.let { NoteAdapter(options, it) }
        recyclerView!!.adapter = noteAdapter


        val addNote : FloatingActionButton = view.findViewById(R.id.fab_addnote)
        addNote.setOnClickListener(this)


    }


    override fun onClick(view: View?) {
       val intent = Intent(this@NotesFragment.requireContext(), AddnoteActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        noteAdapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        noteAdapter?.stopListening()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        noteAdapter?.notifyDataSetChanged()
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            NotesFragment().apply {
                arguments = Bundle().apply {}
            }
    }

}