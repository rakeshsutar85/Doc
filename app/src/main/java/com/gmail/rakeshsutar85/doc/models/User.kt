package com.gmail.rakeshsutar85.doc.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id:Int,

    @SerializedName("name")
    val name:String,

    @SerializedName("email")
    val email:String )