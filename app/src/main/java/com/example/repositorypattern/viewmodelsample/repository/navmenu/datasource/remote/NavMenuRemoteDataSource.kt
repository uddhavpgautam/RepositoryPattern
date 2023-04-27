//package com.example.repositorypattern.viewmodel_sample.repository.navmenu.datasource.remote
//
//import com.example.repositorypattern.viewmodel_sample.repository.navmenu.datasource.remote.remote_menu_api.NavMenuRemoteApi
//import com.example.repositorypattern.viewmodel_sample.repository.navmenu.datasource.INavMenuDataSource
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.withContext
//
//class NavMenuRemoteDataSource(
//    private val navMenuRemoteApi: NavMenuRemoteApi,
//    private val ioDispatcher: CoroutineDispatcher
//) : INavMenuDataSource {
//
//
//    suspend fun fetchNavRemoteMenu(): String =
//        withContext(ioDispatcher) {
//            navMenuRemoteApi.fetchNavRemoteMenu()
//        }
//}
//
//
