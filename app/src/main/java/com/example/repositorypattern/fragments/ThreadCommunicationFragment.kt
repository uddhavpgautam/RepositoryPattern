package com.example.repositorypattern.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.repositorypattern.R


class ThreadCommunicationFragment : Fragment(), View.OnClickListener {
    private lateinit var firstEditText: EditText
    private lateinit var secondEditText: EditText
    private lateinit var firstButton: Button
    private lateinit var secondButton: Button

    companion object {
        @JvmStatic
        fun newInstance() = ThreadCommunicationFragment()
        lateinit var uiHandler: Handler
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_thread_communication, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstEditText = requireActivity().findViewById(R.id.firstEditText)
        secondEditText = requireActivity().findViewById(R.id.secondEditText)
        firstButton = requireActivity().findViewById(R.id.firstButton)
        firstButton.setOnClickListener(this)
        secondButton = requireActivity().findViewById(R.id.secondButton)
        secondButton.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        FirstThread().start()

        //MainLooper is already created so, no need to create and start it
        uiHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                //update UI
                val data: String? = msg.data.getString("data")
                data?.let {
                    updateSecondEditText(it)
                }
            }

        }

    }

    private fun sendMessageFromEditTextToFirstThread(firstEditText: EditText) {
        val dataToSend: Message = Message.obtain()
        val bundle = Bundle()
        bundle.putString("data", firstEditText.text.toString())
        dataToSend.data = bundle
        FirstThread.firstThreadHandler.sendMessage(dataToSend)
    }

    class FirstThread : Thread() {
        companion object {
            lateinit var firstThreadHandler: Handler
        }

        override fun run() {
            super.run()
            Looper.prepare()
            firstThreadHandler = object : Handler(Looper.myLooper()!!) {
                override fun handleMessage(msg: Message) {
                    val data: String? = msg.data.getString("data")
                    val toSend = "Hello $data" //received data modified
                    val dataToSend = Message.obtain()
                    val bundle = Bundle()
                    bundle.putString("data", toSend)
                    dataToSend.data = bundle
                    uiHandler.sendMessage(dataToSend)
                }

            }
            Looper.loop()
        }
    }

    private fun updateSecondEditText(data: String) {
        requireActivity().findViewById<EditText>(R.id.secondEditText).setText(data)
    }

    override fun onClick(v: View?) {
        v?.let {
            when (v.id) {
                R.id.firstButton -> sendMessageFromEditTextToFirstThread(firstEditText)
            }
        }
    }
}
