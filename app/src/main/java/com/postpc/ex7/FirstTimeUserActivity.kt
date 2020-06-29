package com.postpc.ex7

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FirstTimeUserActivity : AppCompatActivity()
{
    private val TAG : String = "FirstTimeUserActivity**"

    private lateinit var btn : Button
    private lateinit var edit_txt : EditText
    private var usr : String = ""
    private lateinit var token : TokenResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_time_user)

        btn = findViewById<Button>(R.id.new_user_name_btn)
        edit_txt = findViewById<EditText>(R.id.new_user_name_edit_text)

        btn.setOnClickListener(View.OnClickListener {
            if(isStringValid(edit_txt.text.toString()) && edit_txt.text.toString().isNotEmpty())
            {
                usr = edit_txt.text.toString()
                // save to sp
                val thread = Thread(Runnable {
                    // get the token
                    val response = ServerHolder.getInstance().myserver.getUsrToken(usr).execute()
                    Log.d(TAG, "response code = ${response.code()}")
                    Log.d(TAG, "response token data = ${response.body()?.data}")
                    Log.d(TAG, "is successful = ${response.isSuccessful}")
                    val sp : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                    val edit = sp.edit()
                    // check for success
                    edit.putString("token", response.body()?.data)
                    edit.apply()
                    Log.d(TAG, "thread ${Thread.currentThread().id} has finished")
                })
                thread.start()
                val sp : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                val edit = sp.edit()
                edit.putString("user", usr)
                edit.apply()
                Log.d(TAG, "thread ${Thread.currentThread().id} has finished")
                // run next activity
                val intent : Intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            else
            {
                Log.d(TAG, "Invalid user name")
                Toast.makeText(applicationContext,"Invalid user name",Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun isStringValid(str:String): Boolean
    {
        for(c in str)
        {
            if(!c.isLetterOrDigit())
            {
                return false
            }
        }
        return true
    }
}