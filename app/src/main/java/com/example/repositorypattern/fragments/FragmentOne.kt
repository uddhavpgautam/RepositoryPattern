package com.example.repositorypattern.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.repositorypattern.utils.MyFileUtils
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*

class FragmentOne : Fragment() {
    private lateinit var tv: TextView

    companion object {
        @JvmStatic
        fun newInstance() = FragmentOne()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_one, container, false)
        tv = view.findViewById(R.id.textView_fragment_one)

        //since we have tv instance inside launch closure, we must use Main (thread) dispatcher
        //using IO dispatcher means using different IO threads. It means anything
        //inside launch closure is in different thread, hence it is not guaranteed
        //tv is initialized before we set text in tv inside launch closure
        CoroutineScope(Dispatchers.Main).launch {
            //Kotlin extension
            val fileContents: String? =
                "product_json.json".useCoroutineToDownloadFile()

            //Kotlin let vs. null-check vs run
            if (fileContents != null) {
                //can be something long ops. running at this time and
                //before we set text, the fileContents may have changed if it is var
                //since fileContents is immutable so don't introduce extra variable like it in let
                //this block belongs to outer scope
                tv.text = fileContents
            }
            /*fileContents.run {
                //this whole block is scoped to this, which is String?
                //this block is not inline
                //if same variable inside and outside then outside variable is updated by inside
                tv.text = this
            }*/
            /*fileContents.let {
                //this would be safer than checking fileContents != null if fileContents is var not val
                //because it already hold exact same contents as fileContents
                //outside to inside scoping
                tv.text = it
            }*/
        }
        super.onCreateView(inflater, container, savedInstanceState)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TabLayoutMediator(
            requireActivity().findViewById(R.id.into_tab_layout_for_fragments),
            requireActivity().findViewById(R.id.pager_for_fragments)
        )
        { _, _ -> }.attach()
    }

    private suspend fun String.useCoroutineToDownloadFile(): String? {
        //reading local file can be done in Main thread, but not network ops.
        //but the local file is too big, we still get ANR
        //use either thread or coroutines

        val jsonString: String? =
            withContext(Dispatchers.IO) {
                MyFileUtils.readFileFromRawRes(this@useCoroutineToDownloadFile, requireActivity())
            }

        val jsonString1: String? = CoroutineScope(Dispatchers.IO).async {
            MyFileUtils.readFileFromAssets(this@useCoroutineToDownloadFile, requireActivity())
        }.await()

        val jsonString2: String? =
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                MyFileUtils.readFileFromAssets(this@useCoroutineToDownloadFile, requireActivity())
            }

        if (jsonString == jsonString1 && jsonString1 == jsonString2) {
            return jsonString1
        }
        return null
    }

}