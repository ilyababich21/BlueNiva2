package com.example.blueniva2.view

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blueniva2.viewmodel.FileAdapter
import com.example.blueniva2.model.FileItem
import com.example.blueniva2.R
import com.example.blueniva2.databinding.FragmentMainBinding
import com.example.bt_def.BluetoothConstans
import com.example.bt_def.bluetooth.BluetoothController


class MainFragment : Fragment() ,BluetoothController.Listener, FileAdapter.Listener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var bluetoothController: BluetoothController
    private lateinit var bAdapter: BluetoothAdapter
    private var adapter = FileAdapter(this@MainFragment)
    private var connection:Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun initBtAdapter() {
        val bManager = activity?.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bAdapter = bManager.adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBtAdapter()
        val pref = activity?.getSharedPreferences(
            BluetoothConstans.PREFERENCES,
            Context.MODE_PRIVATE)
        val mac = pref?.getString(BluetoothConstans.MAC,"")
        binding.tvStatus.text = if (mac == "") "Pizda" else mac
        bluetoothController= BluetoothController(bAdapter)

        binding.bConnect.setOnClickListener{
            if(connection)bluetoothController.closeConnection()
            else bluetoothController.connect(mac ?:"",this)
//            bluetoothController.sendMessage("hui")
        }
        binding.rcViewFiles.layoutManager = LinearLayoutManager(requireContext())
        binding.rcViewFiles.adapter =adapter

        binding.btSend.setOnClickListener {
            bluetoothController.sendMessage("/test1")
        }
        binding.btItems.setOnClickListener {
            bluetoothController.sendMessage("/test2")
        }
        binding.btInfo.setOnClickListener {
            bluetoothController.sendMessage("/test3")
        }



//        addFiles("PIZDA")
//        addFiles("PIZDA")
//        addFiles("PIZDA")
//        addFiles("PIZDA")

    }


//    private fun addFiles(message: String){
////        for (i in 0 until 20) {
////                        adapter.addFile(FileItem("hui$message"," eto ebaniy pizdec",R.drawable.folder))
////            }
//        adapter.addFile(
//            FileItem("$message"," eto ebaniy pizdec",
//                R.drawable.baseline_upload_file_24
//            )
//        )
//
//    }


//    private fun clearFiles(){
//        adapter.clearFile()
//    }

    override fun onReceiver(message: String) {
        activity?.runOnUiThread{
            when(message){
                BluetoothController.BLUETOOTH_CONNECTED->{
                    binding.bConnect.backgroundTintList = AppCompatResources
                        .getColorStateList(requireContext(), R.color.red)
                    binding.bConnect.text = "Disconnect"
                    binding.btSend.visibility = View.VISIBLE
                    connection = true
                }
                BluetoothController.BLUETOOTH_NO_CONNECTED->{
                    binding.bConnect.backgroundTintList = AppCompatResources
                        .getColorStateList(requireContext(), R.color.green)
                    bluetoothController.closeConnection()
                    binding.bConnect.text = "hui"
                    binding.btSend.visibility = View.INVISIBLE
                    connection = false
                }
                else -> {
                    Log.d("MyLog", message[0].toString())
                    if (message[0]=='2'){
//                        clearFiles()
//                        Log.d("MyLog",message)
                        var crinzh = message.substring(1,message.length-1)
                        crinzh.trim()
//                        Log.d("MyLog",message.last())

                        var words = crinzh.split("\n")
                        binding.tvStatus.text = words[0]

                        for (m in 1..words.size-1){

//                            addFiles(words[m])

                        }
//                        for(word in words){
//                            addFiles(word)
//                        Log.d("MyLog",word)
//
//                        }
                    }

//                    Toast.makeText(context,message,Toast.LENGTH_LONG).show()
//                    addFiles(message)
                }
            }
        }
    }

    override fun onClick(item: FileItem) {
//        addFiles("PIZDA")
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()

    }

}