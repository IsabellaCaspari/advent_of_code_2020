import java.io.File

val coins = mutableListOf<Int>()
fun main() {
    readInputFile("01_puzzel_input.txt")

    println("Puzzel 1, Part 1")
    for (i in coins.indices) {
        for (j in i + 1 until coins.size) {
            if (coins[i] + coins[j] == 2020) {
                println(coins[i] * coins[j])
            }
        }
    }

    println("Puzzel 1, Part 2")
    for (i in coins.indices) {
        for (j in i + 1 until coins.size) {
            for (x in j + 1 until coins.size) {
                if (coins[i] + coins[j] + coins[x] == 2020) {
                    println(coins[i] * coins[j] * coins[x])
                }
            }
        }
    }
}

private fun readInputFile(filename: String) {
    File(filename).forEachLine {
        try {
            val number = it.toInt()
            coins.add(number)

        } catch (nfe: NumberFormatException) {
            println("Thats not a Integer, please check the inputfile")
        }
    }
}
