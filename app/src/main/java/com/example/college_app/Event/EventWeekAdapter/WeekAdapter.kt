package com.example.college_app.Event.EventWeekAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.college_app.Event.Event
import com.example.college_app.Event.EventAdder.UploadFileModel
import com.example.college_app.MainActivity
import com.example.college_app.R
import kotlinx.android.synthetic.main.event_popular_card.view.*
import kotlinx.android.synthetic.main.event_popular_card.view.club_name
import kotlinx.android.synthetic.main.event_popular_card.view.event_date
import kotlinx.android.synthetic.main.event_popular_card.view.event_location
import kotlinx.android.synthetic.main.event_popular_card.view.event_month
import kotlinx.android.synthetic.main.event_popular_card.view.event_starting
import kotlinx.android.synthetic.main.event_popular_card.view.event_title
import kotlinx.android.synthetic.main.event_week_card.view.*

class WeekAdapter(
    private val weekArrayList: ArrayList<UploadFileModel>,
    val context: Event
) :
    RecyclerView.Adapter<WeekAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(uploadFileModel: UploadFileModel) {
            val date = uploadFileModel.eventDate.split("/")
            itemView.club_name.text = uploadFileModel.clubName
            itemView.event_date.text = date[0]
            itemView.event_month.text = date[1]
            itemView.event_starting.text = uploadFileModel.startingTime
            itemView.event_title.text = uploadFileModel.eventName
            itemView.event_location.text = uploadFileModel.eventLocation

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.event_week_card, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return weekArrayList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(weekArrayList[position])
        holder.itemView.week_event_images.setImageResource(MainActivity.eventBackgroundImages[MainActivity.eventBackgroundImages.size-position-1])
    }
}
