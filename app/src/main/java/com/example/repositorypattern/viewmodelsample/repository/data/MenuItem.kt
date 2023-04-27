package com.example.repositorypattern.viewmodelsample.repository.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "menu_item_table")
data class MenuItem constructor(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id_menu_item")
    @NonNull
    var id_menu_item: Int,

    @SerializedName("name")
    var name: String?,

    @SerializedName("onclick")
    var onclick: String?,
)
