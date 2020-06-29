package com.postpc.ex7

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity**"
    private lateinit var infoText : TextView
    var token : String? = null
    var userName : String? = null
    var isDone = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        infoText = findViewById<TextView>(R.id.usr_info_textView)

        val sp : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        Log.d(TAG, "user name = ${sp.getString("user", null)}")

        token = sp.getString("token", null)

        val thread = Thread(Runnable {
            val response = ServerHolder.getInstance().myserver.getUsrInfo("token $token").execute()
            infoText.setText("Welcome back ${response.body()?.data?.username}")
            // Finish thread
        })
        thread.start()
        // Show loading UI
    }
}
