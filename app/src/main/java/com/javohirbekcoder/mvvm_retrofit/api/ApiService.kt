package com.javohirbekcoder.mvvm_retrofit.api

import com.javohirbekcoder.mvvm_retrofit.model.DeleteUser
import com.javohirbekcoder.mvvm_retrofit.model.User
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


/*
Created by Javohirbek on 11.07.2023 at 15:32
*/

interface ApiService {

    @GET("users")
    @Headers(
        ApiConf.ACCEPT,
        ApiConf.CONTENT_TYPE,
        ApiConf.AUTH_TOKEN
    )
    fun getAllUsers(): Call<List<User>>

    @POST("users")
    @Headers(
        ApiConf.ACCEPT,
        ApiConf.CONTENT_TYPE,
        ApiConf.AUTH_TOKEN
    )
    fun createUser(@Body user: User):Call<User>

    @DELETE("users/{Id}")
    @Headers(
        ApiConf.ACCEPT,
        ApiConf.CONTENT_TYPE,
        ApiConf.AUTH_TOKEN
    )
    fun deleteUser(@Path("Id") Id: String): Call<DeleteUser>

    @PATCH("users/{userId}")
    @Headers(
        ApiConf.ACCEPT,
        ApiConf.CONTENT_TYPE,
        ApiConf.AUTH_TOKEN
    )
    fun updateUser(@Path("userId") userId: String, @Body user: User): Call<User>

}