package com.example.repositorypattern.viewmodelsample.repository.converters

import androidx.room.TypeConverter
import com.example.repositorypattern.viewmodelsample.repository.data.MenuItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NavMenuConverters {
    @TypeConverter
    fun fromMenuItemList(menuItemList: List<MenuItem?>?): String? {
        val type = object : TypeToken<List<MenuItem?>?>() {}.type
        return Gson().toJson(menuItemList, type)
    }

    @TypeConverter
    fun toMenuItemList(menuListString: String?): List<MenuItem>? {
        val type = object : TypeToken<List<MenuItem?>?>() {}.type
        return Gson().fromJson<List<MenuItem>>(menuListString, type)
    }

    @TypeConverter
    fun stringListToJson(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToStringList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}

