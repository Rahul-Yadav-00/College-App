package com.example.college_app.Schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.college_app.Schedule.AllSubjects.Subjects
import com.example.college_app.Schedule.TodayTimeTable.today_timetable

internal class ScheduleAdapter(fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getItem(position: Int) : Fragment {
        when(position) {
            0 -> {
                return today_timetable()
            }
            1 ->{
                return Subjects()
            }
            else -> return today_timetable()

        }
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}