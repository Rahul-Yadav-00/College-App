package com.example.college_app.TimeTable.WeekDays.Monday

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.college_app.MainActivity
import com.example.college_app.R
import com.example.college_app.TimeTable.WeekDays.WeekModel
import com.google.protobuf.Empty
import kotlinx.android.synthetic.main.break_card.view.*
import kotlinx.android.synthetic.main.schedule_card.view.*
import kotlinx.android.synthetic.main.timetable_card.view.*
import kotlinx.android.synthetic.main.timetable_card.view.location
import kotlinx.android.synthetic.main.timetable_card.view.periodName
import kotlinx.android.synthetic.main.timetable_card.view.teacherName

private const val TYPE_PERIOD: Int=1
private const val TYPE_BREAK: Int=0
private const val TYPE_EMPTY: Int =2
class MondayAdapter(
    private var mondayList: List<WeekModel>,
    val mondayFragment: MondayFragment
):
        RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    class PeriodViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(weekModel: WeekModel){
            itemView.startingTime1.text = weekModel.opening_time
            itemView.startingTime2.text = weekModel.opening_time
            itemView.closingTime.text = weekModel.closing_time
            itemView.periodName.text = weekModel.period_name
            itemView.teacherName.text = weekModel.teacher_name
            itemView.location.text = weekModel.location
        }
    }
    class BreakViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(weekModel: WeekModel){
            itemView.startingBreak.text=weekModel.opening_time
            itemView.endingBreak.text=weekModel.closing_time

        }
    }
    class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(weekModel: WeekModel){

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_BREAK -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.break_card,parent,false)
                BreakViewHolder(view)
            }
            TYPE_PERIOD -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.timetable_card,parent,false)
                PeriodViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.empty_card,parent,false)
                EmptyViewHolder(view)
            }
        }

    }

    override fun getItemCount(): Int {
        return mondayList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getItemViewType(position: Int): Int {
        return when (mondayList[position].type) {
            0 -> TYPE_BREAK
            1 -> {
                TYPE_PERIOD
            }
            else -> TYPE_EMPTY
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position)== TYPE_BREAK){
            (holder as BreakViewHolder).bind(mondayList[position])
        }else if(getItemViewType(position)== TYPE_PERIOD){
            (holder as PeriodViewHolder).bind(mondayList[position])
            holder.itemView.startingTime2.setTextColor(Color.parseColor(MainActivity.timetableTextColor[position]))
            holder.itemView.closingTime.setTextColor(Color.parseColor(MainActivity.timetableTextColor[position]))
            holder.itemView.periodName.setTextColor(Color.parseColor(MainActivity.timetableTextColor[position]))
            holder.itemView.teacherName.setTextColor(Color.parseColor(MainActivity.timetableTextColor[position]))
            holder.itemView.location.setTextColor(Color.parseColor(MainActivity.timetableTextColor[position]))
            holder.itemView.period_card.setCardBackgroundColor(Color.parseColor(MainActivity.timetableBackground[position]))
            holder.itemView.period_image.setImageResource(MainActivity.scheduleImages[position])
        }else{
            (holder as EmptyViewHolder).bind(mondayList[position])
        }
    }

}