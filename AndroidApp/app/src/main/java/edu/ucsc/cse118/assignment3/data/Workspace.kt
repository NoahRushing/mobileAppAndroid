
package edu.ucsc.cse118.assignment3.data

import kotlinx.serialization.Serializable

@Serializable
data class Workspace (
    val name: String,
    val id: String,
    val channels: Int
)

