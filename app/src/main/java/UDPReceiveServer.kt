package com.jcsoftware.gopigoremote

import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket

class UDPReceiveServer(internal var messageListener: RecievedMessageListener) : Runnable {

    private var receivePort: Int = 0

    fun setReceivePort(receivePort: Int) {
        this.receivePort = receivePort
    }

    override fun run() {
        val receiveSocket: DatagramSocket
        //Log.d("CommandServer","Starting UDP receive server with port: " +Integer.toString(receivePort));
        try {
            receiveSocket = DatagramSocket(receivePort)
            receiveSocket.soTimeout = SOCKET_TIMEOUT
            while (!Thread.currentThread().isInterrupted) {
                try {
                    val message = receiveCommandPacket(receiveSocket)
                    messageListener.onRecieveMessage(message)
                } catch (e: IOException) {

                }

                try {
                    Thread.sleep(1)
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                }

            }
            //Log.d("CommandServer","Shutting down receive server...");
            receiveSocket.close()
        } catch (e: Exception) {
            //Log.d("CommandServer","Error binding receive socket");
            e.printStackTrace()
        }

    }

    @Throws(IOException::class)
    private fun receiveCommandPacket(socket: DatagramSocket): String {
        val receiveData = ByteArray(1024)
        val receivePacket = DatagramPacket(receiveData, receiveData.size)
        socket.receive(receivePacket)
        //Log.d("CommandServer","Received Packet: "+ receivePacket.getAddress().getHostName() + ": " + message);
        return String(receiveData, 0, receivePacket.length)
    }

    interface RecievedMessageListener {
        fun onRecieveMessage(message: String)
    }

    companion object {
        internal var SOCKET_TIMEOUT = 1000
    }


}