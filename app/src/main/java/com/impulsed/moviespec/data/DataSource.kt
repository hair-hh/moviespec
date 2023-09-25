package com.impulsed.moviespec.data

import com.impulsed.moviespec.remote.NetworkManager

interface DataSource {
    fun api() : NetworkManager
}