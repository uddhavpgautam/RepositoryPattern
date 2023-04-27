//package com.example.repositorypattern.viewmodel_sample.repository.contextmenu.datasource.local
//
//import com.example.repositorypattern.viewmodel_sample.repository.contextmenu.datasource.IContextMenuDataSource
//import com.example.repositorypattern.viewmodel_sample.repository.contextmenu.datasource.local.local_menu_api.ContextMenuLocalApi
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.withContext
//
//
//class ContextMenuLocalDataSource(
//    private val contextMenuLocalApi: ContextMenuLocalApi,
//    private val ioDispatcher: CoroutineDispatcher
//) : IContextMenuDataSource {
//
//
//    suspend fun fetchContextLocalMenu(): String =
//        withContext(ioDispatcher) {
//            contextMenuLocalApi.fetchContextLocalMenu()
//        }
//}
//
//
//
