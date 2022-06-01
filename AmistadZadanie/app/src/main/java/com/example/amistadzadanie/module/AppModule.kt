package com.example.amistadzadanie.module

import com.example.amistadzadanie.RouteAdapter
import com.example.amistadzadanie.data.api.RouteApi
import com.example.amistadzadanie.data.datasource.RouteCacheDataSource
import com.example.amistadzadanie.data.repository.Repository
import com.example.amistadzadanie.viewModel.DetailViewModel
import com.example.amistadzadanie.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://www.traseo.pl")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RouteApi::class.java)
    }
    single {
        Repository(get(), get())
    }
    single {
        RouteCacheDataSource()
    }

    viewModel{
        DetailViewModel(get())
    }

    viewModel{
        MainViewModel(get())
    }
}