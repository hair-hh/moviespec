package com.impulsed.moviespec.remote

import com.impulsed.moviespec.remote.rest.RestAPI
import javax.inject.Inject

class NetworkManagerImpl @Inject constructor(private val restApi: RestAPI): NetworkManager{
    override fun restAPI(): RestAPI = restApi
}