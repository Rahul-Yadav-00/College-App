package com.example.college_app

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.college_app.Event.EventAdder.UploadFileModel
import com.example.college_app.LoginAndRegistration.StudentRegister
import com.example.college_app.Schedule.AllSubjects.SubjectModel
import com.example.college_app.TimeTable.WeekDays.WeekModel
import com.example.college_app.Schedule.TodayTimeTable.todayModelAdapter.TodayModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.*
import java.util.*
import kotlin.collections.ArrayList

class LogoDisplay :AppCompatActivity(){
    companion object{
        var scheduleList =ArrayList<TodayModel>()
        var mondayList = ArrayList<WeekModel>()
        var tuesdayList = ArrayList<WeekModel>()
        var wednesdayList = ArrayList<WeekModel>()
        var thursdayList = ArrayList<WeekModel>()
        var fridayList = ArrayList<WeekModel>()
        val months = ArrayList<String>()
        val subjectList = java.util.ArrayList<SubjectModel>()

    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mStore: FirebaseFirestore


    //array of days
    private var days = arrayOf("Monday")

    //timetable data variables
    private var mStartingTime: String = ""
    private var mEndingTime: String = ""
    private var mPeriodName: String = ""
    private var mTeacherName: String = ""
    private var mLocation: String = ""
    private var mFlag :Int =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logo_display)


        //months
        months.add("Jan")
        months.add("Feb")
        months.add("Mar")
        months.add("Apr")
        months.add("May")
        months.add("Jun")
        months.add("Jul")
        months.add("Aug")
        months.add("Sep")
        months.add("Oct")
        months.add("Nov")
        months.add("Dec")


        //checking whether user is login or not
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser == null) {
            val delay= 5000
            val handler = Handler()
            handler.postDelayed(Runnable {
                val intent = Intent(this, StudentRegister::class.java)
                startActivity(intent)
            }, delay.toLong())
        }else{
            //timetable is loading from the cache
            dataLoading()
            subjectDataLoading()

            //displaying the emblem for 5 seconds
            val delay= 5000
            val handler = Handler()
            handler.postDelayed(Runnable {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }, delay.toLong())
        }
    }


    //Fetching the TimeTable and Today's Schedule data for the user
    private fun dataLoading(){
        //day of the week
        val calendar: Calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_WEEK)
        //timetable lists
        mondayList.clear()
        tuesdayList.clear()
        wednesdayList.clear()
        thursdayList.clear()
        fridayList.clear()
        scheduleList.clear()

        mStore = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        val userID = mAuth.currentUser!!.uid
        val departmentDocument: DocumentReference =
            mStore.collection("Users").document(userID)
        departmentDocument.get(Source.CACHE)
            .addOnCompleteListener(OnCompleteListener<DocumentSnapshot> { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null) {
//                        Log.d(ContentValues.TAG, "DocumentSnapshot data: " + task.result!!.data)
                        val department : String = document.getString("Department").toString()
                        val year : String = document.getString("Year").toString()
//                        Log.d(ContentValues.TAG, "Department and Year" + mDepartment + " " + mYear)
                        for (dayName in days) {
                            Log.d(ContentValues.TAG, "Day of the week:" + dayName)
                            val periodCollectionReference: CollectionReference =
                                mStore.collection("Btech").document(department)
                                    .collection("Year").document(year)
                                    .collection("TimeTable")
                                    .document(dayName).collection("Periods")
                            periodCollectionReference.get(Source.CACHE)
                                .addOnSuccessListener { documents ->
                                    for (periodDocument in documents) {
                                        if (periodDocument != null) {
                                            mStartingTime =
                                                periodDocument.getString("Starting_Time")
                                                    .toString()
                                            mEndingTime =
                                                periodDocument.getString("Ending_Time")
                                                    .toString()
                                            mPeriodName =
                                                periodDocument.getString("Period_Name")
                                                    .toString()
                                            mTeacherName =
                                                periodDocument.getString("Teacher_Name")
                                                    .toString()
                                            mLocation =
                                                periodDocument.getString("Location")
                                                    .toString()
                                            val flag =
                                                periodDocument.getString("Type").toString()
                                            mFlag= flag.toInt()
                                            when (dayName) {

                                                "Monday" -> {
                                                    if(mFlag==0 || mFlag==1) {
                                                        mondayList.add(
                                                            WeekModel(
                                                                mStartingTime,
                                                                mEndingTime,
                                                                mPeriodName,
                                                                mTeacherName,
                                                                mLocation,
                                                                mFlag
                                                            )
                                                        )
                                                    }
                                                    if(mFlag==1){
                                                        scheduleList.add(
                                                            TodayModel(
                                                                mStartingTime,
                                                                mEndingTime,
                                                                mPeriodName,
                                                                mLocation,
                                                                mTeacherName
                                                            ))

                                                    }
                                                }
                                                "Tuesday" -> {
                                                    if(mFlag==0 || mFlag==1) {
                                                        tuesdayList.add(
                                                            WeekModel(
                                                                mStartingTime,
                                                                mEndingTime,
                                                                mPeriodName,
                                                                mTeacherName,
                                                                mLocation,
                                                                mFlag
                                                            )
                                                        )
                                                    }
                                                    if(mFlag==1 && day==Calendar.TUESDAY){
                                                        scheduleList.add(
                                                            TodayModel(
                                                                mStartingTime,
                                                                mEndingTime,
                                                                mPeriodName,
                                                                mLocation,
                                                                mTeacherName
                                                            ))

                                                    }
                                                }
                                                "Wednesday" -> {
                                                    if(mFlag==0 || mFlag==1) {
                                                        wednesdayList.add(
                                                            WeekModel(
                                                                mStartingTime,
                                                                mEndingTime,
                                                                mPeriodName,
                                                                mTeacherName,
                                                                mLocation,
                                                                mFlag
                                                            )
                                                        )
                                                    }
                                                    if(mFlag==1 && day==Calendar.WEDNESDAY){
                                                        scheduleList.add(
                                                            TodayModel(
                                                                mStartingTime,
                                                                mEndingTime,
                                                                mPeriodName,
                                                                mLocation,
                                                                mTeacherName
                                                            ))

                                                    }
                                                }
                                                "Thursday" -> {
                                                    if(mFlag==0 || mFlag==1) {
                                                        thursdayList.add(
                                                            WeekModel(
                                                                mStartingTime,
                                                                mEndingTime,
                                                                mPeriodName,
                                                                mTeacherName,
                                                                mLocation,
                                                                mFlag
                                                            )
                                                        )
                                                    }
                                                    if(mFlag==1 && day==Calendar.THURSDAY){
                                                        scheduleList.add(
                                                            TodayModel(
                                                                mStartingTime,
                                                                mEndingTime,
                                                                mPeriodName,
                                                                mLocation,
                                                                mTeacherName
                                                            ))

                                                    }
                                                }
                                                else -> {
                                                    if(mFlag==0 || mFlag==1) {
                                                        fridayList.add(
                                                            WeekModel(
                                                                mStartingTime,
                                                                mEndingTime,
                                                                mPeriodName,
                                                                mTeacherName,
                                                                mLocation,
                                                                mFlag
                                                            )
                                                        )
                                                    }
                                                    if(mFlag==1 && day==Calendar.FRIDAY){
                                                        scheduleList.add(
                                                            TodayModel(
                                                                mStartingTime,
                                                                mEndingTime,
                                                                mPeriodName,
                                                                mLocation,
                                                                mTeacherName
                                                            ))

                                                    }
                                                }
                                            }

                                        } else {
                                            Log.d(ContentValues.TAG, "No such document")
                                        }
                                    }
                                }
                        }
                    } else {
                        Log.d(ContentValues.TAG, "No such document")
                    }
                }else {
                    Log.d(ContentValues.TAG, "No such document")
                }

            })
    }
    private fun subjectDataLoading(){
        val subject:SharedPreferences =getSharedPreferences("User_Subjects",Context.MODE_PRIVATE)
        val allEntries: Map<String,*> = subject.all
        for ((Name, code) in allEntries) {
            subjectList.add(
                SubjectModel(
                    Name,
                    code.toString()
                )
            )

        }
    }
}