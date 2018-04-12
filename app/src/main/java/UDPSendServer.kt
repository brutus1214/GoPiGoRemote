package com.jcsoftware.gopigoremote

import android.util.Log
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.UnknownHostException


/**
 * Created by James Chang
 */
class UDPSendServer constructor(private val ctrl_state_outgoing: Map<String, Int>) : Runnable {
    private var destinationIP: String? = null
    private var destinationPort: Int = 0
    private var updateRate: Int = 0

    fun setDestination(ip: String, port: Int) {
        destinationIP = ip
        destinationPort = port
    }

    fun setUpdateRate(rate: Int) {
        updateRate = rate
    }

    override fun run() {
        val sendSocket: DatagramSocket
        val running = true

        Log.d("UDPSendServer","Starting UDP send server with IP destination: " + destinationIP +
                " and port: " + Integer.toString(destinationPort))
        try {
            sendSocket = DatagramSocket()
            while (!Thread.currentThread().isInterrupted) {
                try {
                    val stringOut = ctrl_state_outgoing.toString().replace("=", ":")
                    sendCommandPacket(sendSocket, stringOut)
                } catch (e: UnknownHostException) {
                    //Log.d("CommandServer","Invalid destination IP");
                    //e.printStackTrace();
                } catch (e: IOException) {
                    //Log.d("CommandServer","Error sending packet");
                    //e.printStackTrace();
                }

                try {
                    Thread.sleep((1000 / updateRate).toLong())
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                }

            }
            //Log.d("CommandServer","Shutting down UDP send server...");
            sendSocket.close()
        } catch (e: Exception) {
            Log.d("CommandServer","Error binding send socket");
            e.printStackTrace();
        }

    }

    @Throws(IOException::class)
    private fun sendCommandPacket(socket: DatagramSocket, message: String) {
        val sendData = message.toByteArray()
        val sendPacket = DatagramPacket(sendData, sendData.size, InetAddress.getByName(destinationIP), destinationPort)
        socket.send(sendPacket)
        Log.d("CommandThread","Sent message: " + sendData);
    }


}