import java.io.File

const val GREEN_BOLD = "\u001b[1;32m"
const val BLACK = "\u001b[0;30m"

fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    println("""${GREEN_BOLD}Puzzel Day 3, Part 1 """)

    //Right 3, down 1.
    val result = findWaySlope(3, 1)

    println("""${BLACK}Thats the answer $result""")

}

private fun partTwo() {
    println(GREEN_BOLD + "Puzzel Day 3, Part 2 ")

    /*Right 1, down 1.
      Right 3, down 1.
      Right 5, down 1.
      Right 7, down 1.
      Right 1, down 2.*/

    val result: Long = findWaySlope(1, 1).toLong() *
            findWaySlope(3, 1).toLong() *
            findWaySlope(5, 1).toLong() *
            findWaySlope(7, 1).toLong() *
            findWaySlope(1, 2).toLong()

    println("""${BLACK}Thats the answer $result""")
}


private fun findWaySlope(right: Int, down: Int): Int {
    var map = readInputFile("03_puzzel_input.txt")
    var lines = 0
    var columns = 0
    var numbersOfTree = 0

    while (lines <= map.size - 1) {
        if (map[lines][columns] == '#') {
            map[lines][columns] = '0'
            numbersOfTree++
        } else {
            map[lines][columns] = 'X'
        }
        lines = lines + down
        columns = columns + right
    }
    return numbersOfTree
}


private fun readInputFile(filename: String): MutableList<MutableList<Char>> {
    var map: MutableList<MutableList<Char>> = ArrayList()
    File(filename).forEachLine {
        try {
            // squares (.) and trees (#)
            //..##.........##.........##.........##.........##.........##.......
            // multiply pattern X times
            var linepattern = ""
            for (i in 0..100) {
                linepattern += it
            }
            map.add(linepattern.toCharArray().toMutableList())
        } catch (e: java.lang.Exception) {
            println(e)
        }
    }
    return map
}

