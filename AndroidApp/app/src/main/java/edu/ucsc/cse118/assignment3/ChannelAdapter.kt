package edu.ucsc.cse118.assignment3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.ucsc.cse118.assignment3.data.Workspace
import edu.ucsc.cse118.assignment3.R
import edu.ucsc.cse118.assignment3.data.Channel


class ChannelAdapter(private val clubs: ArrayList<Channel>, private val listener: ChannelListener) :
    RecyclerView.Adapter<ChannelAdapter.BallClubViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelAdapter.BallClubViewHolder {
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
    class BallClubViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        private val name: TextView = itemView.findViewById(R.id.name)
        private val numMessages : TextView = itemView.findViewById(R.id.number)
        fun bind(club: Channel) {
            name.text = club.name
            //stadium.text = club.stadium.name
            numMessages.text = club.messages.toString() + " Messages"
        }
    }
}