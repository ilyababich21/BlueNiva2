package com.example.blueniva2.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.blueniva2.model.FileItem
import java.io.File
import java.io.IOException
import java.util.UUID


class ConnectThread(device: BluetoothDevice) : Thread() {
    private val uuid = "00001101-0000-1000-8000-00805F9B34FB"
     var connectedTread:ConnectedThread = ConnectedThread()
    var isConnect: MutableLiveData<Boolean> = MutableLiveData(false)

    private val mSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
        try {
            device.createRfcommSocketToServiceRecord(UUID.fromString(uuid))

        }catch (e:SecurityException){
            return@lazy null
        }
    }


    override fun run() {
       try{
           mSocket?.connect()

           Log.d("MyLog","good")
           mSocket?.let { connectedTread.setSocket(it) }
           if(mSocket!=null)connectedTread.start()
           isConnect.postValue(true)

       }catch (e:IOException){}
        catch (e:SecurityException){}

    }


    fun closeConnection(){
        mSocket?.close()
    }

    fun getConnection():LiveData<Boolean>{
        return isConnect
    }

}

