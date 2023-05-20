package com.example.psychologycareapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.psychologycareapps.databinding.ActivityAddnoteBinding
import com.example.psychologycareapps.model.ModelNote
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddnoteActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddnoteBinding
    var isEditMode : Boolean = false

    @Suppress("SENSELESS_COMPARISON")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddnoteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val titleData = intent.getStringExtra("title")
        val contentData = intent.getStringExtra("content")
        val docId = intent.getStringExtra("docId")

        if (docId != null && !docId.isEmpty()) {
            isEditMode = true
        }

        binding.etNotetitle.setText(titleData)
        binding.etNotecontent.setText(contentData)

        if (isEditMode) {
            binding.btnDelete.visibility = View.VISIBLE
        }

        binding.btnSubmitnote.setOnClickListener{
            val title = binding.etNotetitle.text.toString()
            val content = binding.etNotecontent.text.toString()
            if (title == null || title.isEmpty()) {
                binding.etNotetitle.error = "Tuliskan Bagaimana perasaaan mu"
                binding.etNotetitle.requestFocus()
                return@setOnClickListener
            }

            val note = ModelNote()
            note.setTitle(title)
            note.setContent(content)
            note.setTimestamp(Timestamp.now())

            saveNote(note, docId)
        }

        binding.btnDelete.setOnClickListener {
            deleteNote(docId)
        }

    }

    private fun deleteNote(docId: String?) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val ref = FirebaseFirestore.getInstance().collection("notes").document(uid!!).collection("my_notes").document(docId!!)
        ref.delete().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(applicationContext, "Note Berhasil dihapus", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(applicationContext, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveNote(note: ModelNote, docId: String?) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        val ref = if (isEditMode) {
            FirebaseFirestore.getInstance().collection("notes").document(uid!!).collection("my_notes").document(docId!!)
        } else {
            FirebaseFirestore.getInstance().collection("notes").document(uid!!).collection("my_notes").document()
        }

        ref.set(note).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(applicationContext, "Note Berhasil disimpan", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(applicationContext, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}