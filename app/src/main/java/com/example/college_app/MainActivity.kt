package com.example.college_app

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.college_app.Schedule.AllSubjects.SubjectModel
import com.example.college_app.TimeTable.WeekDays.WeekModel
import com.example.college_app.Schedule.TodayTimeTable.todayModelAdapter.TodayModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import java.util.*


class MainActivity : AppCompatActivity() {
    companion object{

        val scheduleImages = arrayListOf<Int>(
            R.drawable.timetable_background_1,
            R.drawable.timetable_background_2,
            R.drawable.timetable_background_3,
            R.drawable.timetable_background_4,
            R.drawable.timetable_background_1,
            R.drawable.timetable_background_2,
            R.drawable.timetable_background_3,
            R.drawable.timetable_background_4,
            R.drawable.timetable_background_1,
            R.drawable.timetable_background_2
        )
        val scheduleDivider = arrayListOf<String>("#48C2F9", "#FFCDD2", "#FECCD2","#FFCDD2","#48C2F9", "#FFCDD2", "#FECCD2","#FFCDD2","#48C2F9", "#FFCDD2")
        val scheduleBackground = arrayListOf<Int>(
            R.drawable.schedule_background_1,
            R.drawable.schedule_background_2,
            R.drawable.schedule_background_3,
            R.drawable.schedule_background_4,
            R.drawable.schedule_background_1,
            R.drawable.schedule_background_2,
            R.drawable.schedule_background_3,
            R.drawable.schedule_background_4,
            R.drawable.schedule_background_1,
            R.drawable.schedule_background_2
        )
        val timetableBackground = arrayListOf<String>("#bbdefb","#e1bee7","#c8e6c9","#d1c4e9","#bbdefb","#e1bee7","#c8e6c9","#d1c4e9","#bbdefb","#e1bee7")
        val timetableTextColor = arrayListOf<String>("#0755AA","#7809A4","#2A7E2E","#3E0576","#0755AA","#7809A4","#2A7E2E","#3E0576","#0755AA","#7809A4")
        val eventBackgroundImages = arrayListOf<Int>(
            R.drawable.event_background_1,
            R.drawable.event_background_2,
            R.drawable.event_background_3,
            R.drawable.event_background_4,
            R.drawable.event_background_5,
            R.drawable.event_background_1,
            R.drawable.event_background_2,
            R.drawable.event_background_3,
            R.drawable.event_background_4,
            R.drawable.event_background_5
        )
    }
    private lateinit var mViewPage: ViewPager
    private lateinit var mTodaySchedule: ImageButton
    private lateinit var mTimeTable: ImageButton
    private lateinit var mEvent: ImageButton
    private lateinit var mNotification: ImageButton
    private lateinit var mProfile: ImageButton
    private lateinit var mUpdateTimetable:ImageButton

    private lateinit var mPagerAdapter: PagerAdapter

    // ui variables
    private lateinit var userName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //buttons status is saved in shared preferences
        buttonStatus()

        //subjects data loading
        if(LogoDisplay.subjectList.isEmpty())
            subjectDataLoading()

        //userName from shared preferences
        val userInfo:SharedPreferences = getSharedPreferences("User_Information",Context.MODE_PRIVATE)
        userName = findViewById(R.id.userName)
        userName.text = userInfo.getString("UserName","")


        mViewPage = findViewById(R.id.view_pager)
        mTodaySchedule = findViewById(R.id.todaySchedule)
        mTimeTable = findViewById(R.id.timetable)
        mEvent = findViewById(R.id.event)
        mNotification = findViewById(R.id.notification)
        mProfile = findViewById(R.id.profile)
        mUpdateTimetable = findViewById(R.id.update_timetable)

        //update timetable
        mUpdateTimetable.visibility=View.GONE
//        mUpdateTimetable.setOnClickListener{
//            updateTimetable()
//        }
        mTodaySchedule.setOnClickListener{
            mViewPage.currentItem=0
            mUpdateTimetable.visibility=View.GONE
        }
        mTimeTable.setOnClickListener{
            mViewPage.currentItem=1
            mUpdateTimetable.visibility=View.VISIBLE
        }
        mEvent.setOnClickListener{
            mViewPage.currentItem=2
            mUpdateTimetable.visibility=View.GONE
        }
        mNotification.setOnClickListener{
            mViewPage.currentItem=3
            mUpdateTimetable.visibility=View.GONE
        }
        mProfile.setOnClickListener{
            mViewPage.currentItem=4
            mUpdateTimetable.visibility=View.GONE
        }
        mPagerAdapter = EventPageAdapter(supportFragmentManager)
        mViewPage.adapter = mPagerAdapter
        mViewPage.offscreenPageLimit = 5

        mViewPage.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
    }
    private fun changingTabs(position: Int) {
        if (position == 0) {
            mTodaySchedule.setImageResource(R.drawable.schedule_selected)
            mTimeTable.setImageResource(R.drawable.timetable)
            mEvent.setImageResource(R.drawable.event)
            mNotification.setImageResource(R.drawable.notification)
            mProfile.setImageResource(R.drawable.profile)
        }
        if (position == 1) {
            mTodaySchedule.setImageResource(R.drawable.schedule)
            mTimeTable.setImageResource(R.drawable.timetable_selected)
            mEvent.setImageResource(R.drawable.event)
            mNotification.setImageResource(R.drawable.notification)
            mProfile.setImageResource(R.drawable.profile)
        }
        if (position == 2) {
            mTodaySchedule.setImageResource(R.drawable.schedule)
            mTimeTable.setImageResource(R.drawable.timetable)
            mEvent.setImageResource(R.drawable.event_selected)
            mNotification.setImageResource(R.drawable.notification)
            mProfile.setImageResource(R.drawable.profile)
        }
        if (position == 3) {
            mTodaySchedule.setImageResource(R.drawable.schedule)
            mTimeTable.setImageResource(R.drawable.timetable)
            mEvent.setImageResource(R.drawable.event)
            mNotification.setImageResource(R.drawable.notification_selected)
            mProfile.setImageResource(R.drawable.profile)
        }
        if (position == 4) {
            mTodaySchedule.setImageResource(R.drawable.schedule)
            mTimeTable.setImageResource(R.drawable.timetable)
            mEvent.setImageResource(R.drawable.event)
            mNotification.setImageResource(R.drawable.notification)
            mProfile.setImageResource(R.drawable.profile_selected)
        }

    }
    @SuppressLint("CommitPrefEdits")
    private fun buttonStatus(){
        val calendar: Calendar = Calendar.getInstance()
        val date: Int = calendar.get(Calendar.DATE)
        val month: Int = calendar.get(Calendar.MONTH)
        val buttonInfo:SharedPreferences = getSharedPreferences("Status_Button",Context.MODE_PRIVATE)
        val editor = buttonInfo.edit()
        if(!buttonInfo.contains("Date")){
            editor.putString("Date", "$date/$month")
            for(every in LogoDisplay.scheduleList){
                editor.putInt(every.period_name,-1)
            }
        }else{
            if(buttonInfo.getString("Date","")!= "$date/$month"){
                editor.clear().apply()
                editor.putString("Date","$date/$month")
                for(every in LogoDisplay.scheduleList){
                    editor.putInt(every.period_name,-1)
                }
            }
        }
    }
    private fun subjectDataLoading(){
        LogoDisplay.subjectList.clear()
        val subject:SharedPreferences =getSharedPreferences("User_Subjects",Context.MODE_PRIVATE)
        val allEntries: Map<String,*> = subject.all
        for ((Name, TeacherName) in allEntries) {
            LogoDisplay.subjectList.add(
                SubjectModel(
                    Name,
                    TeacherName.toString()
                )
            )

        }
    }

}
