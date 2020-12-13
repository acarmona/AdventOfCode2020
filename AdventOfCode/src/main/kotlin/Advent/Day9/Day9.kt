package Advent.Day9

import java.io.File

val allLines = arrayListOf<String>()
var preamble = listOf<Long>()

fun main() {
    readInputFile("src/main/resources/Advent/Day9/Day9Input.txt")
    val preambleSize = 25
    println(firstStep(preambleSize)) //507622668
}

fun firstStep(preambleSize: Int):Long{
    val initialDataSet = allLines.map(String::toLong) as MutableList<Long>
    var final= false
    while(!final) {
        setPreamble(preambleSize, initialDataSet)
        val next = existsSolution(preambleSize, initialDataSet[preambleSize])

        if (!next) return initialDataSet[preambleSize]
        else initialDataSet.removeAt(0)

        if(initialDataSet.size==0) final = true
    }
    return 0
}

private fun existsSolution(preambleSize: Int, candidate: Long): Boolean {
    for (i in 0 until preambleSize)
        for (j in 0 until preambleSize)
            if (i != j && (preamble[i] + preamble[j] == candidate)) return true
    return false
}

fun setPreamble(length: Int, initialDataSet: MutableList<Long>){preamble = initialDataSet.take(length).sorted()}

fun readInputFile(fileName: String) = File(fileName).forEachLine { allLines.add(it) }

