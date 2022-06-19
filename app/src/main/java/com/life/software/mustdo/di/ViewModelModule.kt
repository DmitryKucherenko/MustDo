package com.life.software.mustdo.di

import androidx.lifecycle.ViewModel
import com.life.software.mustdo.presentation.TasksListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TasksListViewModel::class)
    fun bindShopItemViewModel(viewModel: TasksListViewModel): ViewModel
}
