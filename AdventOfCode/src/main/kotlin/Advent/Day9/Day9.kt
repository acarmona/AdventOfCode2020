package Advent.Day9

import java.io.File

val allLines = arrayListOf<String>()
var preamble = listOf<Long>()

fun main() {
    readInputFile("src/main/resources/Advent/Day9/Day9Input.txt")
    val preambleSize = 25
    val weaknessNumber =  firstStep(preambleSize) //507622668
    println(weaknessNumber)
    println(secondStep(preambleSize-1,weaknessNumber))
}

fun firstStep(preambleSize: Int):Long{
    val initialDataSet = allLines.map(String::toLong) as MutableList<Long>
    return searchSum(preambleSize, initialDataSet)
}

fun secondStep(preambleSize: Int, weaknessNumber:Long):Long{
    val initialDataSet = allLines.map(String::toLong) as MutableList<Long>
    val index = searchWeaknessIndex(initialDataSet, weaknessNumber)
    val firstDataSet = initialDataSet.chunked(index-1)[0] as MutableList<Long>
    initialDataSet.removeAll(firstDataSet)
    initialDataSet.removeAt(0)
    val firstSol = searchSumFixedCandidate(preambleSize,firstDataSet,weaknessNumber)
    if(firstSol>0) return firstSol
    return searchSumFixedCandidate(preambleSize,initialDataSet,weaknessNumber)
}

fun searchSumFixedCandidate(preambleSize: Int, initialDataSet: MutableList<Long>, candidate: Long): Long {
    var final = false
    while (!final) {
        setPreamble(preambleSize, initialDataSet)
        val existsSol = existsSolutionWithAllPreamble(candidate)

        if (existsSol){
            preamble = preamble.sorted()
            return preamble[0] + preamble.last()
        }
        else initialDataSet.removeAt(0)

        if (initialDataSet.size == 0) final = true
    }
    return 0
}

fun searchWeaknessIndex(initialDataSet:MutableList<Long>, weaknessNumber: Long): Int {
    return initialDataSet.indexOfFirst{ it == weaknessNumber }
}

private fun searchSum(preambleSize: Int, initialDataSet: MutableList<Long>): Long {
    var final = false
    while (!final) {
        setPreamble(preambleSize, initialDataSet)
        val next = existsSolution(preambleSize, initialDataSet[preambleSize])

        if (!next) return initialDataSet[preambleSize]
        else initialDataSet.removeAt(0)

        if (initialDataSet.size == 0) final = true
    }
    return 0
}

private fun existsSolution(preambleSize: Int, candidate: Long): Boolean {
    for (i in 0 until preambleSize)
        for (j in 0 until preambleSize)
            if (i != j && (preamble[i] + preamble[j] == candidate)) return true
    return false
}
private fun existsSolutionWithAllPreamble(candidate: Long): Boolean {
    var accumulator: Long= 0
    for (i in 0 until preamble.count()) {
        accumulator += preamble[i]
        if(accumulator == candidate) return true
    }
    return false
}

fun setPreamble(length: Int, initialDataSet: MutableList<Long>){preamble = initialDataSet.take(length)}

fun readInputFile(fileName: String) = File(fileName).forEachLine { allLines.add(it) }

