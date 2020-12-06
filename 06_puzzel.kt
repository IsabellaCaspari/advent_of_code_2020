import java.io.File
import  kotlin.math.round

const val FILE_NAME = "06_puzzel_input.txt"
var declarationFroms: MutableList<DeclarationFrom> = ArrayList()

fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    println("""Puzzel Day 5, Part 1 """)
    var result = 0
    seperateGroups()
    declarationFroms.forEach {
        result += it.questions.size
    }

    println("""The answer is $result""")
}

private fun partTwo() {
    var result = 0
    declarationFroms.forEach {
        result = result + it.filterDuplicateAnswers()
    }
    println("""Puzzel Day 5, Part 2 """)
    println("""The answer is $result""")
}

private fun seperateGroups() {
    val list = readInputFile(FILE_NAME)
    var declarationFrom = DeclarationFrom()
    for (line in list) {
        if (line == "" || line == " ") {
            declarationFroms.add(declarationFrom)
            declarationFrom = DeclarationFrom()
        } else {
            declarationFrom.increment(line)
        }
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


data class DeclarationFrom(
    var personCount: Int = 0,
    var questions: MutableList<Char> = mutableListOf<Char>(),
    var duplicateAnswers: MutableList<Char> = mutableListOf<Char>()
) {

    fun increment(line: String) {
        incrementPersonCount()
        addQuestion(line)
        addDuplicateAnswers(line)
    }

    fun addDuplicateAnswers(ques: String) {
        for (letter in ques) {
            duplicateAnswers.add(letter)
        }
    }

    fun filterDuplicateAnswers(): Int {
        var count = 0
        var listCountedLetter = mutableListOf<Char>()
        duplicateAnswers.forEach { letter ->
            if (duplicateAnswers.filter { it == letter && !listCountedLetter.contains(letter) }.size == personCount) {
                count++
            }
            listCountedLetter.add(letter)
        }
        return count
    }

    fun addQuestion(ques: String) {
        for (letter in ques) {
            questions.add(letter)
        }
        questions = questions.toSet().toMutableList()
    }

    fun incrementPersonCount() {
        personCount++
    }

    override fun toString(): String {
        println("DeclarationForm")
        println("Personcount " + personCount)
        println("Questions answerd with yes " + questions.toString())
        return ""
    }
}