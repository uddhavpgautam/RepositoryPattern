package com.example.repositorypattern.simplenavigation

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

class NavigationMenuData {

    @Serializable
    data class MenuJson<T>(val menuData: T)

    @Serializable
    data class MenuData<T>(val version: String, val date: String, val menuItems: T)

    @Serializable
    data class MenuItem(val name: String, val onclick: String)
}

fun readFileUsingBufferedReader(file: String?): String {
    val text = StringBuilder()
    try {
        val br = file?.let { FileReader(it) }?.let { BufferedReader(it) }
        var line: String?
        while (br?.readLine().also { line = it } != null) {
            text.append(line)
            text.append('\n')
        }
        br?.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return text.toString()
    //return Files.readString(file?.let { Path.of(it) })
}

fun constructMenuKotlinX(fileJsonString: String?) {
    if (fileJsonString != null) {
        val deserializeDynamicMenuData =
            Json.decodeFromString<
                    NavigationMenuData.MenuJson<
                            NavigationMenuData.MenuData<
                                    List<
                                            NavigationMenuData.MenuItem
                                            >
                                    >
                            >
                    >(fileJsonString)
        println(deserializeDynamicMenuData.toString())
    }
}

