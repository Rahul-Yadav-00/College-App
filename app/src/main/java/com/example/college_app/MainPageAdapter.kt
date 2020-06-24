package com.example.college_app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.college_app.Event.Event
import com.example.college_app.Notification.Notification
import com.example.college_app.Profile.Profile
import com.example.college_app.TimeTable.Timetable
import com.example.college_app.Schedule.todaySchedule

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
internal class EventPageAdapter(fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getItem(position: Int) : Fragment {
        when(position) {
            0 -> {
                return todaySchedule()
            }
            1 ->{
                return Timetable()
            }
            2 -> {
                return Event()
            }
            3 -> {
                return Notification()
            }
            4 -> {
                return Profile()
            }
            else -> return todaySchedule()

        }
    }

    override fun getCount(): Int {
        // Show 5 total pages.
        return 5
    }
}