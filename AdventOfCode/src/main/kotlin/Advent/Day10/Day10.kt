package Advent.Day10

import java.io.File

val allLines = arrayListOf<String>()
var joltArrangements = mutableListOf<Long>()


fun main() {
    readInputFile("src/main/resources/Advent/Day10/Day10Input.txt")
    println(firstStep()) //2048
}

fun firstStep():Long{
    //Init
    var joltAdapterAvailable = mutableListOf<Long>()
    joltAdapterAvailable.add(0)
    joltAdapterAvailable.addAll(fillJoltBagOrdered())
    val max:Long= joltAdapterAvailable.maxOrNull()?.plus(3)!!
    joltAdapterAvailable.add(max)
    //Body
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

fun fillJoltBagOrdered(): MutableList<Long>{
    return allLines.map(String::toLong).sorted() as MutableList<Long>
}


fun setJoltWindow(joltAdapterAvailable: MutableList<Long>): MutableList<Long>{
    val window = joltAdapterAvailable.take(4) as MutableList<Long>
    if(window.size<4) for(c in window.size-1 .. 3) window.add(0)
    joltAdapterAvailable.removeAt(0)
    return window
}

fun readInputFile(fileName: String) = File(fileName).forEachLine { allLines.add(it) }

