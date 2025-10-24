package com.example.pokemon.utils

import android.os.Message
import java.sql.Date

sealed class Resource<T>(val date:T? = null,val message: String? = null) {

    class Success<T>(date: T) : Resource<T>(date = date)

    class Error<T>(date: T? =null, message: String) : Resource<T>(date = date, message = message)

    class Loading<T>(date: T? = null, message: String?=null) : Resource<T>(date = date, message = message)
}