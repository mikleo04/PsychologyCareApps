package com.example.psychologycareapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
//    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(HomeFragment.newInstance())
        btm_navigation.show(0)
        btm_navigation.add(MeowBottomNavigation.Model(0,R.drawable.baseline_home))
        btm_navigation.add(MeowBottomNavigation.Model(1,R.drawable.baseline_library))
        btm_navigation.add(MeowBottomNavigation.Model(2,R.drawable.baseline_note))
        btm_navigation.add(MeowBottomNavigation.Model(3,R.drawable.baseline_person))

        btm_navigation.setOnClickMenuListener {
            when(it.id) {
                0 -> {
                    Toast.makeText(this, "Home Fragment",Toast.LENGTH_SHORT).show()
                    replaceFragment(HomeFragment.newInstance())
                }1 -> {
                    Toast.makeText(this, "library Fragment",Toast.LENGTH_SHORT).show()
                    replaceFragment(LibraryFragment.newInstance())
                }2 -> {
                    Toast.makeText(this, "notes Fragment",Toast.LENGTH_SHORT).show()
                    replaceFragment(NotesFragment.newInstance())
                }3 -> {
                    Toast.makeText(this, "profile Fragment",Toast.LENGTH_SHORT).show()
                    replaceFragment(ProfileFragment.newInstance())
                }
        }

        //sign out
//        auth = FirebaseAuth.getInstance()
//
//        val email =  intent.getStringExtra("email")
//        val displayName =  intent.getStringExtra("name")
//        binding.tvName.text = email + "\n" + displayName
//
//        binding.btnSignout.setOnClickListener {
//            auth.signOut()
//            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun addFragment(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container, fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }

    private fun replaceFragment(fragment:Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container, fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }


}