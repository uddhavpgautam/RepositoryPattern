package com.example.repositorypattern.viewmodelsample.repository.data

import androidx.annotation.NonNull
import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "menu_json_table")
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class MenuJson(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id_menu_json")
    @NonNull
    var id_menu_json: Int,

    @Embedded
    var menuData: MenuData? = null
)

