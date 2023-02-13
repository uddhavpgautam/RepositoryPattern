package com.example.repositorypattern.fragments.intercommunication

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
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class RunOnUiThreadEventBus : Fragment(), View.OnClickListener {
    private val eventBus = EventBus.getDefault()
    private lateinit var firstEditText: EditText
    private lateinit var secondEditText: EditText
    private lateinit var firstButton: Button

    companion object {
        @JvmStatic
        fun newInstance() = RunOnUiThreadEventBus()
        lateinit var firstThreadHandler: Handler
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_thread_communication, container, false)
    }

    override fun onStart() {
        super.onStart()
        eventBus.register(this)
    }

    override fun onStop() {
        super.onStop()
        eventBus.unregister(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstEditText = requireActivity().findViewById(R.id.firstEditText)
        secondEditText = requireActivity().findViewById(R.id.secondEditText)
        firstButton = requireActivity().findViewById(R.id.firstButton)
        firstButton.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        val uiHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                //update UI
                val data: String? = msg.data.getString("data")
                data?.let {
                    updateSecondEditText(it)
                }
            }
        }
        runFirstThread(uiHandler)
    }

    override fun onClick(v: View?) {
        v?.let {
            when (v.id) {
                //R.id.firstButton -> sendMessageFromEditTextToFirstThread(firstEditText)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCustomEvent(event: CustomEvent) {

        //onCustomEvent is a ACK signal after firstThread initializes firstThreadHandler

        val msg: Message = Message.obtain()
        val bundle = Bundle()
        bundle.putString("data", firstEditText.text.toString())
        msg.data = bundle

        firstThreadHandler.sendMessage(msg) //wait for ACK from FirstThread
    }

    private fun updateSecondEditText(data: String) {
        requireActivity().findViewById<EditText>(R.id.secondEditText).setText(data)
    }

    private fun runFirstThread(uiHandler: Handler) {
        val firstThread = Thread {
            run {
                Looper.prepare() //create a looper
                firstThreadHandler = object : Handler(Looper.myLooper()!!) {
                    override fun handleMessage(msg: Message) {
                        val data: String? = msg.data.getString("data")
                        val modifiedMsg = "Hello $data" //received data modified
                        val dataToSend = Message.obtain()
                        val bundle = Bundle()
                        bundle.putString("data", modifiedMsg)
                        dataToSend.data = bundle

                        uiHandler.post {
                            Runnable {
                                this@RunOnUiThreadEventBus.requireActivity()
                                    .findViewById<EditText>(R.id.secondEditText)
                                    .setText(modifiedMsg)
                            }.run()
                        }

                    }
                }
                //send ACK to subscriber after firstThreadHandler is initialized
                eventBus.post(CustomEvent())
            }
            Looper.loop() //start a created looper
        }
        firstThread.start()
    }

}

class CustomEvent()
