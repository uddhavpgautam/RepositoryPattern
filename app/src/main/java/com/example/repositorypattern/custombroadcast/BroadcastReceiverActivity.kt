package com.example.repositorypattern.custombroadcast

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.repositorypattern.databinding.ActivityBroadcastReceiverBinding

/**
 * BroadcastReceiverActivity
 */

class BroadcastReceiverActivity : AppCompatActivity(),
    NetworkStateChangeReceiver.NetworkStateReceiverListener, CustomBroadcastReceiver.CustomBroadcastReceiverListener, OnClickListener {

    private lateinit var mNetworkStateChangeReceiver: NetworkStateChangeReceiver
    private lateinit var mCustomBroadCastReceiver: CustomBroadcastReceiver
    private lateinit var binding: ActivityBroadcastReceiverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBroadcastReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = binding.broadcastButton
        button.setOnClickListener(this)

        //NetworkStateChangeReceiver is defined in manifest
        mNetworkStateChangeReceiver =
            NetworkStateChangeReceiver()
        mNetworkStateChangeReceiver.addListener(this)
        registerReceiver(mNetworkStateChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        //CustomBroadcastReceiver is dynamically defined (not in manifest)
        mCustomBroadCastReceiver = CustomBroadcastReceiver()
        mCustomBroadCastReceiver.addListener(this)
        registerReceiver(mCustomBroadCastReceiver, IntentFilter("UDDHAV_PRASAD_GAUTAM"))
    }

    override fun networkAvailable() {
        Toast.makeText(this, "Network Available", Toast.LENGTH_SHORT).show()
    }

    override fun networkUnavailable() {
        Toast.makeText(this, "Network Unavailable", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mNetworkStateChangeReceiver.removeListener(this)
        unregisterReceiver(mNetworkStateChangeReceiver)

        mCustomBroadCastReceiver.removeListener(this)
        unregisterReceiver(mCustomBroadCastReceiver)
    }

    override fun onClick(v: View?) {
        //send custom broadcast using implicit intent
        sendBroadcast(Intent().setAction("UDDHAV_PRASAD_GAUTAM"))
    }

    override fun onCustomBroadcast() {
        Toast.makeText(this, "Custom Broadcast", Toast.LENGTH_SHORT).show()
    }

}

