package com.example.blueniva2.view

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blueniva2.viewmodel.FileAdapter
import com.example.blueniva2.model.FileItem
import com.example.blueniva2.model.Machine
import com.example.blueniva2.R
import com.example.blueniva2.databinding.FragmentMashineInfoBinding
import com.example.blueniva2.viewmodel.FileViewModel
import com.example.blueniva2.viewmodel.MachineAdapter
import com.example.blueniva2.viewmodel.MachineInfoViewModel
import com.example.bt_def.BluetoothConstans
import com.example.bt_def.bluetooth.BluetoothController


class MashineInfoFragment : Fragment(), FileAdapter.Listener {
    private lateinit var binding: FragmentMashineInfoBinding
    private var machine: Machine? = null
    private var adapter = FileAdapter(this@MashineInfoFragment)
    private lateinit var bAdapter: BluetoothAdapter
    private lateinit var bluetoothController: BluetoothController
    private val viewModel: MachineInfoViewModel by lazy { ViewModelProvider(requireActivity())[MachineInfoViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val safeArgs: MashineInfoFragmentArgs by navArgs()
        machine = safeArgs.machineData
//        initBtAdapter()
//
//        bluetoothController = BluetoothController(bAdapter)
        binding = FragmentMashineInfoBinding.inflate(inflater, container, false)
        return binding.root
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Разрешение получено, можно сохранять файл

            } else {
                Toast.makeText(requireContext(), "Для сохранения файла необходимо разрешение на запись во внешнее хранилище", Toast.LENGTH_SHORT).show()
            }
        }
    }



    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.createBtControl(bAdapter)

//        val pref = activity?.getSharedPreferences(
//            BluetoothConstans.PREFERENCES,
//            Context.MODE_PRIVATE
//        )
//        val mac = pref?.getString(BluetoothConstans.MAC, "")

//        viewModel.connect(mac ?: "")


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    101
                )
            }
        }


        machine?.let {
            binding.imMachine.setImageResource(it.imageID); binding.txName.text = it.name
        }
        binding.rcView.layoutManager = LinearLayoutManager(requireContext())
        binding.rcView.adapter = adapter


        viewModel.getConnect().observe(viewLifecycleOwner, Observer {

            Toast.makeText(
                requireContext(),
                if (it) "Connect" else "Not connect",
                Toast.LENGTH_LONG
            ).show()
        })


        viewModel.getFileList().observe(viewLifecycleOwner, Observer {
            if (it == null) {
                Log.d("MyLog", "hui")

            } else {
                Log.d("MyLog", it.toString())
                adapter.refreshFiles(it)
            }
        })


//        try{
//            viewModel.addFile(FileItem("Pizda"," eto ebaniy pizdec",
//                R.drawable.folder
//            ),)
//
//        }catch (e:Exception){
//            Log.d("MyLog", e.toString())
//        }
//        viewModel.setFileList(listOf(
//            FileItem("Zhopa"," eto ebaniy pizdec",
//                R.drawable.baseline_upload_file_24
//            ), FileItem("Hui"," eto ebaniy pizdec",
//                R.drawable.baseline_upload_file_24
//            ), FileItem("siska"," eto ebaniy pizdec",
//                R.drawable.baseline_upload_file_24
//            )
//        ))

    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.closeConnection()
    }

    override fun onClick(item: FileItem) {
        viewModel.setSomeFiles()

        if (item.name.split(".").size >= 2) {
//            viewModel.downloadFile(item.name)
        } else {
//            viewModel.sendData(item.name)
        }
    }
}