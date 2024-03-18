package com.example.blueniva2.bluetooth

import android.bluetooth.BluetoothSocket
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.blueniva2.R
import com.example.blueniva2.model.FileItem
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class ConnectedThread() : Thread() {
    private lateinit var mSocket: BluetoothSocket

    private lateinit var mmOutStream: OutputStream
    private var btBuffer: ByteArray = ByteArray(102400)
    var mode: String = "read"
    lateinit var fos: FileOutputStream
    var currentFile: String = ""
    private var file_list = MutableLiveData(
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


    fun setSocket(mmSocket: BluetoothSocket) {
        mSocket = mmSocket
    }

    fun sendData(message: String) {
        val runnable = Runnable {
            try {
                mmOutStream.write(message.toByteArray())
            } catch (e: IOException) {

            }
        }
        val thread = Thread(runnable)
        thread.start()
    }

    override fun run() {
        if (mSocket == null) return
        val mmInStream: InputStream = mSocket.inputStream
        mmOutStream = mSocket.outputStream
        var lenght: Int
        while (true) {

            when (mode) {
                "read" -> {
                    sendData("Hello\n")
                    Log.d("MyLog", "Otpravka")
                    sleep(1000)
                    lenght = mmInStream.read(btBuffer)
                    Log.d("MyLog", lenght.toString() + String(btBuffer, 0, lenght ?: 0))
                    file_list.postValue(
                        listOf(
                            FileItem(
                                "Pidar",
                                "hello pidr",
                                R.drawable.baseline_upload_file_24
                            )
                        )
                    )
//                    try
//                    { lenght = mmInStream.read(btBuffer)
//                        var string = String(btBuffer,0,lenght?:0).trim()
//                        Log.d("MyLog",string)
//                        var array = string.split("\n")
//                        var list = array.map { FileItem(it,"hello pidr", 113) }
//                        Log.d("MyLog", list.toString())
//                        file_list.postValue( array.map { FileItem(it,"hello pidr", R.drawable.baseline_upload_file_24) })
//
////                Log.d("MyLog","Spliter ${array[0]}")
//
//                    }
//                    catch (e: IOException){
//                        if (e.message == "socket closed"){
//                            break
//                        }
//                        Log.d("MyLog", "vse huina$e")
//                    }catch (e:Exception){
//                        Log.d("MyLog",e.toString())
//                    }

                }

                "test" -> {
                    Log.d("MyLog",Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString())
                    fos = FileOutputStream(
                        File(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                            "sisi2.csv"
                        )
                    )
                    sendData("/Event.csv\n")
                    sleep(1000)
                    Log.d("MyLog", "otpravil")
                    var byte = ByteArray(1024)
                    try {

                        Log.d("MyLog", "It's okey")


//                        Log.d("MyLog", lenght.toString())
//
//                        fos.write(btBuffer, 0, lenght)

                        while (mmInStream.read(byte).also { lenght=it }!= -1) {

                            Log.d("MyLog",lenght.toString())
//                            Log.d("MyLog",String(byte, 0, lenght))
                            fos.write(byte, 0, lenght)
                            sleep(200)
                            if (lenght<192)break

                        }
                        fos.close()
//                        try {
//                            fos = FileOutputStream(
//                                File(
//                                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
//                                    "kusok_govna.txt"
//                                )
//                            )
//
//
//                            while (mmInStream.read(btBuffer) > 0) {
//                                fos.write(btBuffer)
//
////                            btBuffer= ByteArray(102400)
//                            }
//                        } catch (e: Exception) {
//                            Log.d("MyLog", "kakaya to huina:$e")
//                        }
//                        fos.close()
                        Log.d("MyLog", "It's okey")

                        mode = "read"
                        sleep(200)
                    } catch (e: Exception) {
                        Log.d("MyLog", e.toString())
                        fos.close()
                    }
                }
            }


        }
    }


    fun getFileList(): LiveData<List<FileItem>> {
        return file_list
    }

}