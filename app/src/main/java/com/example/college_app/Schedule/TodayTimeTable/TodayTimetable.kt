package com.example.college_app.Schedule.TodayTimeTable

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.college_app.LogoDisplay
import com.example.college_app.R
import com.example.college_app.Schedule.TodayTimeTable.todayModelAdapter.TodayAdapter
import com.example.college_app.Schedule.TodayTimeTable.todayModelAdapter.TodayModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [today_timetable.newInstance] factory method to
 * create an instance of this fragment.
 */
class today_timetable : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var todayList : ArrayList<TodayModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_schedule_today, container, false)
        todayList = LogoDisplay.scheduleList
        arrangingList()
        val todayRecycleView=
            view.findViewById(R.id.todayTimeTableRecycleView) as RecyclerView // Add this
        val cardAdapter = TodayAdapter(todayList, this)
        todayRecycleView.layoutManager = LinearLayoutManager(activity)
        todayRecycleView.adapter = cardAdapter
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment today_timetable.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            today_timetable().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun arrangingList() {
        var j = 0
        while (j <= todayList.size - 1) {
            var index = 0
            var lowestTime =
                LocalTime.parse(todayList[0].opening_time, DateTimeFormatter.ofPattern("H:m"))
            for (i in 0..(todayList.size - 1 - j)) {
                val time = LocalTime.parse(
                    todayList[i].opening_time,
                    DateTimeFormatter.ofPattern("H:m")
                )
                if (lowestTime > time) {
                    lowestTime = time
                    index = i
                }
            }
            j += 1
            todayList.add(todayList[index])
            todayList.removeAt(index)
        }
        val startingTime = LocalTime.parse("8:30", DateTimeFormatter.ofPattern("H:m"))
        for (z in 0..(todayList.size - 1)) {
            val time =
                LocalTime.parse(todayList[0].opening_time, DateTimeFormatter.ofPattern("H:m"))
            if (time < startingTime) {
                todayList.add(todayList[0])
                todayList.removeAt(0)
            }
        }
    }
}