package com.example.repositorypattern.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R

//eval `ssh-agent -s`; ssh-add /Users/roshanidahal/.ssh/id_rsa; git push
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_one_container_view, FragmentOne.newInstance()).commitNow()
    }
}
