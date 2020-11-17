package com.example.leandropayments.data.networking

interface ResponseListener<T> {
    fun onResponse(response: List<T>)
    fun onError(t: Throwable?)
}