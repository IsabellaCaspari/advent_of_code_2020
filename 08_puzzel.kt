import java.io.File
import  kotlin.math.round

const val FILE_NAME = "08_puzzel_input.txt"

fun main() {
    partOne()
    //partTwo()
}

private fun partOne() {
    println("""Puzzel Day 8, Part 1 """)
    val result = doOperationFromBootCode()

    println("""The result is $result""")
}

private fun partTwo() {
    println("""Puzzel Day 8, Part 2 """)
}

private fun doOperationFromBootCode(): Int {
    val list = readInputFile(FILE_NAME)
    val operations: MutableList<Operation> = mutableListOf<Operation>()

    list.forEach { line ->
        val array = line.split(" ")
        operations.add(Operation(array[0], array[1].toInt()))
    }

    var accumulator = 0
    var execute = 0
    var pos = 0
    while (execute != 2) {
        val operation = operations[pos]
        operation.increaseExecute()
        execute = operation.execute
        when {
            execute == 2 -> {
                return accumulator
            }
            operation.command == "acc" -> {
                accumulator = accumulator + operation.value
                pos = increasePos(pos, operations.size)
            }
            operation.command == "jmp" -> {
                pos = increasePos(pos, operations.size, operation.value)
            }
            else -> {
                pos = increasePos(pos, operations.size)
            }
        }
    }

    return accumulator
}

private fun increasePos(pos: Int, listLength: Int, count: Int = 1) =
    (pos + count) % listLength


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

data class Operation(
    val command: String,
    val value: Int,
    var execute: Int = 0
) {
    fun increaseExecute() {
        execute++
    }

    override fun toString(): String {
        println("Command")
        println(command + " " + value)
        return ""
    }
}