package com.raedghazal.githubuserssearchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raedghazal.githubuserssearchapp.databinding.ActivityMainBinding
import com.raedghazal.githubuserssearchapp.presentation.users.SearchUsersFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}