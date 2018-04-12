package com.jcsoftware.gopigoremote

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import android.R.id.edit
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.View
import android.widget.EditText



class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val ipAddress = findViewById(R.id.etIP) as EditText
        val sendPort = findViewById(R.id.etSendPort) as EditText
        val receivePort = findViewById(R.id.etReceivePort) as EditText
        val updateRate = findViewById(R.id.etUpdateRate) as EditText

        val spref = PreferenceManager.getDefaultSharedPreferences(this)

        ipAddress.setText(spref.getString("controller_ip", ""))
        sendPort.setText(Integer.toString(spref.getInt("controller_send_port", 8111)))
        receivePort.setText(Integer.toString(spref.getInt("controller_receive_port", 8112)))
        updateRate.setText(Integer.toString(spref.getInt("controller_update_rate", 60)))


    }

    fun saveSettings(view: View) {
        val ipAddress = findViewById(R.id.etIP) as EditText
        val sendPort = findViewById(R.id.etSendPort) as EditText
        val receivePort = findViewById(R.id.etReceivePort) as EditText
        val updateRate = findViewById(R.id.etUpdateRate) as EditText

        val ipAddressString: String
        val portInt: Int
        try {
            val spref = PreferenceManager.getDefaultSharedPreferences(this)
            val editor = spref.edit()

            editor.putString("controller_ip", ipAddress.text.toString())
            editor.putInt("controller_send_port", Integer.parseInt(sendPort.text.toString()))
            editor.putInt("controller_receive_port", Integer.parseInt(receivePort.text.toString()))
            editor.putInt("controller_update_rate", Integer.parseInt(updateRate.text.toString()))

            editor.commit()

        } catch (e: Exception) {
            e.printStackTrace()
            val context = applicationContext
            val text = "Error: Inputted settings are invalid!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(context, text, duration)
            toast.show()
            return
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)


    }
}
