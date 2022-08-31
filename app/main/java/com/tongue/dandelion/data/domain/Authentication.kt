package com.tongue.dandelion.data.domain

data class Authentication(
    val jwt: String,
    val username: String,
    val name: String
)
