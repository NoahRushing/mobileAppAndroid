package edu.ucsc.cse118.assignment3.data

import kotlinx.serialization.Serializable

@Serializable
data class Member (
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val accessToken: String,
)

