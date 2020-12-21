package Advent.Day11

import java.io.File

val initSeats = arrayListOf<String>()
var temporarySeats = mutableListOf<String>()
var currentSeats = mutableListOf<String>()
var maxRows:Int = 0
var maxCols:Int = 0

const val occupied ="#"
const val free ="L"


fun main() {

    readInputFile("src/main/resources/Advent/Day11/Day11Input.txt")
    println(firstStep()) //2273
}

fun firstStep():Int{

    currentSeats = initSeats.toMutableList()
    temporarySeats = initSeats.toMutableList()

    maxRows = currentSeats.count()
    maxCols = currentSeats[0].count()
    var stopLoop = false
    var count: Int
    while(!stopLoop) {
        count = 0
        for (row in 0 until maxRows) {
            for (col in 0 until maxCols) {
                var seatRow = currentSeats[row]
                when (seatRow[col]) {
                    'L' -> {
                        //If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
                        if (countConcurrencesOf(row, col, occupied) == 0) {
                            changeSeatRow(row, col, '#')
                            count += 1
                        }
                    }
                    '#' -> {
                        //If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
                        if (countConcurrencesOf(row, col, occupied) - 1 >= 4) {
                            changeSeatRow(row, col,'L')
                            count += 1
                        }
                    }
                }
            }
        }
        currentSeats = temporarySeats.toMutableList()
        stopLoop = (count==0)
    }
    var results = 0
    currentSeats.forEach { row -> results += row.count{ occupied.contains(it)} }
    return results
}

private fun changeSeatRow(row: Int, col: Int, status: Char) {
    var seatRowToChange = temporarySeats[row]
    seatRowToChange = seatRowToChange.substring(0, col) + status + seatRowToChange.substring(col + 1)
    temporarySeats[row] = seatRowToChange
}

fun countConcurrencesOf(row: Int, col: Int, chr:String):Int{
    val rowAbove = if(row - 1 < 0) 0 else row -1
    val rowBelow = if(row + 1 > maxRows -1) maxRows-1 else row +1
    val colBefore = if(col - 1 < 0) 0 else col -1
    val colAfter = setColAfter(col)
    var countMatches = 0
    if(rowAbove!= row) countMatches += currentSeats[rowAbove].substring(colBefore,colAfter).count{chr.contains(it)}
    countMatches += currentSeats[row].substring(colBefore,colAfter).count{chr.contains(it)}
    if(rowBelow!= row)
        countMatches += currentSeats[rowBelow].substring(colBefore,colAfter).count{chr.contains(it)}
    return countMatches
}

private fun setColAfter(col: Int): Int {
    if (col + 1 > maxCols-1)
        return maxCols
    return col + 2
}


fun readInputFile(fileName: String) = File(fileName).forEachLine { initSeats.add(it) }