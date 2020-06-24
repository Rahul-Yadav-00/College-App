package com.example.college_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.college_app.Schedule.AllSubjects.SubjectModel

class Goal: AppCompatActivity() {
    private lateinit var mGoal: EditText
    private lateinit var mStart: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.attendance_goal)
        mGoal = findViewById(R.id.Goal_of_attendance)
        mStart = findViewById(R.id.start)

        //user information is updated with the goal for the attendance
        val userInfo: SharedPreferences = getSharedPreferences("User_Information", Context.MODE_PRIVATE)
        val userEditor=userInfo.edit()

        //goal data is saved in the user information shared preferences
        mStart.setOnClickListener {
            val goal: String = mGoal.text.toString()
            userEditor.putString("Goal",goal)
            userEditor.apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //subjects addition
        subjectDataLoading()
    }
    private fun subjectDataLoading(){
        LogoDisplay.subjectList.clear()
        val subject:SharedPreferences =getSharedPreferences("User_Subjects",Context.MODE_PRIVATE)
        val allEntries: Map<String,*> = subject.all
        for ((Name, code) in allEntries) {
            LogoDisplay.subjectList.add(
                SubjectModel(
                    Name,
                    code.toString()
                )
            )

        }
    }
}