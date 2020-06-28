package com.postpc.ex7

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
    private val TAG : String = "FirstTimeUserActivity"

    private lateinit var btn : Button
    private lateinit var edit_txt : EditText
    private var usr : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_time_user)

        btn = findViewById<Button>(R.id.new_user_name_btn)
        edit_txt = findViewById<EditText>(R.id.new_user_name_edit_text)

        btn.setOnClickListener(View.OnClickListener {
            if(isStringValid(edit_txt.text.toString()))
            {
                usr = edit_txt.text.toString()
                Log.d(TAG, "User name is $usr")
                // save to sp
                val sp : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                val edit = sp.edit()
                edit.putString("user", usr)
                edit.apply()
                // send Intent to indicate it is first time
                // run next activity
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