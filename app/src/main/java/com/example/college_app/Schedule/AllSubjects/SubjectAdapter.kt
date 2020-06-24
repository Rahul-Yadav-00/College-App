package com.example.college_app.Schedule.AllSubjects

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.college_app.R
import kotlinx.android.synthetic.main.subject_card.view.*

class SubjectAdapter(
    private val subjectArrayList: ArrayList<SubjectModel>,
    val context: Subjects
) :
    RecyclerView.Adapter<SubjectAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(subjectModel: SubjectModel) {
            itemView.subjectName.text= subjectModel.subjectName
            itemView.subjectTeacherName.text = subjectModel.teacherName

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.subject_card, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return subjectArrayList.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(subjectArrayList[position])
        val subjectInfo:SharedPreferences = context.activity!!.getSharedPreferences(subjectArrayList[position].subjectName,Context.MODE_PRIVATE)
        val present: Int = subjectInfo.getInt("Present", 0)
        val absent: Int = subjectInfo.getInt("Absent", 0)
        val total:Int =present+ absent
        if (present == 0) {
            holder.itemView.circularProgressbar.progress = 0
            holder.itemView.subject_present.text = 0.toString()
            holder.itemView.subject_total.text = total.toString()
            holder.itemView.progressPercentage.text = "0%"

        } else {
            val attendancePercentage: Double = present / (absent + present).toDouble()
            holder.itemView.circularProgressbar.max = 1000
            holder.itemView.circularProgressbar.secondaryProgress = 1000
            holder.itemView.circularProgressbar.progress = (attendancePercentage*1000).toInt()
            holder.itemView.progressPercentage.text = String.format("%.1f",attendancePercentage*100) + "%"
            holder.itemView.subject_present.text = present.toString()
            holder.itemView.subject_total.text = total.toString()
        }
    }
}
