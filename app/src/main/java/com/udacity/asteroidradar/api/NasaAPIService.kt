package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = Constants.BASE_URL

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

interface NasaAPIService{
    @GET("neo/rest/v1/feed")
    fun getAsteroidData(
            @Query("start_date") startDate: String,
            @Query("end_date") endDate: String,
            @Query("api_key") apiKey: String = Constants.API_KEY

    ) : String
}

object APIService{
    val retrofitService : NasaAPIService by lazy {
        retrofit.create(NasaAPIService::class.java)
    }
}
