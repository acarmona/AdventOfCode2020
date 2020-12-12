package Advent.Day7

import java.io.File

val allLines = arrayListOf<String>()
val bags = mutableMapOf<String, List<String>>()


fun main() {
    readInputFile("src/main/resources/Advent/Day7/Day7Input.txt")
    fillBags()
    println(bags)
    println(firstStep()) //208
    println(secondStep()) //1664
}
 // region FirstStep
fun firstStep():Int{
    return bagBacktracking().count()-1
}

fun bagBacktracking(): MutableMap<String,Int>{
    val bagSolution = mutableListOf<String>()
    val finalSol = mutableMapOf<String,Int>()
    val usedKeys = mutableListOf<String>()
    bagSolution.add("shiny gold")
    do {
        val bagToSearch = bagSolution[0]
        if(!usedKeys.contains(bagToSearch)){
            val iteSolution = searchSolution(bagToSearch)
            bagSolution.addAll(iteSolution.keys)
            usedKeys.add(bagToSearch)
            finalSol[bagToSearch] = iteSolution.values.sum()
        }
        bagSolution.remove(bagToSearch)
    }while(bagSolution.size > 0)
    return finalSol
}

private fun searchSolution(sol: String): MutableMap<String,Int>{
    val bagSolution = mutableMapOf<String,Int>()
    bags.forEach { bag ->
        bag.value.forEach { bagValue ->
            if (bagValue.contains(sol))
            {
                bagSolution[bag.key] = sumAllBags(bag.value)
            }
        }
    }
    return bagSolution
}

fun sumAllBags(bags: List<String>):Int{
    var bagCounter = 0
    bags.forEach{
        bagCounter += it.trim().substringBefore(" ").toInt()
    }
    return bagCounter
}
// endregion

fun secondStep():Int{
    return search()
}

fun search():Int{
    val searchNode = "shiny gold"
    return searchNodes(searchNode)-1
}

private fun searchNodes(searchNode: String):Int {
    bags.forEach { bag ->
        if (bag.key == searchNode ) {
            return if (bag.value.any()) {
                var res = 1
                bag.value.forEach { bagValue ->
                    val searchInnerNode = bagValue.trim().substringAfter(" ")
                        .replace("bags","").replace("bag","").trim()
                    res += (bagValue.trim().substringBefore(" ").toInt() *
                            searchNodes(searchInnerNode))
                }
                res
            } else 1
        }
    }
    return 0
}


fun fillBags(){
    allLines.forEach { line ->
        val key = line.substringBefore(" bags contain").trim()
        val listOfBags = line.substringAfter(" bags contain ")
            .removeSuffix(".").split(" bags,", " bag,")
        if(!listOfBags.contains("no other bags")) {
            bags[key] = listOfBags
        }
        else{bags[key]= emptyList() }
    }
}

fun readInputFile(fileName: String)
        = File(fileName).forEachLine { allLines.add(it)  }

