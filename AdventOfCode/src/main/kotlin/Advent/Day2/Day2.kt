package Advent.Day2

import java.io.File



fun main() {

    println(firstStep("src/main/resources/Advent/Day2Input.txt"))
    println(secondStep("src/main/resources/Advent/Day2Input.txt"))
}

fun firstStep(fileName: String):Int
{
    var count = 0
    File(fileName).forEachLine {
        val key = it.split(":")[0]
        val value = it.split(":")[1].trim()
        val min = key.substringBefore("-").toInt()
        val max = key.substringAfter("-").substringBefore(" ").toInt()
        val constrain = key.substringAfter(" ")
        val occurrences = value.count{ occ -> constrain.contains(occ)}
        if(occurrences in min..max) count += 1
    }
    return count
}

fun secondStep(fileName: String):Int
{
    var count2 = 0
    File(fileName).forEachLine {
        val key = it.split(":")[0]
        val value = it.split(":")[1].trim()
        val min = key.substringBefore("-").toInt() -1
        val max = key.substringAfter("-").substringBefore(" ").toInt()-1
        val constrain = key.substringAfter(" ").trim()
        if((value[min].toString() == constrain && value[max].toString() != constrain)
            || (value[max].toString() == constrain && value[min].toString() != constrain)) count2 += 1
    }
    return count2
}