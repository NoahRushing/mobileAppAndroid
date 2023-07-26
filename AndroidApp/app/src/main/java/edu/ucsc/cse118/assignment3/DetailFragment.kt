package edu.ucsc.cse118.assignment3

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import edu.ucsc.cse118.assignment3.data.Message
import edu.ucsc.cse118.assignment3.databinding.FragmentDetailBinding
import java.time.format.DateTimeFormatter
import java.util.*

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val errorObserver = Observer<ViewModelEvent<String>> { event ->
        var error = event.getUnhandledContent()
        if (error != null) {
            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
        }
    }
    private val clubObserver = Observer<ViewModelEvent<Message>> { event ->
        val club = event.getUnhandledContent()
        if (club != null) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = club.poster
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
            binding.stadium.text = finalDate
            binding.location.text = club.content
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel.error.observe(this, errorObserver)
        sharedViewModel.message.observe(this, clubObserver)
    }
    override fun onDestroy() {
        super.onDestroy()
        sharedViewModel.error.removeObserver(errorObserver)
        sharedViewModel.message.removeObserver(clubObserver)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.getCurrChannel()?.let { sharedViewModel.getMessage(it.id) }
    }
}