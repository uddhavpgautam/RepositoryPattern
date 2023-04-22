package com.example.repositorypattern.viewmodel_sample.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.repositorypattern.viewmodel_sample.datasource.usecase.IMenuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*
Don't use AndroidViewModel which can have context.
Context is UI related dependency. All UI related dependencies
instead come from DI
 */
@HiltViewModel
class MenuViewModel @Inject internal constructor(
    private val menuUseCase: IMenuUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {


}
