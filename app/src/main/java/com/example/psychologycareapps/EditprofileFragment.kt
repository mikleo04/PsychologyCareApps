package com.example.psychologycareapps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.psychologycareapps.databinding.FragmentEditprofileBinding
import com.google.firebase.auth.FirebaseAuth

class EditprofileFragment : Fragment() {

    private lateinit var binding: FragmentEditprofileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditprofileBinding.inflate(inflater, container, false)
        return binding.root

    }

}