package com.example.repositorypattern.viewmodel_sample.usecase.navmenu

import com.example.repositorypattern.viewmodel_sample.datasource.usecase.IMenuUseCase
import com.example.repositorypattern.viewmodel_sample.repository.IMenuRepository
import javax.inject.Inject

class NavMenuUseCase @Inject internal constructor(
    private val navMenuRepository: IMenuRepository
) : IMenuUseCase {

}
