package com.example.repositorypattern.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import kotlinx.coroutines.*
import java.io.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv = findViewById<TextView>(R.id.textView)

        CoroutineScope(Dispatchers.IO).launch {
            //Kotlin extension
            val fileContents: String? = "product_json_file.json".useCoroutineToDownloadFile()

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
    }

    private suspend fun String.useCoroutineToDownloadFile(): String? {
        //reading local file can be done in Main thread, but not network ops.
        //but the local file is too big, we still get ANR
        //use either thread or coroutines

        val jsonString: String? =
            withContext(Dispatchers.IO) {
                readFileFromAssets(this@useCoroutineToDownloadFile)
            }

        val jsonString1: String? = CoroutineScope(Dispatchers.IO).async {
            readFileFromAssets(this@useCoroutineToDownloadFile)
        }.await()

        val jsonString2: String? =
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                readFileFromAssets(this@useCoroutineToDownloadFile)
            }

        if (jsonString == jsonString1 && jsonString == jsonString2) {
            return jsonString
        }
        return null

    }

    private fun readFileFromAssets(fileName: String): String? {
        try {
            val inputStream: InputStream = assets.open(fileName)
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
}