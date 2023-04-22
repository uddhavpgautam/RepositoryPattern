package com.example.repositorypattern.viewmodel_sample.repository.navmenu

import com.example.repositorypattern.viewmodel_sample.repository.IMenuRepository
import com.example.repositorypattern.viewmodel_sample.repository.navmenu.datasource.INavMenuDataSource
import javax.inject.Inject

class NavMenuRepository @Inject internal constructor(
    private val navMenuDataSource: INavMenuDataSource
) : IMenuRepository {

}
