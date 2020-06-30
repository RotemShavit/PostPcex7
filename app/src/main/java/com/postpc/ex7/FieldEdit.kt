package com.postpc.ex7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.ViewAnimator
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_field_edit.*
import java.util.function.LongFunction

class FieldEdit : AppCompatActivity() {

    lateinit var edit_btn : Button
    lateinit var edit_field : EditText
    private val TAG = "FieldEdit**"
    private lateinit var spinner : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field_edit)

        edit_btn = findViewById(R.id.edit_btn)
        edit_field = findViewById(R.id.edit_Edittext)
        spinner = findViewById(R.id.progress_bar_edit)

        spinner.visibility = View.GONE

        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val intentCreatedMe = intent
        val token = sp.getString("token", null)
        val editThread = Thread( Runnable {
            var didSuccess = true
            if(intentCreatedMe.getIntExtra("id", -1) == 0)
            {
                val new_value = PrettyNameObj(edit_field.text.toString())
                Log.d(TAG, "token $token\nnew_value = $new_value")
                val call = ServerHolder.getInstance().myserver.EditPrettyName("token $token",
                    new_value)
                val response = call.execute()
                didSuccess = response.isSuccessful
                Log.d(TAG, "call headers for edit name ${call.request().headers()}")
                Log.d(TAG, "call URL for edit name ${call.request().url()}")
                Log.d(TAG, "response for edit name $response")
            }
            else
            {
                val new_value = URLObj(edit_field.text.toString())
                val call = ServerHolder.getInstance().myserver.EditImageURL("token $token",
                    new_value)
                val response  = call.execute()
                didSuccess = response.isSuccessful
                Log.d(TAG, "new URL for edit URL $new_value")
                Log.d(TAG, "response for edit URL $response")
            }
            if(didSuccess)
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else
            {
                val builder = AlertDialog.Builder(this)
                builder.setView(R.layout.dialog)
                builder.create()
                builder.show()
            }
        })
        edit_btn.setOnClickListener {
            edit_btn.visibility = View.GONE
            edit_field.visibility = View.GONE
            spinner.visibility = View.VISIBLE
            editThread.start()
        }

    }
}
