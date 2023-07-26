package edu.ucsc.cse118.assignment3

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import edu.ucsc.cse118.assignment3.data.Message
import edu.ucsc.cse118.assignment3.databinding.FragmentDetailBinding
import java.time.format.DateTimeFormatter
import java.util.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import edu.ucsc.cse118.assignment3.databinding.FragmentNewMessageBinding
import java.time.LocalDateTime

class NewMessageFragment: Fragment() {

    private lateinit var binding: FragmentNewMessageBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val errorObserver = Observer<ViewModelEvent<String>> { event ->
        var error = event.getUnhandledContent()
        if (error != null) {
            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.addButton.isEnabled =
                (binding.content.text.length > 15);
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel.error.observe(this, errorObserver)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "New Message"
        //sharedViewModel.message.observe(this, clubObserver)
    }
    override fun onDestroy() {
        super.onDestroy()
        sharedViewModel.error.removeObserver(errorObserver)
        //sharedViewModel.message.removeObserver(clubObserver)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewMessageBinding.inflate(inflater, container, false)
        binding.addButton.isEnabled = false
        binding.content.addTextChangedListener(textWatcher)
        binding.addButton.setOnClickListener {


            val member = sharedViewModel.getMember()?.name
            val mes = member?.let { it1 ->
                Message("2",
                    it1, LocalDateTime.now().toString(), binding.content.text.toString() )
            }
            if (mes != null) {
                sharedViewModel.addMessage(mes)
            }
            getActivity()?.let { it1 ->
                Snackbar.make(
                    it1.findViewById(android.R.id.content),
                    "Message Created", Snackbar.LENGTH_LONG).show()
            };
            findNavController().navigate(R.id.action_new_message_to_messages)
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.getCurrChannel()?.let { sharedViewModel.getMessage(it.id) }
    }




}