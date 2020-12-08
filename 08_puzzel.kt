import java.io.File
import  kotlin.math.round

const val FILE_NAME = "08_puzzel_input.txt"

fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    println("""Puzzel Day 8, Part 1 """)
    val result = runOperations(extractOperation())
    println("""The result is $result""")
}

private fun partTwo() {
    println("""Puzzel Day 8, Part 2 """)
    val result = doOperationFromBootCode(extractOperation())
    println("""The result is $result""")
}


private fun extractOperation(): MutableList<Operation> {
    val list = readInputFile(FILE_NAME)
    val operations: MutableList<Operation> = mutableListOf<Operation>()

    list.forEach { line ->
        val array = line.split(" ")
        operations.add(Operation(array[0], array[1].toInt()))
    }
    return operations
}

private fun doOperationFromBootCode(operations: MutableList<Operation>): Int {
    var accumulator = 0
    for (i in 0..operations.size - 1) {

        val copyOperation: MutableList<Operation> = mutableListOf<Operation>()
        copyOperation.addAll(operations)
        val operation = copyOperation[i]

        when {
            accumulator != 0 -> {
                return accumulator
            }
            operation.command == "jmp" -> {
                val manipulateOperation = Operation("nop", operation.value, operation.execute)
                copyOperation[i] = manipulateOperation
                accumulator = runOperations(copyOperation, false)
            }
            operation.command == "nop" -> {
                val manipulateOperation = Operation("jmp", operation.value, operation.execute)
                copyOperation[i] = manipulateOperation
                accumulator = runOperations(copyOperation, false)
            }
        }
    }
    return accumulator
}

private fun runOperations(operations: MutableList<Operation>, looping : Boolean = true): Int {
    var accumulator = 0
    var execute = 0
    var pos = 0
    while (execute != 2) {
        val operation = operations[pos]
        operation.increaseExecute()
        execute = operation.execute
        when {
            execute == 2 -> {
                return if (looping) {
                    accumulator
                } else {
                    0
                }
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
        if (pos == 0 && execute == 1) {
            println("finished correctly " + accumulator)
            return accumulator
        }
    }
    return if (looping) {
        accumulator
    } else {
        0
    }
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