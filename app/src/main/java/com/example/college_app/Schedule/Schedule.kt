package com.example.college_app.Schedule

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.ViewPager
import com.example.college_app.LogoDisplay
import com.example.college_app.R
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [todaySchedule.newInstance] factory method to
 * create an instance of this fragment.
 */
class todaySchedule : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mDate : TextView
    private lateinit var mMonth : TextView

    //toggle images
    private lateinit var mTodayTimetable: ImageView
    private lateinit var mSubjects: ImageView
    private lateinit var mScheduleViewPager: ViewPager
    private lateinit var mScheduleAdapter: ScheduleAdapter

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


        val view =inflater.inflate(R.layout.fragment_schedule, container, false)
        mDate = view.findViewById(R.id.Date)
        mMonth = view.findViewById(R.id.Month)
        val calendar: Calendar = Calendar.getInstance()
        val month:Int = calendar.get(Calendar.MONTH);
        val date:Int = calendar.get(Calendar.DATE)
        //Ui data and month display
        mDate.text=date.toString()
        mMonth.text=LogoDisplay.months[month]

        //toggle between the today's timetable and the subjects
        mTodayTimetable = view.findViewById(R.id.todayTimetable)
        mSubjects = view.findViewById(R.id.subjects)
        mScheduleViewPager = view.findViewById(R.id.scheduleViewPager)
        mTodayTimetable.setOnClickListener {
            mScheduleViewPager.currentItem = 0
        }
        mSubjects.setOnClickListener {
            mScheduleViewPager.currentItem = 1
        }
        mScheduleAdapter = ScheduleAdapter(childFragmentManager)
        mScheduleViewPager.adapter = mScheduleAdapter
        mScheduleViewPager.offscreenPageLimit = 2
        mScheduleViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
        mScheduleViewPager.currentItem = 0

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment todaySchedule.
         */

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            todaySchedule().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun changingTabs(position: Int) {
        if (position == 0) {
            mTodayTimetable.setImageResource(R.drawable.today_timetable_selected)
            mSubjects.setImageResource(R.drawable.subject)
        }
        if (position == 1) {
            mTodayTimetable.setImageResource(R.drawable.today_timetable)
            mSubjects.setImageResource(R.drawable.subject_selected)
        }
    }

}