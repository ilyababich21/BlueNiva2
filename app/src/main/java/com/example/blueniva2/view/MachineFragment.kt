package com.example.blueniva2.view

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.blueniva2.model.Machine
import com.example.blueniva2.viewmodel.MachineAdapter
import com.example.blueniva2.viewmodel.MachineViewModel
import com.example.blueniva2.databinding.FragmentMachineBinding
import com.example.bt_def.BluetoothConstans

class MachineFragment : Fragment(), MachineAdapter.Listener {
    private var preferences: SharedPreferences? = null
    private var machineAdapter = MachineAdapter(this@MachineFragment)
    private lateinit var binding: FragmentMachineBinding
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[MachineViewModel::class.java]
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMachineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        preferences=activity?.getSharedPreferences(BluetoothConstans.PREFERENCES,Context.MODE_PRIVATE)
//        val editor = preferences?.edit()
//        editor?.putString(BluetoothConstans.MAC,"hello_nigga")
//        editor?.putStringSet(BluetoothConstans.MAC, mutableSetOf("piska","ui"))
//        editor?.apply()

        binding.rcView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.rcView.adapter =machineAdapter
        viewModel.getMachineList().observe(viewLifecycleOwner, Observer {
            it?.let { machineAdapter.refreshMachines(it) }
        })
        binding.btAdd.setOnClickListener{
            viewModel.addMachines()
//            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragDrop,MainFragment.newInstance()).commit()
        }

    }

    override fun onClick(machine: Machine) {
//        requireActivity().supportFragmentManager.beginTransaction().addToBackStack("kek").replace(R.id.fragDrop,MashineInfoFragment.newInstance(machine)).commit()

            val action =MachineFragmentDirections.
               actionMachineFragmentToMashineInfoFragment(
                    machine
                )
            findNavController().navigate(action)


    }


}