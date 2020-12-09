import java.io.File
import  kotlin.math.round

const val FILE_NAME = "09_puzzel_input.txt"

fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    println("""Puzzel Day 9, Part 1 """)
    val result = findFirstNotValidNumber(25)
    println("""The result is $result""")
}

private fun partTwo() {
    println("""Puzzel Day 9, Part 2 """)
    val result = findContiguousList(2089807806)
    println("""The result is $result""")
}

private fun findFirstNotValidNumber(size: Int): Long {
    val list = readInputFile(FILE_NAME)
    for (i in size..list.size) {
        val preamble = list.subList(i - size, i)
        if (!checkValidXmasNumber(list[i], preamble)) {
            return list[i]
        }
    }
    return 0
}

private fun checkValidXmasNumber(number: Long, preamble: List<Long>): Boolean {
    for (i in 0..preamble.size - 1) {
        for (j in 1..preamble.size - 1) {
            if (preamble[i] + preamble[j] == number) {
                return true
            }
        }
    }
    return false
}

private fun findContiguousList(number: Long): Long {
    val list = readInputFile(FILE_NAME)
    for (i in 0..list.size - 1) {
        val sumList: MutableList<Long> = ArrayList()
        loop@ for (j in i..list.size - 1) {
            sumList.add(list[j])
            when {
                list[j] == number -> {
                    break@loop
                }
                sumList.sum() == number -> {
                    return (sumList.maxOrNull() ?: 0) + (sumList.minOrNull() ?: 0)
                }
                sumList.sum() > number -> {
                    break@loop
                }
            }
        }

    }
    return 0
}

private fun readInputFile(filename: String): List<Long> {
    var list: MutableList<Long> = ArrayList()
    File(filename).forEachLine {
        try {
            list.add(it.toLong())
        } catch (e: java.lang.Exception) {
            println(e)
        }
    }
    return list
}
