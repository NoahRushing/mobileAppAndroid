package edu.ucsc.cse118.assignment3

import edu.ucsc.cse118.assignment3.data.Message


interface MessageListener {
    fun onClick(club: Message)
}