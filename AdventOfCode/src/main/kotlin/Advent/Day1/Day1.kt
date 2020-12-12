package Advent.Day1

import java.io.File


val list = arrayListOf<Int>()
val finalList  = arrayListOf<Int>()
fun main() {
    readInputFile("src/main/resources/Advent/Day1Input.txt")
    println(firstStep()) //1020084
    println(secondStep())
}

fun firstStep():Int
{
    for (i in 0 until list.size)
        for (j in 0 until list.size)
            if (i != j && (list[i] + list[j] == 2020)) return (list[i] * list[j])

    return 0
}

fun secondStep():Int
{
    for (i in 0 until list.size)
        for (j in 0 until list.size)
            for(x in 0 until list.size)
                if (i != j && x!=j && x != i && (list[i] + list[j] + list[x] == 2020)) return list[i] * list[j] * list[x]


    return 0
}

fun readInputFile(fileName: String)
= File(fileName).forEachLine { list.add(it.toInt())  }
