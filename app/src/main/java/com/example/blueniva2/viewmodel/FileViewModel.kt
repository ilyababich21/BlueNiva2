package com.example.blueniva2.viewmodel

import android.bluetooth.BluetoothAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blueniva2.bluetooth.BleRepo
import com.example.blueniva2.model.FileItem

class FileViewModel:ViewModel() {
    private lateinit var bluetoothController: BleRepo

     val file_list: MutableLiveData<List<FileItem>>?
        get() = bluetoothController.connectThread?.recivierThread?.file_list

//    init {
//        file_list =  bluetoothController.connectThread?.recivierThread?.file_list
//    }


    var isConnect: MutableLiveData<Boolean>? = MutableLiveData()
        get() = bluetoothController.connectThread?.isConnect

    fun createBtControl(bluetoothAdapter: BluetoothAdapter){
        bluetoothController= BleRepo(bluetoothAdapter)
    }

    fun connect(mac:String){
        bluetoothController.connect(mac)
//        file_list = bluetoothController.connectThread?.recivierThread?.file_list!!
    }

    fun disconnect(){
        bluetoothController.closeConnection()
    }

    fun getFiles(): MutableLiveData<List<FileItem>>? {
        return file_list
    }


    fun downloadFile(name: String){
        bluetoothController.connectThread?.recivierThread?.currentFile = name
        bluetoothController.connectThread?.recivierThread?.mode   = "test"
    }


    fun sendData(message: String = "/test1"){

        bluetoothController.sendData(message)
    }

//    fun getFileList(): MutableLiveData<List<FileItem>>? {
//        return file_list
//    }

//    fun setFileList(files:List<FileItem>){
//        file_list?.value = files
//    }


//    fun sendMessage(message: String){
//        bluetoothController.sendMessage(message)
//    }

    fun addFile(fileItem: FileItem){
        if (file_list?.value ==null){
            file_list?.value = listOf(fileItem)
        }
        else{
            file_list?.value = file_list?.value!!.plus(fileItem)
        }

    }

}