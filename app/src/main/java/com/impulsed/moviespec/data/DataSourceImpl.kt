package com.impulsed.moviespec.data

import com.impulsed.moviespec.remote.NetworkManager
import com.impulsed.moviespec.remote.NetworkManagerImpl
import com.impulsed.moviespec.remote.rest.RestAPI
import javax.inject.Inject

class DataSourceImpl @Inject constructor(private val restAPI: RestAPI): DataSource {
    override fun api(): NetworkManager = NetworkManagerImpl(restApi = restAPI)
}