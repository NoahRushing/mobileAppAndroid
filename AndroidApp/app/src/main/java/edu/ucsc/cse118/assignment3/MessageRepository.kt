package edu.ucsc.cse118.assignment3

import android.util.Log
import androidx.fragment.app.activityViewModels
import edu.ucsc.cse118.assignment3.data.Channel
import edu.ucsc.cse118.assignment3.data.Member
import edu.ucsc.cse118.assignment3.data.Message
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import edu.ucsc.cse118.assignment3.data.Workspace
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MessageRepository {


    fun getAll(member: Member?, id: String): ArrayList<Message> {
        val path = url + "/" + id
        with(URL(path).openConnection() as HttpsURLConnection) {
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

    fun getOne(member: Member?, club: Message?, id: String): Message {
        val path = url + "/" + id + "/" + (club?.id.toString())

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

    fun addOne(member: Member?, message: Message?, cid: String): Message {
        var new_url = "https://cse118.com/api/v0/message/$cid"
        with(URL(new_url).openConnection() as HttpsURLConnection) {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Authorization", "Bearer ${member?.accessToken}")
            outputStream.write(Json.encodeToString(message).toByteArray())
            if (responseCode == HttpsURLConnection.HTTP_CREATED) {
                return Json.decodeFromString(inputStream.bufferedReader().use { it.readText() })
            }
            if (responseCode == HttpsURLConnection.HTTP_CONFLICT) {
                throw Exception("Club with code ${message?.id} exists!")
            }
            throw Exception("Failed to PUT HTTP $responseCode")
        }
    }

    fun removeOne(member: Member?, message: Message?): Message {
        var new_url = "https://cse118.com/api/v0/message/${message?.id}"
        with(URL(new_url).openConnection() as HttpsURLConnection) {
            requestMethod = "DELETE"
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Authorization", "Bearer ${member?.accessToken}")
            outputStream.write(Json.encodeToString(message).toByteArray())
            if (responseCode == HttpsURLConnection.HTTP_CREATED) {
                return Json.decodeFromString(inputStream.bufferedReader().use { it.readText() })
            }
            if (responseCode == HttpsURLConnection.HTTP_CONFLICT) {
                throw Exception("Club with code ${message?.id} exists!")
            }
            throw Exception("Failed to PUT HTTP $responseCode")
        }
    }

    companion object {
        private const val url = "https://cse118.com/api/v0/channel"
    }
}