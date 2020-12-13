package Advent.Day10

import java.io.File

val allLines = arrayListOf<String>()
var joltArrangements = mutableListOf<MutableList<Long>>()
var maxVal:Long =0
var joltAdapterAvailable = mutableListOf<Long>()

fun main() {

    readInputFile("src/main/resources/Advent/Day10/Day10Tiny.txt")
    println(firstStep()) //2048
    println(secondStep())
    println(joltArrangements)
}

fun secondStep():Int{
    joltAdapterAvailable = setJoltInitialList()
    for (index in 0 .. joltAdapterAvailable.lastIndex)
    {
        joltArrangements.add(searchSolution(index, joltAdapterAvailable, true))
    }
    //joltArrangements =  joltArrangements.distinct().toMutableList()
    return joltArrangements.count()
}

fun searchSolution(index: Int, joltAdapterAvailable: MutableList<Long>, firstAttempt: Boolean): MutableList<Long> {
    var innerJoltList = joltAdapterAvailable.toMutableList()
    if(index>joltAdapterAvailable.lastIndex) return joltAdapterAvailable
    val window = setJoltWindow(index,innerJoltList)
    if(canBeSolution1(window,1, firstAttempt))
        return (searchSolution(index+1,innerJoltList,false))
    if(canBeSolution2(window,2, firstAttempt)) {
        if(index+1 < innerJoltList.lastIndex) innerJoltList.removeAt(index+1)
        return (searchSolution(index + 2, innerJoltList, false))
    }
    if(canBeSolution3(window,3,firstAttempt)) {
        if(index+1 < innerJoltList.lastIndex) innerJoltList.removeAt(index+1)
        if(index+2 < innerJoltList.lastIndex) innerJoltList.removeAt(index+2)
        return (searchSolution(index + 3, innerJoltList, false))
    }
    return joltAdapterAvailable
}

fun canBeSolution1(window: MutableList<Long>, plus: Int, firstAttempt: Boolean): Boolean {
    if (!firstAttempt && !canEvaluate(window, 0)) return false
    val reference = window[0] + plus
    if (canEvaluate(window,1) && reference == window[1]) return true
    return false
}

fun canBeSolution2(window: MutableList<Long>, plus: Int, firstAttempt: Boolean): Boolean {
    if (!firstAttempt && !canEvaluate(window, 0)) return false
    val reference = window[0] + plus
    if (canEvaluate(window,2) && (reference == window[1] || reference == window[2])) return true
    return false
}

fun canBeSolution3(window: MutableList<Long>, plus: Int, firstAttempt: Boolean): Boolean {
    if (!firstAttempt && !canEvaluate(window, 0)) return false
    val reference = window[0] + plus
    if (canEvaluate(window,3) && (reference == window[1] || reference == window[2] || reference == window[3])) return true
    return false
}

private fun canEvaluate(window: MutableList<Long>, index: Int): Boolean {
    val comp: Long = 0
    if (window[index] == comp) return false
    return true
}

fun setJoltWindow(initIndex: Int,joltAdapterAvailable: MutableList<Long>): MutableList<Long>{
    var finalIndex = initIndex + 3
    if(finalIndex > joltAdapterAvailable.lastIndex)
        finalIndex = joltAdapterAvailable.lastIndex
    val window = joltAdapterAvailable.subList(initIndex,finalIndex+1).toMutableList()
    if(window.size<4) for(c in window.size-1 .. 2) window.add(0)
    return window
}

//region firstCase
fun firstStep():Long{
    var joltAdapterAvailable = setJoltInitialList()
    var finished = false
    var multiple1:Long =0
    var multiple3:Long =0
    while(!finished) {
        var window = setJoltWindow(joltAdapterAvailable)
        val pair = evaluateCase(window)
        multiple1 += pair.first
        multiple3 += pair.second
        finished = noMoreWindowsAvailable(joltAdapterAvailable)
    }
    return multiple1*multiple3
}

private fun evaluateCase(window: List<Long>): Pair<Long, Long> {
    var reference = window[0]
    if ((reference + 1) == window[1])
        return Pair(1, 0)
    if ((reference + 3) == window[1] || (reference + 3) == window[2] || (reference + 3) == window[3]) {
        return Pair(0, 1)
    }
    return Pair(0, 0)

}

fun noMoreWindowsAvailable(joltAdapterAvailable: MutableList<Long>): Boolean {
    if(joltAdapterAvailable.size<=1) return true
    return false
}

fun setJoltWindow(joltAdapterAvailable: MutableList<Long>): MutableList<Long>{
    val window = joltAdapterAvailable.take(4) as MutableList<Long>
    if(window.size<4) for(c in window.size-1 .. 3) window.add(0)
    joltAdapterAvailable.removeAt(0)
    return window
}
//endregion firstCase

//region common
fun fillJoltBagOrdered(): MutableList<Long>{
    return allLines.map(String::toLong).sorted() as MutableList<Long>
}

private fun setJoltInitialList(): MutableList<Long> {
    var joltAdapterAvailable = mutableListOf<Long>()
    joltAdapterAvailable.add(0)
    joltAdapterAvailable.addAll(fillJoltBagOrdered())
    maxVal= joltAdapterAvailable.maxOrNull()?.plus(3)!!
    joltAdapterAvailable.add(maxVal)
    return joltAdapterAvailable
}

fun readInputFile(fileName: String) = File(fileName).forEachLine { allLines.add(it) }
//endregion common