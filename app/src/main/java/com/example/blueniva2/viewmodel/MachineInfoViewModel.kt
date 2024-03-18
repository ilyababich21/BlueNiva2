package com.example.blueniva2.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blueniva2.bluetooth.BluetoothRepository
import com.example.blueniva2.model.FileItem

class MachineInfoViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var bluetoothRepository: BluetoothRepository
    private lateinit var file_list: LiveData<List<FileItem>>
    private lateinit var isConnect: LiveData<Boolean>

    init {
        try {
            bluetoothRepository = BluetoothRepository(application)
            file_list = bluetoothRepository.getFileList()
            isConnect = bluetoothRepository.getConnect()
        } catch (e: Exception) {
            Log.d("MyLog", e.toString())
        }
    }


    fun getFileList(): LiveData<List<FileItem>> {
        return file_list
    }

    fun getConnect(): LiveData<Boolean> {
        return isConnect
    }

    fun setSomeFiles() {

        bluetoothRepository.setSomeFiles()
    }

    fun closeConnection(){
        bluetoothRepository.closeConnection()
    }

}