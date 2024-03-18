package com.example.blueniva2.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blueniva2.model.Machine
import com.example.blueniva2.R
import com.example.blueniva2.databinding.MachineItemBinding

class MachineAdapter(private val listener: Listener) :
    RecyclerView.Adapter<MachineAdapter.MachineHolder>() {
    var machineList: List<Machine> = ArrayList()

    class MachineHolder(item: View, private val listener: Listener) :
        RecyclerView.ViewHolder(item) {
        val binding = MachineItemBinding.bind(item)


        fun bind(machine: Machine) = with(binding) {
            txName.text = machine.name
            imMachine.setImageResource(machine.imageID)
            itemView.setOnClickListener {
                listener.onClick(machine)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MachineHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.machine_item, parent, false)
        return MachineHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return machineList.size
    }

    override fun onBindViewHolder(holder: MachineHolder, position: Int) {
        holder.bind(machineList[position])
    }

    fun refreshMachines(machines: List<Machine>) {
        this.machineList = machines
        notifyDataSetChanged()
    }


    interface Listener {
        fun onClick(machine: Machine)
    }

}