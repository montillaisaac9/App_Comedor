package com.example.app_comedor.utils

sealed class ApiResult<T>(var data:T?=null, var error:String?=null){
    class Success<T>(quotes: T):ApiResult<T>(data = quotes)
    class Error<T>(error: String):ApiResult<T>(error = error)
    class Loading<T>:ApiResult<T>()
}