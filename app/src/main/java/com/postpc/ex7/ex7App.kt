package com.postpc.ex7

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log

class ex7App : Application()
{
//    override fun onCreate()
//    {
//        super.onCreate()
//        val sp : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
//        if(sp.getString("user", null) != null)
//        {
//            // Already signed in
//            val intent : Intent = Intent(this, FirstTimeUserActivity::class.java)
//        }
//        else
//        {
//            // First time signing in, run sing in activity
//            val intent : Intent = Intent(this, FirstTimeUserActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//        }
//    }
}