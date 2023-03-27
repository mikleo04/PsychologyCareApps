package com.example.psychologycareapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import com.example.psychologycareapps.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.tvLoginNow.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.etRegisterfullname.text.toString()
            val username = binding.etRegisterusername.text.toString()
            val age = binding.etRegisterumur.text.toString()
            val contact = binding.etRegistercontact.text.toString()
            val email = binding.etRegisteremail.text.toString()
            val password = binding.etRegisterpassword.text.toString()

            // validasi form
            if (name.isEmpty()) {
                binding.etRegisterfullname.error = "Nama harus diisi"
                binding.etRegisterfullname.requestFocus()
                return@setOnClickListener
            }

            if (username.isEmpty()) {
                binding.etRegisterusername.error = "Username harus diisi"
                binding.etRegisterusername.requestFocus()
                return@setOnClickListener
            }

            if (age.isEmpty()) {
                binding.etRegisterumur.error = "Umur harus diisi"
                binding.etRegisterumur.requestFocus()
                return@setOnClickListener
            }

            if (contact.isEmpty()) {
                binding.etRegistercontact.error = "Contact harus diisi"
                binding.etRegistercontact.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.PHONE.matcher(contact).matches()) {
                binding.etRegistercontact.error = "Contact tidak valid"
                binding.etRegistercontact.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                binding.etRegisteremail.error = "Email harus diisi"
                binding.etRegisteremail.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.etRegisteremail.error = "Email tidak valid"
                binding.etRegisteremail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etRegisterpassword.error = "Password harus diisi"
                binding.etRegisterpassword.requestFocus()
                return@setOnClickListener
            }
            if (password.length < 6) {
                binding.etRegisterpassword.error = "Password harus lebih dari 6 karakter"
                binding.etRegisterpassword.requestFocus()
                return@setOnClickListener
            }

            RegisterFirebase(name, username, age, contact, email, password)
        }
    }

    private fun RegisterFirebase(name: String, username: String, age: String, contact: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, OnboardingActivity::class.java)  // belum pasti
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT)   .show()
                }
            }
    }
}