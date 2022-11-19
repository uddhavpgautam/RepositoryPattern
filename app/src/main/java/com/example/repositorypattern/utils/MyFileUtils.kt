package com.example.repositorypattern.utils

import android.content.Context
import org.apache.commons.io.FilenameUtils
import java.io.*

open class MyFileUtils {

    companion object {

        fun readFileFromAssets(fileName: String, context: Context): String? {
            try {
                val inputStream: InputStream = context.assets.open(fileName)
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


        fun readFileFromRawRes(fileName: String, context: Context): String? {
            try {
                /*Use of this function is discouraged because resource reflection makes it harder to
                perform build optimizations and compile-time verification of code. It is much more
                efficient to retrieve resources by identifier (e.g. R.foo.bar) than by name
                (e.g. getIdentifier("bar", "foo", null)).

                Ans: This is because it is matching based on string name, so system has to iterate over
                all string resources. This is slow as compared with matching with int id, which is done
                by hashmap
                 */

                //val inputStream: InputStream = context.resources.openRawResource(R.raw.product_json)

                /*val inputStream: InputStream = resources.openRawResource(
                    resources.getIdentifier(getString(R.string.product_json), "raw", packageName)
                )*/
                val inputStream: InputStream = context.resources.openRawResource(
                    context.resources.getIdentifier(
                        FileNameRemoveExtension.removeExt2(fileName),
                        "raw",
                        context.packageName
                    )
                )

                /*val resourceId: Int = context.resources.getIdentifier("product_json", "raw", context.packageName)
                val inputStream: InputStream = context.resources.openRawResource(resourceId)*/

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

        //wrap remove file extensions different ways into a separate class
        class FileNameRemoveExtension {

            companion object {

                private fun String.removeExtension(): String? {
                    //this@removeExtension holds the string value
                    return FilenameUtils.removeExtension(this@removeExtension)
                }

                val removeExt1: (String) -> String = {
                    FilenameUtils.removeExtension(it)
                }

                val removeExt3: (String) -> String?
                    get() = {
                            it: String -> it.removeExtension()
                    }

                val removeExt2: (String) -> String? = { fileName: String -> fileName.removeExtension() }

            }
        }

    }

}