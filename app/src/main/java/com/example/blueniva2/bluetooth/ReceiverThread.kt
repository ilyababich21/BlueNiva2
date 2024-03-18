package com.example.blueniva2.bluetooth

import android.bluetooth.BluetoothSocket
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.blueniva2.R
import com.example.blueniva2.model.FileItem
import com.example.bt_def.bluetooth.BluetoothController
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class ReceiverThread():Thread() {
     var mSocket:BluetoothSocket?=null
//    private val inputStream: InputStream = mSocket.inputStream
//    private val outputStream: OutputStream = mSocket.outputStream
    lateinit var inputStream: InputStream
    lateinit var outputStream: OutputStream
    private var btBuffer = ByteArray(1024)
    val file_list = MutableLiveData<List<FileItem>>()
    var mode:String = "read"
    var currentFile:String = ""


    fun sendData(message: String){
        val runnable = Runnable {
            try{
                outputStream.write(message.toByteArray())
            }catch (e: IOException){

            }
        }
        val thread = Thread(runnable)
        thread.start()
    }

    override fun run() {
        var lenght:Int
        inputStream = mSocket?.inputStream!!
        outputStream = mSocket?.outputStream!!
        while (true){

            when (mode){
                "read" -> {
                    sendData("/test2")
                sleep(1000)
                try
                { lenght = inputStream.read(btBuffer)
                    var string = String(btBuffer,0,lenght?:0).trim()
                    Log.d("MyLog",string)
                    var array = string.split("\n")
                    var list = array.map { FileItem(it,"hello pidr", 113) }
                    Log.d("MyLog", list.toString())
                    file_list.postValue( array.map { FileItem(it,"hello pidr", R.drawable.baseline_upload_file_24) })

//                Log.d("MyLog","Spliter ${array[0]}")

                }
                catch (e:IOException){
                    if (e.message == "socket closed"){
                        break
                    }
                    Log.d("MyLog", "vse huina$e")
                }catch (e:Exception){
                    Log.d("MyLog",e.toString())
                }

            }
                "test" ->
                {
                    sendData("/test1")
                    sleep(200)
                    try{
                        lenght = inputStream.read(btBuffer)
                        Log.d("MyLog", String(btBuffer,0,lenght?:0))
                        mode="read"
                    }catch (e:Exception){
                        Log.d("MyLog",e.toString())
                    }
                }            }






        }

    }

}