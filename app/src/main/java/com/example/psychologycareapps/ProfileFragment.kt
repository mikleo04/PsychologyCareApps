package com.example.psychologycareapps

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.values
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.ByteArrayOutputStream

class ProfileFragment : Fragment() {

    companion object {
        const val REQUEST_CAMERA = 100
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    private lateinit var imagUri : Uri
    private lateinit var database : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        readData(uid)

        iv_profile.setOnClickListener{

            inntentCamera()

        }
    }

    private fun readData(uid: String?) {
        Log.d("Id user", uid.toString())
        database = FirebaseDatabase.getInstance().getReference("Users").child(uid!!)
        database.addListenerForSingleValueEvent(object  : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                val name = snapshot.child("name").value
                val username = snapshot.child("username").value
                val email = snapshot.child("email").value
                val age = snapshot.child("age").value
                val contact = snapshot.child("contact").value
                tv_profilefullname.text = name.toString()
                tv_profileusername.text = username.toString()
                tv_profileemail.text = email.toString()
                tv_profileage.text = age.toString()
                tv_profilecontact.text = contact.toString()

                Log.d("Data name", name.toString())

                Toast.makeText(activity, "Data Berhasil didapatkan", Toast.LENGTH_SHORT).show()

            }
        })
    }

    @Suppress("DEPRECATION")
    private fun inntentCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            activity?.packageManager?.let {
                intent.resolveActivity(it).also {
                    startActivityForResult(intent, REQUEST_CAMERA)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            val imgBitmap = data?.extras?.get("data")as Bitmap
            uploadBitmap(imgBitmap)
        }

    }

    private fun uploadBitmap(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref = FirebaseStorage.getInstance().reference.child("img/${FirebaseAuth.getInstance().currentUser?.uid}")

        imgBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val image = baos.toByteArray()

        ref.putBytes(image)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener{
                        it.result?.let {
                            imagUri = it
                            iv_profile.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }


    }
}