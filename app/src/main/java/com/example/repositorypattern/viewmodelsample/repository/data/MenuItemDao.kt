package com.example.repositorypattern.viewmodelsample.repository.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MenuItemDao {

    @Query("SELECT * from menu_item_table")
    fun getMenuItems(): List<MenuItem>

    @Query("SELECT * from menu_item_table")
    fun getMenuItemsInLiveData(): LiveData<List<MenuItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(menuItem: MenuItem)

    @Query("DELETE FROM menu_item_table")
    fun deleteAll()
}
