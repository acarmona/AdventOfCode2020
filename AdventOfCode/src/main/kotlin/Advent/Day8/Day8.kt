package Advent.Day8

import java.io.File

val allLines = arrayListOf<String>()
var commands = mutableListOf<Pair<String,Int>>()
var visits = mutableListOf<Boolean>()


fun main() {
    readInputFile("src/main/resources/Advent/Day8/Day8Input.txt")
    fillCommands()
    println(firstStep()) //1818
    println(secondStep()) //631
}

fun firstStep():Int{
    return searchLoop(commands).first
}


fun secondStep():Int{
    var result = 0
    for (counter in 0 until commands.size)
    {
        val cpCommands = commands.toMutableList()
        if(cpCommands[counter].first!="acc") {
            when (cpCommands[counter].first) {
                "jmp" -> cpCommands[counter] = Pair("nop", cpCommands[counter].second)
                "nop" -> cpCommands[counter] = Pair("jmp", cpCommands[counter].second)
            }
            val temp = searchLoop(cpCommands)
            resetVisits()
            result = temp.first
            if (temp.second >= commands.size-1) {
                return result
            }
        }
    }
    return result
}


private fun searchLoop(outComm:MutableList<Pair<String, Int>>): Pair<Int,Int> {
    var loopFound = false
    var accumulator = 0
    var count = 0
    while (!loopFound && (count < commands.size)) {
        val currentCommand = outComm[count]
        if (!visits[count]) {
            visits[count] = true
            when (currentCommand.first) {
                "acc" -> {
                    accumulator += currentCommand.second
                    count += 1
                }
                "jmp" -> {count += currentCommand.second}
                else -> {count += 1}
            }
        } else loopFound = true
    }
    return Pair(accumulator,count)
}

fun resetVisits() {for (c in 0 until visits.size) visits[c]=false}

fun fillCommands(){
    var index = 0
    allLines.forEach {
        val val1 = it.substringBefore(" ")
        val val2 = it.substringAfter(" ").toInt()
        commands.add(index,Pair(val1,val2))
        visits.add(index,false)
        index ++
    }
}

fun readInputFile(fileName: String)
        = File(fileName).forEachLine { allLines.add(it)  }

