package com.example.leandropayments.data.networking

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


open class BaseInteractorApiMP<T : Any?>(): Callback<List<T>> {
    companion object {
        const val baseBitBucketApiUrl = "https://api.mercadopago.com/v1/"
        const val key = "TEST-a173ff10-6304-49e0-affd-7d7db6781156"
        const val token = "TEST-1060040302868568-110300-3b1c1e4d930d0755bc3d12225b0fa1a9-292453554"
    }

    val mpApiClient: ApiServicesMP

    init {
// Por si llega a usar un token..........

//        val client = OkHttpClient.Builder().addInterceptor(object : Interceptor {
//            @Throws(IOException::class)
//            override fun intercept(chain: Interceptor.Chain): okhttp3.Response? {
//                val newRequest: Request = chain.request().newBuilder()
//                    .addHeader("Authorization", "Bearer $token")
//                    .build()
//                return chain.proceed(newRequest)
//            }
//        }).build()

        mpApiClient = Retrofit.Builder()
//            .client(client)
            .baseUrl(baseBitBucketApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServicesMP::class.java)
    }

    override fun onResponse(call: Call<List<T>>, response: Response<List<T>>) {
        TODO("Not yet implemented")
    }

    override fun onFailure(call: Call<List<T>>, t: Throwable) {
        TODO("Not yet implemented")
    }
}