package Advent.Day5

import java.io.File

val allLines = arrayListOf<String>()
val seats = mutableMapOf<Int,Int>()
fun main() {

    readInputFile("src/main/resources/Advent/Day5/Day5Input.txt")
    val max = firstStep() //996
    println(max)
    clearSeats(max)
    println(secondStep()) //671

}

private fun clearSeats(max: Int) {
    for (c in 0..max) seats[c] = 0
}

fun secondStep():Int{
    for (line in allLines) {
        val cols = line.takeLast(3)
        val rows = line.substringBefore(cols)
        val seat = (searchRow(rows, 0, 127) * 8) + searchCol(cols, 0, 7)
        seats[seat] = 1
    }
    var solution = 0
    for (f in 0 .. seats.count())
        if(seats[f] == 0
            && (f<seats.count() && seats[f+1]!=0)
            && (f>0 && seats[f-1]!=0 ))
            solution= f
    return solution
}

fun firstStep():Int{
    var maxSeat = 0
    for (line in allLines) {
        val cols = line.takeLast(3)
        val rows = line.substringBefore(cols)
        val seat = (searchRow(rows, 0, 127) * 8) + searchCol(cols, 0, 7)
        if(seat > maxSeat) maxSeat = seat
    }
    return maxSeat
}

fun searchCol(colPath:String, min:Int, max:Int):Int{
    if(colPath.count() == 1)
    {
        if(colPath=="R") return max
        return min
    }
    if(colPath[0]=='L'){
        return searchCol(colPath.substring(1),min,((max-min)/2)+min)
    }
    if(colPath[0]=='R'){
        return searchCol(colPath.substring(1),((max-min)/2)+min + 1,max)
    }
    return 0
}


fun searchRow(rowPath:String, min:Int, max:Int):Int{
    if(rowPath.count() == 1)
    {
        if(rowPath=="F") return min
        return max
    }
    if(rowPath[0]=='F'){
        return searchRow(rowPath.substring(1),min,((max-min)/2)+min)
    }
    if(rowPath[0]=='B'){
        return searchRow(rowPath.substring(1),((max-min)/2)+min+1,max)
    }
    return 0
}

fun readInputFile(fileName: String)
        = File(fileName).forEachLine { allLines.add(it)  }