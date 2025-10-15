package com.example.kaswarga_mobile

import android.content.Context
import com.example.kaswarga_mobile.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val BASE_URL = "http://192.168.130.151:8080/api/"
    fun getInstance(context: Context, token: String): ApiService{
        val client = OkHttpClient.Builder()
            .addInterceptor {
                    chain->
                val origin = chain.request()
                val token = SessionManager.getToken(context)
                val newBuilder = origin.newBuilder()
                if (!token.isNullOrEmpty()){
                    newBuilder.addHeader("Authorization", "Bearer $token")
                }
                val request = newBuilder.build()
                chain.proceed(request)
            }
            .build()
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }
}