package Advent.Day3

import java.io.File


val trees = arrayListOf<String>()
fun main() {

    readInputFile("src/main/resources/Advent/Day3Input.txt")
    //First 167
    println(firstStep(3, false))
    // 736527114
    println(secondStep())
}

fun secondStep():Long{
    return firstStep(1, false) * firstStep(3, false) *
            firstStep(5, false) * firstStep(7, false) * firstStep(1, true)
}
fun firstStep(right:Int, odd: Boolean):Long
{
    var x = 0
    var y = 0
    var treesFound:Long = 0
    trees.forEach {
        if(!(odd && y.rem(2)==1)){
        if(x>= it.count()) x -= it.count()
        if( it[x]=='#') treesFound += 1
        x+=right}
        y+=1
    }
    return treesFound
}
fun readInputFile(fileName: String)
        = File(fileName).forEachLine { trees.add(it)  }