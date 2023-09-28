package com.impulsed.moviespec.injection

import com.impulsed.moviespec.data.DataSource
import com.impulsed.moviespec.data.DataSourceImpl
import com.impulsed.moviespec.remote.rest.RestAPI
import com.impulsed.moviespec.remote.rest.RestAPIImpl
import com.impulsed.moviespec.remote.retrofit.RetrofitAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    @Provides
    fun provideRestAPI(retrofitAPI: RetrofitAPI): RestAPI =RestAPIImpl(retrofitAPI = retrofitAPI)

    @Provides
    fun providesDispatcher() = Dispatchers.IO

    @Provides
    fun providesDataSource(restAPI: RestAPI): DataSource = DataSourceImpl(restAPI = restAPI)

}