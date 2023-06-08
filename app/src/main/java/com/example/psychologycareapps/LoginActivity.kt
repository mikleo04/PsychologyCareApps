package com.example.psychologycareapps

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.psychologycareapps.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        //register button
        binding.tvRegisterNow.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        //forgot password button
        binding.tvForgotpassword.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity((intent))
        }

        // sign in with google account
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnGoogleaccount.setOnClickListener {
            signInGoogle()
        }

        // sign in with email & password
        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginemail.text.toString()
            val password = binding.etLoginpassword.text.toString()

            if (email.isEmpty()) {
                binding.etLoginemail.error = "Email harus diisi"
                binding.etLoginemail.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.etLoginemail.error = "Email tidak valid"
                binding.etLoginemail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etLoginpassword.error = "Password harus diisi"
                binding.etLoginpassword.requestFocus()
                return@setOnClickListener
            }

            LoginFirebase(email, password)
        }

    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account : GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("email", account.email)
                intent.putExtra("name", account.displayName)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Selamat datang $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT)   .show()
                }
            }
    }
}