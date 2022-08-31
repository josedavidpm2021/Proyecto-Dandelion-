package com.tongue.dandelion.helper

class CustomParser {

    companion object{

        fun getMinutesFromArrivalTimeString(time: String): Int{
            val times = time.split(":")
            val minute = times[1]
            return minute.toInt()
        }

    }

}