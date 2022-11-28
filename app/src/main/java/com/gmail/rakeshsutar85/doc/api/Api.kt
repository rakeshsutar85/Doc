package com.gmail.rakeshsutar85.doc.api

import com.gmail.rakeshsutar85.doc.models.DefaultResponse
import com.gmail.rakeshsutar85.doc.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("register.php")
    fun createUser(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("mobile") mobile:String,
        @Field("password") password:String
    ):Call<DefaultResponse>


    @FormUrlEncoded
    @POST("login.php")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password: String
    ): Call<LoginResponse>
}