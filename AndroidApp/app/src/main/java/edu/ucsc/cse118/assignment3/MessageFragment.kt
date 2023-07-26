package edu.ucsc.cse118.assignment3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.ucsc.cse118.assignment3.data.Channel
import edu.ucsc.cse118.assignment3.data.Message
import edu.ucsc.cse118.assignment3.databinding.FragmentChannelBinding
import edu.ucsc.cse118.assignment3.databinding.FragmentMasterBinding
import edu.ucsc.cse118.assignment3.databinding.FragmentMessageBinding

class MessageFragment : Fragment(), MessageListener  {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView

    private val errorObserver = Observer<ViewModelEvent<String>> { event ->
        val error = event.getUnhandledContent()
        if (error != null) {
            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
        }
    }
    private val clubsObserver = Observer<ArrayList<Message>> { clubs ->
        recyclerView.adapter = MessageAdapter(clubs, this)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = sharedViewModel.getCurrChannel()?.name
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel.error.observe(this, errorObserver)
        sharedViewModel.messages.observe(this, clubsObserver)
    }
    override fun onDestroy() {
        super.onDestroy()
        sharedViewModel.error.removeObserver(errorObserver)
        sharedViewModel.messages.removeObserver(clubsObserver)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragmentBinding = FragmentMessageBinding.inflate(inflater, container, false)
        fragmentBinding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_messages_to_new_message)
        }
        //btn.setOnClickListener(this)
        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = MessageAdapter(arrayListOf(), this)
        sharedViewModel.getCurrChannel()?.let { sharedViewModel.getMessages(it.id) }
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = sharedViewModel.member.value?.name
        val itemSwipe = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showDialog(viewHolder)
            }
        }
        val swap = ItemTouchHelper(itemSwipe)
        swap.attachToRecyclerView(recyclerView)
    }
    private fun showDialog(viewHolder: RecyclerView.ViewHolder) {
        val id = findNavController().currentDestination?.id
        val builder = AlertDialog.Builder(activity as AppCompatActivity)
        builder.setTitle("Delete Item")
        builder.setMessage("Are you sure you want to delete this message?")
        builder.setPositiveButton("Yes") {dialog, which ->
            val ad = recyclerView.adapter as MessageAdapter
            val deleting = ad.clubs[viewHolder.adapterPosition]
            sharedViewModel.removeMessage(deleting)
            findNavController().popBackStack(id!!,true)
            findNavController().navigate(id)
        }
        builder.setNegativeButton("No") {dialog, which ->
            findNavController().popBackStack(id!!,true)
            findNavController().navigate(id)
        }
        builder.show()


    }
    override fun onClick(club: Message) {
        sharedViewModel.setMessage(club)
        //change later
        findNavController().navigate(R.id.actions_messages_to_single_message)
    }
}