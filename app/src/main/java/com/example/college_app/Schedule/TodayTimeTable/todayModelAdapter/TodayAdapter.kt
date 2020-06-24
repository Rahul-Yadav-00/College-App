package com.example.college_app.Schedule.TodayTimeTable.todayModelAdapter

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.college_app.MainActivity
import com.example.college_app.R
import com.example.college_app.Schedule.TodayTimeTable.today_timetable
import kotlinx.android.synthetic.main.schedule_card.view.*
import kotlinx.android.synthetic.main.schedule_card.view.absent
import kotlinx.android.synthetic.main.schedule_card.view.cancelled
import kotlinx.android.synthetic.main.schedule_card.view.endTime
import kotlinx.android.synthetic.main.schedule_card.view.location
import kotlinx.android.synthetic.main.schedule_card.view.periodName
import kotlinx.android.synthetic.main.schedule_card.view.present
import kotlinx.android.synthetic.main.schedule_card.view.startTime
import kotlinx.android.synthetic.main.schedule_card.view.teacherName
import kotlin.collections.ArrayList


class TodayAdapter(
    private val arrayList: ArrayList<TodayModel>,
    val context: today_timetable
) :
    RecyclerView.Adapter<TodayAdapter.ViewHolder>() {
    class ViewHolder(itemView: View, context: today_timetable) : RecyclerView.ViewHolder(itemView) {
        private val periodPreferences: SharedPreferences =
            context.activity!!.getSharedPreferences("Status_Button", Context.MODE_PRIVATE)


        fun bindItems(TodayModel: TodayModel) {
            itemView.startTime.text = TodayModel.opening_time
            itemView.endTime.text = TodayModel.closing_time
            itemView.teacherName.text = TodayModel.teacher_name
            itemView.periodName.text = TodayModel.period_name
            itemView.location.text = TodayModel.location
            when (periodPreferences.getInt(TodayModel.period_name, -1)) {
                -1 -> {
                    itemView.present_background.setCardBackgroundColor(Color.WHITE)
                    itemView.present.setImageResource(R.drawable.present)
                    itemView.absent_background.setCardBackgroundColor(Color.WHITE)
                    itemView.absent.setImageResource(R.drawable.absent)
                    itemView.cancelled_background.setCardBackgroundColor(Color.WHITE)
                    itemView.cancelled.setImageResource(R.drawable.cancelled)
                }
                0 -> {
                    itemView.present_background.setCardBackgroundColor(Color.parseColor("#ffa726"))
                    itemView.present.setImageResource(R.drawable.present_white)
                    itemView.absent_background.setCardBackgroundColor(Color.WHITE)
                    itemView.absent.setImageResource(R.drawable.absent)
                    itemView.cancelled_background.setCardBackgroundColor(Color.WHITE)
                    itemView.cancelled.setImageResource(R.drawable.cancelled)
                }
                1 -> {
                    itemView.present_background.setCardBackgroundColor(Color.WHITE)
                    itemView.present.setImageResource(R.drawable.present)
                    itemView.absent_background.setCardBackgroundColor(Color.parseColor("#ffa726"))
                    itemView.absent.setImageResource(R.drawable.absent_white)
                    itemView.cancelled_background.setCardBackgroundColor(Color.WHITE)
                    itemView.cancelled.setImageResource(R.drawable.cancelled)
                }
                2 -> {
                    itemView.present_background.setCardBackgroundColor(Color.WHITE)
                    itemView.present.setImageResource(R.drawable.present)
                    itemView.absent_background.setCardBackgroundColor(Color.WHITE)
                    itemView.absent.setImageResource(R.drawable.absent)
                    itemView.cancelled_background.setCardBackgroundColor(Color.parseColor("#ffa726"))
                    itemView.cancelled.setImageResource(R.drawable.cancelled_white)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.schedule_card, parent, false)
        return ViewHolder(v, context)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])
        holder.itemView.schedule_image.setImageResource(MainActivity.scheduleImages[position])
        holder.itemView.schedule_background.setBackgroundResource(MainActivity.scheduleBackground[position])
        holder.itemView.schedule_divider.setCardBackgroundColor(Color.parseColor(MainActivity.scheduleDivider[position]))
        val buttonStatus: SharedPreferences =
            context.activity!!.getSharedPreferences("Status_Button", Context.MODE_PRIVATE)
        val buttonEditor = buttonStatus.edit()
        val subjectStatus: SharedPreferences = context.activity!!.getSharedPreferences(
            arrayList[position].period_name,
            Context.MODE_PRIVATE
        )
        val subjectEditor = subjectStatus.edit()
        val model = arrayList[position]
        //present card is clicked
        holder.itemView.present_background.setOnClickListener {

            when {
                buttonStatus.getInt(model.period_name, -1) == -1 -> {
                    subjectEditor.putInt("Present", subjectStatus.getInt("Present", 0) + 1)
                    subjectEditor.apply()
                }
                buttonStatus.getInt(model.period_name, -1) == 0 -> {

                }
                buttonStatus.getInt(model.period_name, -1) == 1 -> {
                    subjectEditor.putInt("Present", subjectStatus.getInt("Present", 0) + 1)
                    subjectEditor.putInt("Absent", subjectStatus.getInt("Absent", 0) - 1)
                    subjectEditor.apply()
                }
                else -> {
                    subjectEditor.putInt("Present", subjectStatus.getInt("Present", 0) + 1)
                    subjectEditor.putInt("Cancelled", subjectStatus.getInt("Cancelled", 0) - 1)
                    subjectEditor.apply()
                }
            }
            buttonEditor.putInt(model.period_name, 0)
            buttonEditor.apply()

            //button status is changed here
            holder.itemView.present_background.setCardBackgroundColor(Color.parseColor("#ffa726"))
            holder.itemView.present.setImageResource(R.drawable.present_white)
            holder.itemView.absent_background.setCardBackgroundColor(Color.WHITE)
            holder.itemView.absent.setImageResource(R.drawable.absent)
            holder.itemView.cancelled_background.setCardBackgroundColor(Color.WHITE)
            holder.itemView.cancelled.setImageResource(R.drawable.cancelled)
        }

        //absent card is selected
        holder.itemView.absent_background.setOnClickListener {
            when {
                buttonStatus.getInt(model.period_name, -1) == -1 -> {
                    subjectEditor.putInt("Absent", subjectStatus.getInt("Absent", 0) + 1)
                    subjectEditor.apply()
                }
                buttonStatus.getInt(model.period_name, -1) == 0 -> {
                    subjectEditor.putInt("Present", subjectStatus.getInt("Present", 0) - 1)
                    subjectEditor.putInt("Absent", subjectStatus.getInt("Absent", 0) + 1)
                    subjectEditor.apply()
                }
                buttonStatus.getInt(model.period_name, -1) == 1 -> {

                }
                else -> {
                    subjectEditor.putInt("Absent", subjectStatus.getInt("Absent", 0) + 1)
                    subjectEditor.putInt("Cancelled", subjectStatus.getInt("Cancelled", 0) - 1)
                    subjectEditor.apply()
                }
            }
            buttonEditor.putInt(model.period_name, 1)
            buttonEditor.apply()

            //button status is changed here
            holder.itemView.present_background.setCardBackgroundColor(Color.WHITE)
            holder.itemView.present.setImageResource(R.drawable.present)
            holder.itemView.absent_background.setCardBackgroundColor(Color.parseColor("#ffa726"))
            holder.itemView.absent.setImageResource(R.drawable.absent_white)
            holder.itemView.cancelled_background.setCardBackgroundColor(Color.WHITE)
            holder.itemView.cancelled.setImageResource(R.drawable.cancelled)

        }

        //cancelled card is selected
        holder.itemView.cancelled_background.setOnClickListener {
            when {
                buttonStatus.getInt(model.period_name, -1) == -1 -> {
                    subjectEditor.putInt("Cancelled", subjectStatus.getInt("Cancelled", 0) + 1)
                    subjectEditor.apply()
                }
                buttonStatus.getInt(model.period_name, -1) == 0 -> {
                    subjectEditor.putInt("Present", subjectStatus.getInt("Present", 0) - 1)
                    subjectEditor.putInt("Cancelled", subjectStatus.getInt("Cancelled", 0) + 1)
                    subjectEditor.apply()
                }
                buttonStatus.getInt(model.period_name, -1) == 1 -> {
                    subjectEditor.putInt("Absent", subjectStatus.getInt("Absent", 0) - 1)
                    subjectEditor.putInt("Cancelled", subjectStatus.getInt("Cancelled", 0) + 1)
                    subjectEditor.apply()
                }
                else -> {

                }
            }
            buttonEditor.putInt(model.period_name, 2)
            buttonEditor.apply()

            //button status is changed here
            holder.itemView.present_background.setCardBackgroundColor(Color.WHITE)
            holder.itemView.present.setImageResource(R.drawable.present)
            holder.itemView.absent_background.setCardBackgroundColor(Color.WHITE)
            holder.itemView.absent.setImageResource(R.drawable.absent)
            holder.itemView.cancelled_background.setCardBackgroundColor(Color.parseColor("#ffa726"))
            holder.itemView.cancelled.setImageResource(R.drawable.cancelled_white)
        }

    }
}