package com.example.repositorypattern.viewmodelsample.repository.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "menu_data_table")
data class MenuData constructor(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id_menu_data")
    @Expose
    var id_menu_data: Int,

    @SerializedName("version")
    var version: String?,

    @SerializedName("date")
    var date: String?,

    var menuItems: List<MenuItem>
)



