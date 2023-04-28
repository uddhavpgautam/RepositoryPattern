package com.example.repositorypattern.spinner

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


/**
 * ArrayAdapter required TextView
 */
class CustomSpinnerAdapter(
    context: Context,
    textViewResourceId: Int,
    values: Array<SpinnerData>
) : ArrayAdapter<SpinnerData>(context, textViewResourceId, values) {
    private val spinnerData: Array<SpinnerData>

    init {
        this.spinnerData = values
    }

    override fun getCount(): Int {
        return spinnerData.size
    }

    override fun getItem(position: Int): SpinnerData {
        return spinnerData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //just return SpinnerData eachItem
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val eachItem = super.getView(position, convertView, parent) as TextView
        eachItem.text = spinnerData[position].name
        return eachItem
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        label.text = spinnerData[position].name
        return label
    }
}
