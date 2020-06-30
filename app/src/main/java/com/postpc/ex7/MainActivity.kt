package com.postpc.ex7

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity**"
    private lateinit var infoText : TextView
    private lateinit var prettybtn : Button
    private lateinit var urlbtn : Button
    private lateinit var image : ImageView
    private var token : String? = null
    private var userName : String? = null
    private var prettyName : String? = null
    private var imageUrl : String? = null
    private var isDone = 0
    private lateinit var spinner : ProgressBar
    private val base_url = "https://hujipostpc2019.pythonanywhere.com"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        infoText = findViewById<TextView>(R.id.usr_info_textView)
        image = findViewById(R.id.image_view)
        prettybtn = findViewById(R.id.new_pretty_name_btn)
        urlbtn = findViewById(R.id.new_image_url_btn)
        spinner = findViewById(R.id.progress_bar)

        val sp : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        if(sp.getString("user", null) == null)
        {
            // Already signed in
            val intent : Intent = Intent(this, FirstTimeUserActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        urlbtn.setOnClickListener{
            val intent : Intent = Intent(this, FieldEdit::class.java)
            intent.putExtra("id", 1)
            startActivity(intent)
        }

        prettybtn.setOnClickListener{
            val intent : Intent = Intent(this, FieldEdit::class.java)
            intent.putExtra("id", 0)
            startActivity(intent)
        }

        val handler : Handler = Handler(Looper.getMainLooper())

        Log.d(TAG, "user name = ${sp.getString("user", null)}")
        Log.d(TAG, "token = ${sp.getString("token", null)}")

        token = sp.getString("token", null)

        val thread = Thread(Runnable {
            val response = ServerHolder.getInstance().myserver.getUsrInfo("token $token").execute()
            userName = response.body()?.data?.username
            prettyName = response.body()?.data?.pretty_name
            imageUrl = response.body()?.data?.image_url
            // Finish thread
            isDone = 1
            handler.post {
                if(prettyName != null && prettyName != "")
                {
                    infoText.text = "Welcome back $prettyName"
                }
                else
                {
                    infoText.text = "Welcome back $userName"
                }
                val media = base_url + imageUrl
                Glide.with(this).load(media).into(image)
                spinner.visibility = View.INVISIBLE
                prettybtn.visibility = View.VISIBLE
                urlbtn.visibility = View.VISIBLE
            }
        })
        thread.start()
    }
}
