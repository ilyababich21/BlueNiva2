package com.example.blueniva2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blueniva2.model.Machine
import com.example.blueniva2.model.MachineData

class MachineViewModel : ViewModel() {
    private var machineList:MutableLiveData<List<Machine>> = MutableLiveData()
    private var currentMachine:MutableLiveData<Machine> = MutableLiveData()

    init {
        machineList.value = MachineData.getMachine()
    }

    fun getMachineList(): MutableLiveData<List<Machine>> {
        return machineList
    }

    fun addMachines(){
        machineList.value = machineList.value?.plus(MachineData.addMachine())
    }

//    fun getCurMachine():{
//
//    }

}