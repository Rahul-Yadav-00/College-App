package com.example.college_app.Event

import android.content.Intent
import android.media.tv.TvContract
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.college_app.Event.EventAdder.Adder
import com.example.college_app.Event.EventAdder.UploadFileModel
import com.example.college_app.Event.EventPopularAdapter.PopularAdapter
import com.example.college_app.Event.EventWeekAdapter.WeekAdapter
import com.example.college_app.LogoDisplay
import com.example.college_app.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Event.newInstance] factory method to
 * create an instance of this fragment.
 */
class Event : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //search and add event
    private lateinit var mAddEvent:ImageView
    private lateinit var mSearchEvent: ImageView

    val popularEventList = ArrayList<UploadFileModel>()
    val weekendEventList = ArrayList<UploadFileModel>()

    //recycleView
    private lateinit var mPopularRecyclerView: RecyclerView
    private lateinit var mWeekRecyclerView: RecyclerView

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
        val view = inflater.inflate(R.layout.fragment_event, container, false)

        //popular event list
        mPopularRecyclerView = view.findViewById(R.id.event_popular_RecycleView)
        mPopularRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        //week event list
        mWeekRecyclerView = view.findViewById(R.id.event_week_RecycleView)

        mWeekRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )


        val calender: Calendar = Calendar.getInstance()
        val date= calender.get(Calendar.DAY_OF_MONTH)
        val month= calender.get(Calendar.MONTH)
        val database = FirebaseDatabase.getInstance()
        val databaseReference=database.getReference("Events")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val uploadModel: UploadFileModel? = postSnapshot.getValue(
                        UploadFileModel::class.java)
                    if (uploadModel != null) {
                        val event = uploadModel.eventDate.split("/")
                        val eventDate = event[0]
                        val eventMonth = event[1]
                        if(eventDate.toInt()==date){
                            popularEventList.add(uploadModel)
                        }else
                            weekendEventList.add(uploadModel)
                    }

                }
                val popularCardAdapter = PopularAdapter(popularEventList, this@Event)
                mPopularRecyclerView.adapter = popularCardAdapter
                val weekCardAdapter = WeekAdapter(weekendEventList, this@Event)
                mWeekRecyclerView.adapter = weekCardAdapter


            }

            override fun onCancelled(databaseError: DatabaseError) {
//                Toast.makeText(this, databaseError.getMessage(), Toast.LENGTH_SHORT)
//                    .show()
            }
        })


        //search and add event
        mAddEvent = view.findViewById(R.id.eventAdd)
        mSearchEvent = view.findViewById(R.id.searchEvent)

        //add event
        mAddEvent.setOnClickListener {
            startActivity(Intent(activity,
                Adder::class.java))
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
         * @return A new instance of fragment Event.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Event().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
