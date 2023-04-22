package com.example.repositorypattern.viewmodel_sample.repository.navmenu.datasource.local

import com.example.repositorypattern.viewmodel_sample.repository.contextmenu.datasource.local.local_menu_api.NavMenuLocalApi
import com.example.repositorypattern.viewmodel_sample.repository.navmenu.datasource.INavMenuDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class NavMenuLocalDataSource(
    private val navMenuLocalApi: NavMenuLocalApi,
    private val ioDispatcher: CoroutineDispatcher
) : INavMenuDataSource {


    suspend fun fetchNavLocalMenu(): String =
        withContext(ioDispatcher) {
            navMenuLocalApi.fetchNavLocalMenu()
        }
}



