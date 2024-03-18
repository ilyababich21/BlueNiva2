package com.example.blueniva2.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.blueniva2.R
import com.example.blueniva2.databinding.ContentStartBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ContentStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.content_start)
        binding = ContentStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bNav.setupWithNavController(findNavController(R.id.nav_host_fragment))




    }






}