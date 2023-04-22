package com.example.repositorypattern.viewmodel_sample.datasource.remote

import com.example.repositorypattern.viewmodel_sample.repository.contextmenu.datasource.IContextMenuDataSource
import com.example.repositorypattern.viewmodel_sample.repository.contextmenu.datasource.local.local_menu_api.ContextMenuRemoteApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ContextMenuRemoteDataSource(
    private val contextMenuRemoteApi: ContextMenuRemoteApi,
    private val ioDispatcher: CoroutineDispatcher
) : IContextMenuDataSource {


    suspend fun fetchContextLocalMenu(): String =
        withContext(ioDispatcher) {
            contextMenuRemoteApi.fetchContextRemoteMenu()
        }
}

