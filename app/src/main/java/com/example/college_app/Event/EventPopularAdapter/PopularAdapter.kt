package com.example.college_app.Event.EventPopularAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.college_app.Event.Event
import com.example.college_app.Event.EventAdder.UploadFileModel
import com.example.college_app.MainActivity
import com.example.college_app.R
import kotlinx.android.synthetic.main.event_popular_card.view.*
import kotlinx.android.synthetic.main.schedule_card.view.*

class PopularAdapter(
    private val popularArrayList: ArrayList<UploadFileModel>,
    val context: Event
) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
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
            LayoutInflater.from(parent.context).inflate(R.layout.event_popular_card, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return popularArrayList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(popularArrayList[position])
        holder.itemView.popular_event_images.setImageResource(MainActivity.eventBackgroundImages[position])
    }
}
