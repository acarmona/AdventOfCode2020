package Advent.Day4

import java.io.File

val allLines = arrayListOf<String>()
val passports =  arrayListOf<MutableMap<String,String>>()
fun main() {

    readInputFile("src/main/resources/Advent/Day4Input.txt")
    setMap()
    println(firstStep())
    println(secondStep())
}

fun firstStep():Long{
    var passOk = 0
    passports.forEach { pass ->
        if(pass.containsKey("byr") && pass.containsKey("iyr") && pass.containsKey("eyr") && pass.containsKey("hgt") &&
            pass.containsKey("hcl") && pass.containsKey("ecl") && pass.containsKey("pid") )
        {
            passOk+=1
        }
    }
    return passOk.toLong()
}
/*
byr (Birth Year) - four digits; at least 1920 and at most 2002.
iyr (Issue Year) - four digits; at least 2010 and at most 2020.
eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
hgt (Height) - a number followed by either cm or in:
    If cm, the number must be at least 150 and at most 193.
    If in, the number must be at least 59 and at most 76.
hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
pid (Passport ID) - a nine-digit number, including leading zeroes.
cid (Country ID) - ignored, missing or not.

* */
fun secondStep():Long{
    var passOk = 0
    var allFieldsOk = false
    passports.forEach { pass ->
        if(pass.containsKey("byr")){
            var byr= pass["byr"]?.toInt()
            allFieldsOk = (byr != null && pass["byr"]?.count()==4 && byr in 1920 .. 2002)
            if(!allFieldsOk) println("fail byr")
        }
        if(allFieldsOk && pass.containsKey("iyr")){
            var iyr = pass["iyr"]?.toInt()
            allFieldsOk = (iyr != null && pass["iyr"]?.count()==4 && iyr in 2010 .. 2020)
            if(!allFieldsOk) println("fail iyr")
        }
        if(allFieldsOk && pass.containsKey("eyr")){
            var eyr = pass["eyr"]?.toInt()
            allFieldsOk = (eyr != null && pass["eyr"]?.count()==4 && eyr in 2020 .. 2030)
            if(!allFieldsOk) println("fail eyr")
        }
        if(allFieldsOk && pass.containsKey("hgt")){
            val unit = pass["hgt"]?.takeLast(2)
            allFieldsOk = when (unit) {
                "cm" -> (pass["hgt"]?.substringBefore("cm")?.toInt() in 150..193)
                "in" -> (pass["hgt"]?.substringBefore("in")?.toInt() in 59..76)
                else -> false
            }
            if(!allFieldsOk) println("fail hgt")
        }
        if(allFieldsOk && pass.containsKey("hcl")){
            allFieldsOk=(pass["hcl"]?.get(0)?.equals('#') == true)
            pass["hcl"]?.trim()?.substringAfter('#')?.forEach { ch ->
                if (allFieldsOk && !(ch in '0' .. '9' || ch in 'a' .. 'f')) allFieldsOk = false}
            if(!allFieldsOk) println("fail hcl")
        }
        if(allFieldsOk && pass.containsKey("ecl")){
            //amb blu brn gry grn hzl oth
            if (!pass["ecl"].equals("amb") && !pass["ecl"].equals("blu") && !pass["ecl"].equals("brn") && !pass["ecl"].equals("gry") &&
                !pass["ecl"].equals("grn") && !pass["ecl"].equals("hzl") && !pass["ecl"].equals("oth")){
                allFieldsOk = false
            }
            if(!allFieldsOk) println("fail ecl")
        }
        if(allFieldsOk && pass.containsKey("pid")){
            if(pass["pid"]?.trim()?.count() != 9) allFieldsOk = false
            if(!allFieldsOk) println("fail pid")
        }
        if(allFieldsOk && !(pass.containsKey("byr") && pass.containsKey("iyr") && pass.containsKey("eyr") && pass.containsKey("hgt") &&
            pass.containsKey("hcl") && pass.containsKey("ecl") && pass.containsKey("pid"))) allFieldsOk = false

        if (allFieldsOk) passOk+=1
        if(!allFieldsOk){
            println(pass)
            println("*********")
        }
    }
    return passOk.toLong()
}

fun setMap()
{
    var passport = mutableMapOf<String, String>()
    allLines.forEach {
        if(it.trim().isEmpty())
        {
            passports.add(passport)
            passport = mutableMapOf<String, String>()
        }
        else
            it.split(" ").forEach{
                param -> passport[param.substringBefore(":").trim()] = param.substringAfter(":").trim()
            }
    }
    passports.add(passport)
    passport = mutableMapOf<String, String>()
}

fun readInputFile(fileName: String)
        = File(fileName).forEachLine { allLines.add(it)  }