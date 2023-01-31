package com.example.repositorypattern.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.repositorypattern.R


class ThreadCommunicationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_thread_communication, container, false)
    }

    override fun onResume() {
        super.onResume()
        ProducerThread().start()
        ConsumerThread().start()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThreadCommunicationFragment()
    }

    class ProducerThread : Thread() {
        private lateinit var data: String

        override fun run() {
            super.run()
            Looper.prepare()
            data = "Uddhav P. Gautam"
            val dataToSend: Message = Message.obtain()
            val bundle = Bundle()
            bundle.putString("data", data)
            dataToSend.data = bundle
//            ConsumerThread.mHandler.sendMessage(dataToSend)
            ConsumerThread.mHandler1.sendMessage(dataToSend)
            Looper.loop()
        }
    }

    class ConsumerThread : Thread() {
        companion object {
            lateinit var mHandler: Handler
            lateinit var mHandler1: Handler
        }

        override fun run() {
            super.run()
            Looper.prepare()

            mHandler = object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message) {
                    println(msg.data.toString())
                }

            }

            mHandler1 = object : Handler(Looper.myLooper()!!) {
                override fun handleMessage(msg: Message) {
                    println(msg.data.toString())
                }

            }
            Looper.loop()
        }
    }
}