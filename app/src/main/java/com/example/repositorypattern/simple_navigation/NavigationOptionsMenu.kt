package com.example.repositorypattern.simple_navigation

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


class NavigationMenuData {
    @Serializable
    data class Menu<T>(
        val version: String,
        val date: String,
        val menuItem: T
    )

    @Serializable
    data class MenuItemLists<T>(val menus: T)

    @Serializable
    data class MenuItem(
        val id: String,
        val name: String,
        val onclick: String
    )
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
        val deserializedStringApartmentListWithKey1 =
            Json.decodeFromString<
                    NavigationMenuData.Menu<
                            NavigationMenuData.MenuItemLists<
                                    List<NavigationMenuData.MenuItem>
                                    >
                            >
                    >(fileJsonString)
        println(deserializedStringApartmentListWithKey1)
    }
}

fun main() {
    val fileJsonString = readFileUsingBufferedReader("/Users/roshanidahal/AndroidStudioProjects/RepositoryPattern/app/src/main/assets/navigation/dynamic_menu.json")
    constructMenuKotlinX(fileJsonString)
}








