package edu.ucsc.cse118.assignment3

import android.util.Log
import edu.ucsc.cse118.assignment3.data.Member
import edu.ucsc.cse118.assignment3.data.Workspace
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class WorkspaceRepository {

    fun getAll(member: Member?): ArrayList<Workspace> {
        with(URL(url).openConnection() as HttpsURLConnection) {
            requestMethod = "GET"
            setRequestProperty("Content-Type", "text/html; charset=UTF-8n")
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Authorization", "Bearer ${member?.accessToken}")
            val response = StringBuffer()
            BufferedReader(InputStreamReader(inputStream)).use {
                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()
            }
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                return Json.decodeFromString(response.toString())
            }
            throw Exception("Failed to GET HTTP $responseCode")
        }
    }

    fun getOne(member: Member?, club: Workspace?): Workspace {
        val path = "$url"
        with(URL(path).openConnection() as HttpsURLConnection) {
            requestMethod = "GET"
            setRequestProperty("Content-Type", "text/html; charset=UTF-8n")
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Authorization", "Bearer ${member?.accessToken}")
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                return Json.decodeFromString(inputStream.bufferedReader().use { it.readText() })
            }
            throw Exception("Failed to GET HTTP $responseCode")
        }
    }

    fun addOne(member: Member?, club: Workspace?): Workspace {
        with(URL(url).openConnection() as HttpsURLConnection) {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Authorization", "Bearer ${member?.accessToken}")
            outputStream.write(Json.encodeToString(club).toByteArray())
            if (responseCode == HttpsURLConnection.HTTP_CREATED) {
                return Json.decodeFromString(inputStream.bufferedReader().use { it.readText() })
            }
            if (responseCode == HttpsURLConnection.HTTP_CONFLICT) {
                throw Exception("Club with code ${club?.id} exists!")
            }
            throw Exception("Failed to PUT HTTP $responseCode")
        }
    }

    companion object {
        private const val url = "https://cse118.com/api/v0/workspace"
    }
}