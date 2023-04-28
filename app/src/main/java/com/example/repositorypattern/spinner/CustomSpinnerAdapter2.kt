package com.example.repositorypattern.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.repositorypattern.R


/**
 * ArrayAdapter requries TextView
 */
class CustomSpinnerAdapter2(
    context: Context,
    textViewResourceId: Int,
    spinnerData: Array<SpinnerData>,
    layoutInflater: LayoutInflater
) : ArrayAdapter<SpinnerData>(context, textViewResourceId, spinnerData) {
    private val spinnerData: Array<SpinnerData>
    private val layoutInflater: LayoutInflater

    init {
        this.spinnerData = spinnerData
        this.layoutInflater = layoutInflater
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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val eachItem = super.getView(position, convertView, parent) as TextView
        eachItem.text = "${spinnerData[position].id}  ${spinnerData[position].name}"
        return eachItem
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView1 = convertView
        val viewHolder: ViewHolderItem

        if (convertView1 == null) {
            convertView1 = layoutInflater.inflate(R.layout.custom_view_for_viewholder, parent, false)
            viewHolder = ViewHolderItem()
            viewHolder.textViewItem1 =
                convertView1.findViewById<View>(R.id.custom_view_for_vh_tv1) as TextView

            viewHolder.textViewItem2 =
                convertView1.findViewById<View>(R.id.custom_view_for_vh_tv2) as TextView

            convertView1?.tag = viewHolder

        } else {
            viewHolder = convertView1.tag as ViewHolderItem
        }

        val spinnerData: SpinnerData = spinnerData.get(position)

        viewHolder.textViewItem1.text = spinnerData.id.toString()
        viewHolder.textViewItem2.text = spinnerData.name

        if(convertView1 != null) {
            return convertView1
        } else {
            return TextView(context)
        }
    }

    internal class ViewHolderItem {
        lateinit var textViewItem1: TextView
        lateinit var textViewItem2: TextView

    }
}

