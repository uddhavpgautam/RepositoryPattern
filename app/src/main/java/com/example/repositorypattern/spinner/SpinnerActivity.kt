package com.example.repositorypattern.spinner

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.repositorypattern.R
import com.example.repositorypattern.databinding.ActivitySpinnerBinding


/**
 * SpinnerActivity
 */
class SpinnerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpinnerBinding
    private val courses = arrayOf("C", "C++", "Java", "Kotlin", "Microprocessor", "OS")
    private val spinnerData: Array<SpinnerData> = arrayOf(
        SpinnerData(1, "Joaquin"),
        SpinnerData(2, "Alberto")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySpinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSimpleAdapter(binding)
        setCustomDataAdapter(binding)
    }

    private fun setSimpleAdapter(binding: ActivitySpinnerBinding) {
        val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            /* how we want to show an individual item */
            R.layout.simple_spinner_item,
            courses
        ).apply {
            setDropDownViewResource(
                /* any textview, where we see all items when selected to choose */
                R.layout.simple_spinner_dropdown_item
            )
        }
        binding.simpleSpinner.adapter = arrayAdapter

        binding.simpleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int, id: Long
            ) {
                Toast.makeText(
                    applicationContext,
                    courses[position],
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            override fun onNothingSelected(adapter: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun setCustomDataAdapter(binding: ActivitySpinnerBinding) {
        val customSpinnerAdapter = CustomSpinnerAdapter(
            this,
            R.layout.simple_spinner_item,
            spinnerData
        )
        binding.customSpinner.adapter = customSpinnerAdapter
        binding.customSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int, id: Long
            ) {
                val spinnerData: SpinnerData = customSpinnerAdapter.getItem(position)
                Toast.makeText(
                    this@SpinnerActivity, "ID: " + spinnerData.id
                            + "\nName: " + spinnerData.name,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(adapter: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

}

