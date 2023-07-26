package edu.ucsc.cse118.assignment3.data


import kotlinx.serialization.Serializable

@Serializable
class Message (
    val id: String,
    val poster: String,
    val date: String,
    val content: String,
)