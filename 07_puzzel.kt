import java.io.File
import  kotlin.math.round

const val FILE_NAME = "07_puzzel_input.txt"
val colorList: MutableList<String> = mutableListOf<String>()
var bags: List<Bag> = emptyList()

fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    println("""Puzzel Day 7, Part 1 """)
    bags = readListOfBags()
    val result = countBags()
    println("""The answer is $result""")
}

private fun partTwo() {
    println("""Puzzel Day 7, Part 2 """)
    bags = bags.toSet().toMutableList()
    val result = countContainedBags()
    println("""The answer is $result""")
}

private fun countBags(): Int {
    var count = checkColor("shinygold")
    return colorList.toSet().count()
}

private fun checkColor(color: String) {
    bags.forEach { bag ->
        bag.containBags.forEach { containsColor ->
            if (containsColor.first == color) {
                colorList.add(bag.color)
                checkColor(bag.color)
            }
        }
    }
}

private fun countContainedBags() = countNumberContainingBags("shinygold") - 1

private fun countNumberContainingBags(color: String): Int {

    if (color == "noColor") {
        return 0
    }

    var numberOfBags = 0
    var bag = bags.first { it.color == color }

    bag?.let {
        bag.containBags.forEach { containedBag ->
            numberOfBags += (countNumberContainingBags(containedBag.first) * containedBag.second)
        }
    }
    return numberOfBags +1
}

private fun readListOfBags(): List<Bag> {
    val list = readInputFile(FILE_NAME)
    val bags: MutableList<Bag> = mutableListOf<Bag>()
    list.forEach { it ->
        val bag = extractBag(it)
        println(bag)
        bags.add(bag)
    }
    return bags
}

private fun extractBag(line: String): Bag {
    var bagline = line.replace("bags contain ", "")
    bagline = bagline.replace("bags", "")
    bagline = bagline.replace("bag", "")
    bagline = bagline.replace(", ", "")
    bagline = bagline.replace(".", "")
    val array = bagline.split(" ")

    val bag = Bag(color = array[0] + array[1])

    for (i in 2..array.size - 2 step 3) {
        if (array[i] != "no") {
            bag.containBags.add(Pair(array[i + 1].plus(array[i + 2]), array[i].toInt()))
        } else {
            bag.containBags.add(Pair("noColor", 1))
        }
    }

    return bag
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

data class Bag(
    val color: String,
    val containBags: MutableList<Pair<String, Int>> = mutableListOf<Pair<String, Int>>()
) {
    override fun toString(): String {
        println("The " + color + "bag")
        containBags.forEach {
            println(it.first + " " + it.second)
        }
        return ""
    }
}

