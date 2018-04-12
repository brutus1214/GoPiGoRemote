package com.jcsoftware.gopigoremote

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.preference.PreferenceManager
import android.content.SharedPreferences
import android.util.Log

import java.util.*
import kotlin.collections.Map;
import kotlin.concurrent.schedule


class RemoteActivity : AppCompatActivity() {


    //output to UDP and input from UDP respectively
    private val ctrlStateOutgoing = mutableMapOf(  "front" to 0,
                                                "back" to 0,
                                                "left" to 0,
                                                "right" to 0,
                                                "servoright" to 0,
                                                "servoleft" to 0,
                                                "launch" to 0,
                                                "camera" to 0)
    private var sendServer: UDPSendServer? = null
    private var sendThread: Thread? = null
    private var receiveServer: UDPReceiveServer? = null
    private var receiveThread: Thread? = null
    private val btnVal1 = 1
    private val btnVal0 = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote)

        val spref = PreferenceManager.getDefaultSharedPreferences(this)
        val controller_ip = spref.getString("controller_ip", "192.168.1.1")
        val sendPort = spref.getInt("controller_send_port", 8111)
        val recievePort = spref.getInt("controller_receive_port", 8112)
        val updateRate = spref.getInt("controller_update_rate", 60)


        sendServer = UDPSendServer(ctrlStateOutgoing)
        sendServer?.setDestination(controller_ip, sendPort)
        sendServer?.setUpdateRate(updateRate)

        /*
        receiveServer = UDPReceiveServer(object : UDPReceiveServer.RecievedMessageListener {
            override fun onRecieveMessage(message: String) {

            }
        })
        */
        //receiveServer?.setReceivePort(recievePort)

        sendThread = Thread(sendServer)
        //receiveThread = Thread(receiveServer)
        sendThread?.start()

        //receiveThread?.start()
    }


    fun moveFront(view: View) {
        ctrlStateOutgoing.put("front", btnVal1)
        Timer().schedule(1000){
            ctrlStateOutgoing.put("front", btnVal0)
        }

    }

    fun moveLeft(view: View) {
        ctrlStateOutgoing.put("left", btnVal1)
        Timer().schedule(1000){
            ctrlStateOutgoing.put("left", btnVal0)
        }

    }

    fun moveRight(view: View) {
        ctrlStateOutgoing.put("right", btnVal1)
        Timer().schedule(1000){
            ctrlStateOutgoing.put("right", btnVal0)
        }

    }

    fun moveBack(view: View) {
        ctrlStateOutgoing.put("back", btnVal1)
        Timer().schedule(1000){
            ctrlStateOutgoing.put("back", btnVal0)
        }

    }

    fun moveServoLeft(view: View) {
        ctrlStateOutgoing.put("servoleft", btnVal1)
        Timer().schedule(1000){
            ctrlStateOutgoing.put("servoleft", btnVal0)
        }

    }

    fun moveServoRight(view: View) {
        ctrlStateOutgoing.put("servoright", btnVal1)
        Timer().schedule(1000){
            ctrlStateOutgoing.put("servoright", btnVal0)
        }

    }

    fun missileLaunch(view: View) {
        ctrlStateOutgoing.put("launch", btnVal1)
        Timer().schedule(1000){
            ctrlStateOutgoing.put("launch", btnVal0)
        }

    }

    fun startTakePicture(view: View) {
        ctrlStateOutgoing.put("camera", btnVal1)
        Timer().schedule(1000){
            ctrlStateOutgoing.put("camera", btnVal0)
        }

    }

    fun startExit(view: View) {
        onBackPressed()
    }



    public override fun onDestroy() {
        super.onDestroy()
        sendThread?.interrupt()
        receiveThread?.interrupt()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit the controller?")
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id -> finish() })
                .setNegativeButton("No", null)
                .show()
    }

}
