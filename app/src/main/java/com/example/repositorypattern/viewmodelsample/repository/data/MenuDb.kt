package com.example.repositorypattern.viewmodelsample.repository.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.repositorypattern.viewmodelsample.repository.converters.NavMenuConverters
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream

@Database(
    entities = [MenuJson::class, MenuData::class, MenuItem::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(NavMenuConverters::class)
abstract class MenuDb : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao

    companion object {
        @Volatile
        private var INSTANCE: MenuDb? = null

        fun getDatabase(context: Context, scope: CoroutineScope, file: String): MenuDb {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context,
                        MenuDb::class.java,
                        "menu_database"
                    ).fallbackToDestructiveMigration()
                        .addCallback(
                            DeveloperDatabaseCallback(context, scope, file)
                        )
                        .build()
                    INSTANCE = instance
                    instance
                }
        }

        private class DeveloperDatabaseCallback(
            private val context: Context,
            private val scope: CoroutineScope,
            private val file: String
        ) : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        val jsonObj = JsonParser().parse(
                            readJSONFromAsset(context, file)
                        ).asJsonObject

                        val menuJsonType = object : TypeToken<MenuJson>() {}.type
                        val menuJson: MenuJson = Gson().fromJson(jsonObj, menuJsonType)

                        populateDatabase(
                            database, menuJson
                        )
                    }
                }
            }
        }

        fun populateDatabase(database: MenuDb, menuJson: MenuJson) {
            val menuItemDao = database.menuItemDao()

            // Empty database on first load
            menuItemDao.deleteAll()

            val menuItemList = menuJson.menuData?.menuItems
            menuItemList?.forEach {
                menuItemDao.insert(it)
            }
        }

        private fun readJSONFromAsset(context: Context, file: String): String {
            val json: String
            try {
                val inputStream: InputStream = context.assets.open(file)
                json = inputStream.bufferedReader().use {
                    it.readText()
                }
            } catch (ex: Exception) {
                ex.localizedMessage
                return ""
            }
            return json
        }
    }
}
