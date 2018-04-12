package com.jcsoftware.gopigoremote

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun startRemote(view: View) {
        val intent = Intent(this, RemoteActivity::class.java)
        startActivity(intent)
    }

    fun startSettings(view: View) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun startHelp(view: View) {
        val intent = Intent(this, HelpActivity::class.java)
        startActivity(intent)
    }

    fun startExit(view: View) {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
