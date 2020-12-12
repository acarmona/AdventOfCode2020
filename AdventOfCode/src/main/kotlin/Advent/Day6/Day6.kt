package Advent.Day6

import java.io.File

val allLines = arrayListOf<String>()
val allAnswers =  arrayListOf<MutableList<Char>>()
val allAnswersStep2 =  arrayListOf<MutableList<String>>()

fun main() {
    readInputFile("src/main/resources/Advent/Day6/Day6Input.txt")
    fillAnswersList()
    println(firstStep()) //6291
    fillAnswersListForStep2()
    println(secondStep()) //3052
}

fun firstStep():Int{
    var sum =0
    allAnswers.forEach { sum += it.count() }
    return sum
}

fun secondStep():Int{
    var result = 0
    allAnswersStep2.forEach { group ->
        val sameAnswer = mutableMapOf<Char,Int>()
        group.forEach{ans ->

            ans.forEach { ch ->
                if(sameAnswer.containsKey(ch)) sameAnswer[ch]=sameAnswer.getValue(ch)+1
                else sameAnswer[ch] = 1
                }
        }
        sameAnswer.forEach{if (it.value == group.count()) result += 1}
    }
    return result
}

fun readInputFile(fileName: String)
        = File(fileName).forEachLine { allLines.add(it)  }

fun fillAnswersList()
{
    var answer = mutableListOf<Char>()
    allLines.forEach {
        if(it.isEmpty())
        {
            allAnswers.add(answer.distinct() as MutableList<Char>)
            answer = mutableListOf()
        }
        else
            it.forEach{
                    param -> answer.add(param)
            }
    }
    allAnswers.add(answer.distinct() as MutableList<Char>)
    answer = mutableListOf()
}

fun fillAnswersListForStep2()
{
    var answer = mutableListOf<String>()
    allLines.forEach {
        if(it.isEmpty())
        {
            allAnswersStep2.add(answer)
            answer = mutableListOf()
        }
        else
            answer.add(it)

    }
    allAnswersStep2.add(answer)
    answer = mutableListOf()
}