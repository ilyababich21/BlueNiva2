package com.example.blueniva2.model

import com.example.blueniva2.R

object MachineData {
    fun getMachine()= listOf(
        Machine("СКРВ","Вот это машина!!!", R.drawable.harvester_image),
        Machine("Q800","Вот это дааа!!!", R.drawable.machine_image),
        Machine("СКРВ","Вот это машина!!!", R.drawable.harvester_image),
        Machine("Q800","Вот это дааа!!!", R.drawable.machine_image),
        Machine("СКРВ","Вот это машина!!!", R.drawable.harvester_image),
        Machine("Q800","Вот это дааа!!!", R.drawable.machine_image),
        Machine("СКРВ","Вот это машина!!!", R.drawable.harvester_image),
        Machine("Q800","Вот это дааа!!!", R.drawable.machine_image),
    )

    fun addMachine()=listOf(
        Machine("Hui","Вот это машина!!!", R.drawable.machine_image),
        Machine("Pizda","Вот это дааа!!!", R.drawable.harvester_image),

    )

}