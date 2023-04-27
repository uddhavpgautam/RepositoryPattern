package com.example.repositorypattern.viewmodelsample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.repositorypattern.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {

    //get view model
//    private val menuViewModel: MenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)
    }
}


