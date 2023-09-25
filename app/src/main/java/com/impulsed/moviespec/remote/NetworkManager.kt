package com.impulsed.moviespec.remote

import com.impulsed.moviespec.remote.rest.RestAPI

interface NetworkManager {
    fun restAPI(): RestAPI
}