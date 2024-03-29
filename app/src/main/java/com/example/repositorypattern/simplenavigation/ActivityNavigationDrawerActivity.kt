package com.example.repositorypattern.simplenavigation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.repositorypattern.R
import com.example.repositorypattern.databinding.ActivityNavigationDrawerBinding
import com.google.android.material.navigation.NavigationView
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset


class ActivityNavigationDrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavigationDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNavigationDrawer.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navigationView
        val bottomNavigationView = binding.bottomNavigationView

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        //val toolbar: Toolbar = findViewById(R.id.toolbar)
        //NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        //setupActionBarWithNavController(navController, appBarConfiguration) //drawer menu
        navView.setupWithNavController(navController)
        bottomNavigationView.setupWithNavController(navController)
    }

    //don't use onOptionsItemSelected() implementation yourself
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun readAssets(file: String): String? {
        val jsonString: String
        try {
            val `is`: InputStream = assets.open(file)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            jsonString = String(buffer, Charset.defaultCharset())
        } catch (exception: IOException) {
            exception.printStackTrace()
            return null
        }
        return jsonString
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //let's create a dynamic menu from dynamicMenu.json file
        constructMenuKotlinX(readAssets("navigation/dynamicMenu.json"))

        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //if item id are different and clicking each item does some different actions
        /*if(item.itemId == R.id.xyz){
            //do something and return true
        }*/
        val navController = findNavController(R.id.nav_host_fragment)
        return NavigationUI.onNavDestinationSelected(
            item,
            navController
        ) || super.onOptionsItemSelected(item)
    }
}
