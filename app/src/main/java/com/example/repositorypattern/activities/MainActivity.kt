package com.example.repositorypattern.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.repositorypattern.R
import com.example.repositorypattern.fragments.HandlerBasedCommunication
import com.example.repositorypattern.fragments.RunOnUiThread
import com.example.repositorypattern.toolbars.utils.CollapsingToolbar

//eval `ssh-agent -s`; ssh-add /Users/roshanidahal/.ssh/id_rsa; git push

//never ever go with Inheritance BaseActivity approach. Things will get messy soon for BaseActivity
//when you have to add other logic and callbacks and you get even hard when you want to migrate
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val collapsingToolbar = CollapsingToolbar(this)
        collapsingToolbar.setCollapsingToolbar()

        //fragment transaction
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.viewpager2_hosting_fragment, ViewPager2HostingFragment.newInstance())
//            .commitNow()

        //fragment transaction
        /*supportFragmentManager.beginTransaction()
            .replace(R.id.viewpager2_hosting_fragment, HandlerBasedCommunication.newInstance())
            .commitNow()*/

        supportFragmentManager.beginTransaction()
            .replace(R.id.viewpager2_hosting_fragment, RunOnUiThread.newInstance())
            .commitNow()
    }
}

