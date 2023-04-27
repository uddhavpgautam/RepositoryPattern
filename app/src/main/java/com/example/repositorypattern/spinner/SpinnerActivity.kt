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
class SpinnerActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var forCustomData: Boolean = false
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

        forCustomData = true
        if (forCustomData) {
            val customSpinnerAdapter = CustomSpinnerAdapter(
                this,
                android.R.layout.simple_spinner_item,
                spinnerData
            )
            binding.spinner.adapter = customSpinnerAdapter
            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        } else {
            binding.spinner.onItemSelectedListener = this
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
            binding.spinner.adapter = arrayAdapter
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(
            applicationContext,
            courses[position],
            Toast.LENGTH_LONG
        )
            .show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}

