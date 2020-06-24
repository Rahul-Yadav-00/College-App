package com.example.college_app.Event.EventAdder

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.college_app.LogoDisplay
import com.example.college_app.MainActivity
import com.example.college_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*


class Adder:AppCompatActivity(){
    private lateinit var mBackBtn: TextView
    private lateinit var mUploadBtn: TextView


    //event details
    private lateinit var mEventTitle: EditText
    private lateinit var mEventClubName: EditText
    private lateinit var mEventDate: EditText
    private lateinit var mEventStartTime: EditText
    private lateinit var mEventLocation: EditText
    private lateinit var mEventDateSelector: ImageView
    private lateinit var mEventBackgroundSelector: ImageView

    //image url
    private var mImageUri: Uri? = null

    //date picker
    private var picker: DatePickerDialog? = null
    private var mDate: Int=0
    private var mMonth: Int =0
    private var mSelectedDate: Int=0;
    private var mSelectedMonth: Int=0;

    //preview
    private lateinit var mPreviewBtn:TextView
    private lateinit var mPreviewCard:CardView
    private lateinit var mPreviewTitle: TextView
    private lateinit var mPreviewClubName: TextView
    private lateinit var mPreviewDate: TextView
    private lateinit var mPreviewMonth: TextView
    private lateinit var mPreviewStartTime: TextView
    private lateinit var mPreviewLocation: TextView
    private lateinit var mPreviewBackground: ImageView

    //fireStore storage
    private lateinit var mStorageRef: StorageReference
    private lateinit var mStore: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_adder)

        //fireStore storage and database
        mStorageRef = FirebaseStorage.getInstance().getReference("Events Uploads");
        mStore = FirebaseFirestore.getInstance()

        //buttons
        mBackBtn = findViewById(R.id.back_to_main)
        mUploadBtn = findViewById(R.id.uploadEvent)

        //event details
        mEventTitle = findViewById(R.id.eventTitle)
        mEventClubName = findViewById(R.id.clubName)
        mEventDate = findViewById(R.id.eventDate)
        mEventDateSelector = findViewById(R.id.dateSelector)
        mEventStartTime = findViewById(R.id.startingTime)
        mEventLocation = findViewById(R.id.eventLocation)
        mEventBackgroundSelector = findViewById(R.id.backgroundSelector)

        //preview items

        mPreviewBtn = findViewById(R.id.preview)
        mPreviewCard = findViewById(R.id.preview_event_card)
        mPreviewTitle = findViewById(R.id.preview_title)
        mPreviewClubName = findViewById(R.id.preview_club_name)
        mPreviewDate = findViewById(R.id.preview_date)
        mPreviewMonth = findViewById(R.id.preview_month)
        mPreviewStartTime = findViewById(R.id.preview_starting)
        mPreviewLocation = findViewById(R.id.preview_location)
        mPreviewBackground = findViewById(R.id.preview_background)
        mPreviewCard.visibility= View.GONE

        //data selector
        mEventDateSelector.setOnClickListener {
            val calender: Calendar = Calendar.getInstance()
            mDate= calender.get(Calendar.DAY_OF_MONTH)
            mMonth= calender.get(Calendar.MONTH)
            val year: Int = calender.get(Calendar.YEAR)
            // date picker dialog
            picker = DatePickerDialog(
                this,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    mEventDate.setText( dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year).toString()
                    mSelectedDate=dayOfMonth
                    mSelectedMonth=monthOfYear
                }, year, mMonth, mDate
            )
            picker!!.show()
        }

        //image selector
        mEventBackgroundSelector.setOnClickListener {
            openFileChooser();
        }
        //change numbers to month

        //preview button
        mPreviewBtn.setOnClickListener {
            validationCheck()
            mPreviewCard.visibility= View.VISIBLE
            mPreviewTitle.text = mEventTitle.text.toString().trim()
            mPreviewClubName.text = mEventClubName.text.toString().trim()
            mPreviewDate.text = mSelectedDate.toString()
            mPreviewMonth.text = LogoDisplay.months[mSelectedMonth]
            mPreviewStartTime.text = mEventStartTime.text.toString().trim()
            mPreviewLocation.text = mEventLocation.text.toString().trim()

        }

        //back to main
        mBackBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }


        //upload button clickListener
        mUploadBtn.setOnClickListener {
            uploadFile();
        }

    }
    private fun openFileChooser(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,1)
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null
        ) {
            mImageUri = data.data
            mPreviewBackground.setImageURI(mImageUri)


        }
    }

    //extension collector
    private fun getFileExtension(uri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    //upload file
    private fun uploadFile(){
        if (mImageUri != null) {
            val fileReference: StorageReference = mStorageRef.child(
                System.currentTimeMillis()
                    .toString() + "." + getFileExtension(mImageUri!!)
            )
            fileReference.putFile(mImageUri!!)
                .addOnSuccessListener {
                    //progress dialog box closed
                    Toast.makeText(this, "Uploaded", Toast.LENGTH_LONG)
                        .show()

                    // download url link
                    fileReference.downloadUrl
                        .addOnSuccessListener { downloadUrl -> //do something with downloadurl
                            val database = FirebaseDatabase.getInstance()
                            val databaseReference = database.getReference("Events")
                            databaseReference.child(UUID.randomUUID().toString()).setValue(
                                UploadFileModel(
                                    mEventTitle.text.toString().trim(),
                                    mEventClubName.text.toString().trim(),
                                    mSelectedDate.toString()
                                        .trim() + "/" + LogoDisplay.months[mSelectedMonth].trim(),
                                    mEventStartTime.text.toString().trim(),
                                    mEventLocation.text.toString().trim(),
                                    downloadUrl.toString()
                                )
                            )
                        }

                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    Toast.makeText(this, "Uploading .... ", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
        }
    }
    private fun validationCheck(){
        if (mEventTitle.text.toString().isEmpty()) {
            mEventTitle.error = "Title is required"
            mEventTitle.requestFocus()
            return
        }
        if (mEventClubName.text.toString().isEmpty()) {
            mEventClubName.error = "Club Name is required"
            mEventClubName.requestFocus()
            return
        }
        if (mEventDate.text.toString().isEmpty()) {
            mEventDate.error = "Date is required"
            mEventDate.requestFocus()
            return
        }
        if(mImageUri.toString().isEmpty()){
            Toast.makeText(this,"No background is selected",Toast.LENGTH_LONG).show()
        }

    }

}