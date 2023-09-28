package com.impulsed.moviespec.data.mapper

import com.impulsed.moviespec.domain.entity.base.ErrorRecord
import com.impulsed.moviespec.domain.entity.base.Record
import com.impulsed.moviespec.remote.RemoteException




    fun <T>RemoteException.mapErrorRecord(): Record<T> {
        val errorRecord: ErrorRecord = when(this) {
            is RemoteException.ClientError -> ErrorRecord.ClientError
            is RemoteException.ServerError -> ErrorRecord.ServerError
            is RemoteException.NoNetworkError -> ErrorRecord.NetworkError
            else -> ErrorRecord.GenericError
        }
        return Record(null, errorRecord)
    }
