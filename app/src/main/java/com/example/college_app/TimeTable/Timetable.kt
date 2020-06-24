package com.example.college_app.TimeTable

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.viewpager.widget.ViewPager
import com.example.college_app.R
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Timetable.newInstance] factory method to
 * create an instance of this fragment.
 */
class Timetable : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mMonday: ImageButton
    private lateinit var mTuesday: ImageButton
    private lateinit var mWednesday: ImageButton
    private lateinit var mThursday: ImageButton
    private lateinit var mFriday: ImageButton
    private lateinit var mTimetableViewPager: ViewPager
    private lateinit var mTimetableAdapter: TimetablePageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_timetable, container, false)
        mMonday = view.findViewById(R.id.mondayTimetable)
        mTuesday = view.findViewById(R.id.tuesdayTimetable)
        mWednesday = view.findViewById(R.id.wednesdayTimetable)
        mThursday = view.findViewById(R.id.thursdayTimetable)
        mFriday = view.findViewById(R.id.fridayTimetable)
        mTimetableViewPager = view.findViewById(R.id.timetable_view_pager)
        mMonday.setOnClickListener {
            mTimetableViewPager.currentItem = 0

        }

        mTuesday.setOnClickListener {

            mTimetableViewPager.currentItem = 1

        }

        mWednesday.setOnClickListener {
            mTimetableViewPager.currentItem = 2

        }

        mThursday.setOnClickListener {
            mTimetableViewPager.currentItem = 3

        }

        mFriday.setOnClickListener {
            mTimetableViewPager.currentItem = 4

        }
        mTimetableAdapter = TimetablePageAdapter(childFragmentManager)
        mTimetableViewPager.adapter = mTimetableAdapter
        mTimetableViewPager.offscreenPageLimit = 5
        mTimetableViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
//                TODO("Not yet implemented")
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
//                TODO("Not yet implemented")
            }

            override fun onPageSelected(position: Int) {
//                TODO("Not yet implemented")
                changingTabs(position)
            }
        })
        val calendar: Calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_WEEK)
        var fag: Int = 0
//        Log.d(TAG, day.toString())
        when (day) {
            Calendar.SUNDAY -> {
                fag = 0
            }
            Calendar.MONDAY -> {
                fag = 0
            }
            Calendar.TUESDAY -> {
                fag = 1
            }
            Calendar.WEDNESDAY -> {
                fag = 2
            }
            Calendar.THURSDAY -> {
                fag = 3
            }
            Calendar.FRIDAY -> {
                fag = 4
            }
            Calendar.SATURDAY -> {
                fag = 0
            }
        }
        mTimetableViewPager.currentItem = fag
        if (fag == 0) {
            mMonday.setImageResource(R.drawable.monday_icon)
            mTuesday.setImageResource(R.drawable.tuesday_icon_selected)
            mWednesday.setImageResource(R.drawable.wednesday_icon_selected)
            mThursday.setImageResource(R.drawable.thursday_icon_selected)
            mFriday.setImageResource(R.drawable.friday_icon_selected)
        } else if (fag == 1) {
            mMonday.setImageResource(R.drawable.monday_icon_selected)
            mTuesday.setImageResource(R.drawable.tuesday_icon)
            mWednesday.setImageResource(R.drawable.wednesday_icon_selected)
            mThursday.setImageResource(R.drawable.thursday_icon_selected)
            mFriday.setImageResource(R.drawable.friday_icon_selected)
        } else if (fag == 2) {
            mMonday.setImageResource(R.drawable.monday_icon_selected)
            mTuesday.setImageResource(R.drawable.tuesday_icon_selected)
            mWednesday.setImageResource(R.drawable.wednesday_icon)
            mThursday.setImageResource(R.drawable.thursday_icon_selected)
            mFriday.setImageResource(R.drawable.friday_icon_selected)
        } else if (fag == 3) {
            mMonday.setImageResource(R.drawable.monday_icon_selected)
            mTuesday.setImageResource(R.drawable.tuesday_icon_selected)
            mWednesday.setImageResource(R.drawable.wednesday_icon_selected)
            mThursday.setImageResource(R.drawable.thursday_icon)
            mFriday.setImageResource(R.drawable.friday_icon_selected)
        } else {
            mMonday.setImageResource(R.drawable.monday_icon_selected)
            mTuesday.setImageResource(R.drawable.tuesday_icon_selected)
            mWednesday.setImageResource(R.drawable.wednesday_icon_selected)
            mThursday.setImageResource(R.drawable.thursday_icon_selected)
            mFriday.setImageResource(R.drawable.friday_icon)
        }
        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Timetable.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Timetable().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun changingTabs(position: Int) {
        if (position == 0) {
            mMonday.setImageResource(R.drawable.monday_icon)
            mTuesday.setImageResource(R.drawable.tuesday_icon_selected)
            mWednesday.setImageResource(R.drawable.wednesday_icon_selected)
            mThursday.setImageResource(R.drawable.thursday_icon_selected)
            mFriday.setImageResource(R.drawable.friday_icon_selected)
        }
        if (position == 1) {
            mMonday.setImageResource(R.drawable.monday_icon_selected)
            mTuesday.setImageResource(R.drawable.tuesday_icon)
            mWednesday.setImageResource(R.drawable.wednesday_icon_selected)
            mThursday.setImageResource(R.drawable.thursday_icon_selected)
            mFriday.setImageResource(R.drawable.friday_icon_selected)
        }
        if (position == 2) {
            mMonday.setImageResource(R.drawable.monday_icon_selected)
            mTuesday.setImageResource(R.drawable.tuesday_icon_selected)
            mWednesday.setImageResource(R.drawable.wednesday_icon)
            mThursday.setImageResource(R.drawable.thursday_icon_selected)
            mFriday.setImageResource(R.drawable.friday_icon_selected)
        }
        if (position == 3) {
            mMonday.setImageResource(R.drawable.monday_icon_selected)
            mTuesday.setImageResource(R.drawable.tuesday_icon_selected)
            mWednesday.setImageResource(R.drawable.wednesday_icon_selected)
            mThursday.setImageResource(R.drawable.thursday_icon)
            mFriday.setImageResource(R.drawable.friday_icon_selected)
        }
        if (position == 4) {
            mMonday.setImageResource(R.drawable.monday_icon_selected)
            mTuesday.setImageResource(R.drawable.tuesday_icon_selected)
            mWednesday.setImageResource(R.drawable.wednesday_icon_selected)
            mThursday.setImageResource(R.drawable.thursday_icon_selected)
            mFriday.setImageResource(R.drawable.friday_icon)
        }

    }
}