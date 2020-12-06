import java.io.File
import  kotlin.math.round

const val GREEN_BOLD = "\u001b[1;32m"
const val BLACK = "\u001b[0;30m"
const val FILE_NAME = "05_puzzel_input.txt"
var ids: MutableList<Int> = ArrayList()

fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    println("""${GREEN_BOLD}Puzzel Day 5, Part 1 """)
    val list = readInputFile(FILE_NAME)
    for (partitioning in list) {
        ids.add(calculateId(partitioning))
    }
    val result = ids.maxOrNull()
    println("""${BLACK}The answer is $result""")
}

private fun partTwo() {
    println("""${GREEN_BOLD}Puzzel Day 5, Part 2 """)
    ids.sort()

    for (i in 0..ids.size - 2) {
        if (ids[i] == (ids[i + 1] - 2)) {
            val result = ids[i] + 1
            println("""${BLACK}The answer is $result""")
        }
    }
}

private fun calculateId(partitioning: String): Int {
    val row = calcRow(partitioning.subSequence(0, 7).toString())
    val column = calcColumn(partitioning.subSequence(7, 10).toString())
    return row * 8 + column
}

private fun calcRow(partitioning: String): Int {
    var start = 0
    var end = 127
    for (letter in partitioning) {
        val diff =
            ((end - start) / 2.0).toBigDecimal().setScale(0, java.math.RoundingMode.UP).toInt()
        when (letter) {
            'F' -> {
                end = end - diff
            }
            'B' -> {
                start = start + diff
            }
        }
    }

    return if (partitioning[partitioning.length - 1] == 'F') {
        start
    } else {
        end
    }
}

private fun calcColumn(partitioning: String): Int {
    var start = 0
    var end = 7
    for (letter in partitioning) {
        val diff =
            ((end - start) / 2.0).toBigDecimal().setScale(0, java.math.RoundingMode.UP).toInt()
        when (letter) {
            'L' -> {
                end = end - diff
            }
            'R' -> {
                start = start + diff
            }
        }
    }
    return if (partitioning[partitioning.length - 1] == 'L') {
        start
    } else {
        end
    }
}

private fun readInputFile(filename: String): List<String> {
    var list: MutableList<String> = ArrayList()
    File(filename).forEachLine {
        try {
            list.add(it)
        } catch (e: java.lang.Exception) {
            println(e)
        }
    }
    return list
}
