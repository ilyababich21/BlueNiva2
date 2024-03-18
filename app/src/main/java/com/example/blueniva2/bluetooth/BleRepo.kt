package com.example.blueniva2.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import androidx.lifecycle.MutableLiveData
import java.io.IOException
import java.util.UUID


class BleRepo(private val adapter: BluetoothAdapter) {
    var connectThread: ConnectThread? = null

    class ConnectThread(device: BluetoothDevice):Thread() {
        private val uuid = "00001101-0000-1000-8000-00805F9B34FB"
        private  var mSocket: BluetoothSocket? = null
        var recivierThread = ReceiverThread()
        var isConnect: MutableLiveData<Boolean> = MutableLiveData(false)
        init {
            try {
                mSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid))
            } catch (e: IOException) {

            } catch (e: SecurityException) {

            }
        }

        override fun run() {
            try{
                mSocket?.connect()
                isConnect.postValue(true)
//                recivierThread = mSocket?.let { ReceiverThread(it) }!!
                recivierThread.mSocket=mSocket
                recivierThread.start()
            }catch (e: IOException){
                isConnect.postValue(false)
                closeConnection()


            }catch (e: SecurityException) {

            }

        }
        fun closeConnection() {
            try {
                mSocket?.close()
            isConnect.postValue(false)
            } catch (e: IOException) {

            }
        }

    }

    fun sendData(message:String){
        connectThread?.recivierThread?.sendData(message)
    }

    fun connect(mac: String) {
        if (adapter.isEnabled && mac.isNotEmpty()) {
            val device = adapter.getRemoteDevice(mac)
            connectThread = ConnectThread(device)
            connectThread?.start()


        }
    }

    fun closeConnection(){
        connectThread?.closeConnection()
    }

}