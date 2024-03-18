package com.example.blueniva2.bluetooth

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.blueniva2.R
import com.example.blueniva2.model.FileItem
import com.example.bt_def.BluetoothConstans
import java.io.File
import java.io.FileOutputStream

class BluetoothRepository(application: Application) {

    private var connectThread: ConnectThread? = null

    private lateinit var file_list: LiveData<List<FileItem>>

    private lateinit var isConnect: LiveData<Boolean>

    var instance = application.applicationContext
    val bleManager: BluetoothManager =
        application.applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    val bleAdapter = bleManager.adapter

    val pref = application.applicationContext.getSharedPreferences(
        BluetoothConstans.PREFERENCES,
        Context.MODE_PRIVATE
    )


    init {
        try {
            val mac = pref?.getString(BluetoothConstans.MAC, "")
            if (bleAdapter.isEnabled && (mac ?: "").isNotEmpty()) {
                val device = bleAdapter.getRemoteDevice(mac)
//            var file =  File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"hui.txt")
                connectThread = ConnectThread(device)
                file_list = connectThread!!.connectedTread.getFileList()
                isConnect = connectThread!!.getConnection()
                try {
                    bleAdapter.cancelDiscovery()

                } catch (e: SecurityException) {
                }

                connectThread?.start()

            } else {
                file_list = MutableLiveData(
                    listOf(
                        FileItem(
                            "Pizda", " eto ebaniy pizdec",
                            R.drawable.folder
                        ),
                        FileItem(
                            "Pizda", " eto ebaniy pizdec",
                            R.drawable.folder
                        ),
                        FileItem(
                            "Pizda", " eto ebaniy pizdec",
                            R.drawable.folder
                        )
                    )
                )
            }
            isConnect = MutableLiveData(false)
        } catch (e: Exception) {
            Log.d("Mylog", e.toString())
        }
    }

    fun getFileList(): LiveData<List<FileItem>> {
        return file_list
    }

    fun getConnect(): LiveData<Boolean> {
        return isConnect
    }


    fun setSomeFiles() {
        connectThread?.connectedTread?.mode = "test"
        try {

            Log.d("MyLog", "OKKKKK")

        } catch (e: Exception) {
            Log.d("MyLog", e.toString())
        }
    }

    fun closeConnection() {
            connectThread?.closeConnection()
    }


}
