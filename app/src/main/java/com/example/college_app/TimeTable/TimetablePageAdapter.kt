package com.example.college_app.TimeTable

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.college_app.TimeTable.WeekDays.Friday.FridayFragment
import com.example.college_app.TimeTable.WeekDays.Monday.MondayFragment
import com.example.college_app.TimeTable.WeekDays.Thursday.ThursdayFragment
import com.example.college_app.TimeTable.WeekDays.Tuesday.TuesdayFragment
import com.example.college_app.TimeTable.WeekDays.Wednesday.WednesdayFragment


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
internal class TimetablePageAdapter(fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getItem(position: Int) : Fragment {
        when(position) {
            0 -> {
                return MondayFragment()
            }
            1 ->{
                return TuesdayFragment()
            }
            2 -> {
                return WednesdayFragment()
            }
            3 -> {
                return ThursdayFragment()
            }
            4 -> {
                return FridayFragment()
            }
            else -> return MondayFragment()

        }
    }

    override fun getCount(): Int {
        // Show 5 total pages.
        return 5
    }
}