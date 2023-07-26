package edu.ucsc.cse118.assignment3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.ucsc.cse118.assignment3.data.Workspace
import edu.ucsc.cse118.assignment3.R
import edu.ucsc.cse118.assignment3.data.Channel
import edu.ucsc.cse118.assignment3.data.Message
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class MessageAdapter(public val clubs: ArrayList<Message>, private val listener: MessageListener) :
    RecyclerView.Adapter<MessageAdapter.BallClubViewHolder>()
{




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.BallClubViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_master, parent, false)
        return BallClubViewHolder(view)
    }
    override fun onBindViewHolder(holder: BallClubViewHolder, position: Int) {
        holder.bind(clubs[position])
        holder.itemView.setOnClickListener { listener.onClick(clubs[position]) }

    }
    override fun getItemCount(): Int {
        return clubs.size
    }
    fun getMessages(): ArrayList<Message> {
        return clubs
    }
    class BallClubViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        private val name: TextView = itemView.findViewById(R.id.name)
        private val date: TextView = itemView.findViewById(R.id.number)
        private val content : TextView = itemView.findViewById(R.id.body)


        fun bind(club: Message) {
            val tempDate = java.time.LocalDateTime.parse(club.date.subSequence(0,19), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
            val month = tempDate.month.toString().subSequence(0,3).toString().lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            var finalDate = ""
            finalDate += month + " " + tempDate.toString().subSequence(8,10) + ", " +tempDate.toString().subSequence(0,4)
            var tempTime = tempDate.toLocalTime().toString()
            var time = "PM"
            var hr = tempDate.hour
            if (hr >= 12) {
                hr -= 12
            }
            else {
                time = "AM"
            }
            if (hr == 0) {
                hr = 12
            }
            tempTime.replaceRange(0, 2, hr.toString())
            finalDate += ", " + tempTime + " " + time


            name.text = club.poster
            date.text = finalDate
            content.text = club.content
        }
    }
}