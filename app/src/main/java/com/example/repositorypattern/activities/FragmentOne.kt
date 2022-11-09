package com.example.repositorypattern.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*
import org.apache.commons.io.FilenameUtils
import java.io.*

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
        super.onViewCreated(view, savedInstanceState)
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
                readFileFromRawRes(this@useCoroutineToDownloadFile)
            }

        val jsonString1: String? = CoroutineScope(Dispatchers.IO).async {
            readFileFromAssets(this@useCoroutineToDownloadFile)
        }.await()

        val jsonString2: String? =
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                readFileFromAssets(this@useCoroutineToDownloadFile)
            }

        if (jsonString == jsonString1 && jsonString1 == jsonString2) {
            return jsonString1
        }
        return null
    }

    private fun readFileFromAssets(fileName: String): String? {
        try {
            val inputStream: InputStream = requireActivity().assets.open(fileName)
            val reader: Reader = BufferedReader(InputStreamReader(inputStream, "UTF8"))

            val writer: Writer = StringWriter()
            val buffer = CharArray(1024)
            reader.use { it ->
                var n: Int
                while (it.read(buffer).also { n = it } != -1) {
                    writer.write(buffer, 0, n)
                }
            }
            return writer.toString()
        } catch (e: java.lang.Exception) {
            //guarantees all type of Exceptions
            return null
        }
    }

    private fun readFileFromRawRes(fileName: String): String? {
        try {
            /*Use of this function is discouraged because resource reflection makes it harder to
            perform build optimizations and compile-time verification of code. It is much more
            efficient to retrieve resources by identifier (e.g. R.foo.bar) than by name
            (e.g. getIdentifier("bar", "foo", null)).

            Ans: This is because it is matching based on string name, so system has to iterate over
            all string resources. This is slow as compared with matching with int id, which is done
            by hashmap
             */

            val inputStream: InputStream = resources.openRawResource(R.raw.product_json)

            /*val inputStream: InputStream = resources.openRawResource(
                resources.getIdentifier(getString(R.string.product_json), "raw", packageName)
            )*/
            /*val inputStream: InputStream = resources.openRawResource(
                resources.getIdentifier(
                    fileName.removeExtension(),
                    "raw",
                    requireActivity().packageName
                )
            )*/
            /*val inputStream: InputStream = resources.
            (
                resources.getIdentifier("product_json", "raw", packageName)
            )*/

            val reader: Reader = BufferedReader(InputStreamReader(inputStream, "utf-8"))

            val writer: Writer = StringWriter()
            val buffer = CharArray(1024)
            reader.use { it ->
                var n: Int
                while (it.read(buffer).also { n = it } != -1) {
                    writer.write(buffer, 0, n)
                }
            }
            return writer.toString()
        } catch (e: java.lang.Exception) {
            //guarantees all type of Exceptions
            return null
        }
    }
}

fun String.removeExtension(): String? {
    //this@removeExtension holds the string value
    return FilenameUtils.removeExtension(this@removeExtension)
}