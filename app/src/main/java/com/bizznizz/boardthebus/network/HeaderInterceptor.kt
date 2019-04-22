package com.bizznizz.boardthebus.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private vararg val headers: Pair<String, String>) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        headers.forEach { builder.addHeader(it.first, it.second) }
        return chain.proceed(builder.build())
    }

}