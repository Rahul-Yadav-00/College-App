package com.example.college_app.Profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.college_app.LoginAndRegistration.StudentRegister
import com.example.college_app.R
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mLogOut:TextView
    private lateinit var mProfileName:TextView
    private lateinit var mProfileRollNumber: TextView
    private lateinit var mProfileDepartment: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_profile, container, false)
        mProfileName = view.findViewById(R.id.profileName)
        mProfileDepartment = view.findViewById(R.id.profileDepartment)
        mProfileRollNumber = view.findViewById(R.id.profileRollNumber)

        //shared preferences user info
        val userInfo:SharedPreferences = activity!!.getSharedPreferences("User_Information",Context.MODE_PRIVATE)
        mProfileName.text=userInfo.getString("UserName","")
        mProfileRollNumber.text=userInfo.getString("UserRollNumber","")
        when {
            userInfo.getString("UserDepartment","")=="ECE" -> {
                mProfileDepartment.text="Electronic and Communication"
            }
            userInfo.getString("UserDepartment","")=="CS" -> {
                mProfileDepartment.text="Computer Engineering"
            }
            userInfo.getString("UserDepartment","")=="IT" -> {
                mProfileDepartment.text="Information Technology"
            }
            userInfo.getString("UserDepartment","")=="ME" -> {
                mProfileDepartment.text="Mechanical Engineering"
            }
            userInfo.getString("UserDepartment","")=="CE" -> {
                mProfileDepartment.text="Civil Engineering"
            }
            userInfo.getString("UserDepartment","")=="PIE" -> {
                mProfileDepartment.text="Production Engineering"
            }
            else -> {
                mProfileDepartment.text="Electrical Engineering"
            }
        }
        mLogOut = view.findViewById(R.id.logOut)
        mLogOut.setOnClickListener {
            val userInfo: SharedPreferences =this.activity!!.getSharedPreferences("User_Information", Context.MODE_PRIVATE)
            val userEditor=userInfo.edit()
            userEditor.clear()
            userEditor.apply()
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity,StudentRegister::class.java))
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
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}