package com.rhuan.spotifyclone

import java.util.Date

data class User(
    val name:String,
    val username:String,
    val email:String,
    val password:String,
    val birthDate:Long
) {
    constructor():this("","","","",0L)
}
