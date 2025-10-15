package com.example.kaswarga_mobile

data class LoginResponse(
    val message: String,
    val token: String,
    val data: User?,
)

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val role: String,
)