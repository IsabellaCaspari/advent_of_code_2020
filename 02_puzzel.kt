import java.io.File

val passwords = mutableListOf<PasswordSpecification>()
const val GREEN_BOLD = "\u001b[1;32m"
const val BLACK = "\u001b[0;30m"
fun main() {
    readInputFile("02_puzzel_input.txt")
    partOne()
    partTwo()
}

private fun partOne() {
    var numberOfValidPasswords = 0
    for (spec in passwords) {
        if (isPasswordValid(spec)) {
            numberOfValidPasswords++
        }
    }

    println(GREEN_BOLD + "Puzzel Day 2, Part 1 ")
    println(BLACK + "There are " + numberOfValidPasswords + " passwords valid of " + passwords.size)
}

private fun partTwo() {
    var numberOfValidPasswords = 0
    for (spec in passwords) {
        if (isPasswordValidNewRules(spec)) {
            numberOfValidPasswords++
        }
    }

    println(GREEN_BOLD + "Puzzel Day 2, Part 2 ")
    println(BLACK + "There are " + numberOfValidPasswords + " passwords valid of " + passwords.size)
}

private fun isPasswordValid(spec: PasswordSpecification): Boolean {
    var incident = 0
    for (letter in spec.password) {
        if (letter == spec.letter) {
            incident++
        }
    }
    return incident >= spec.min && incident <= spec.max
}

private fun isPasswordValidNewRules(spec: PasswordSpecification) =
    (spec.password[spec.min - 1] == spec.letter
            && spec.password[spec.max - 1] != spec.letter) ||
            (spec.password[spec.min - 1] != spec.letter
                    && spec.password[spec.max - 1] == spec.letter)

private fun readInputFile(filename: String) {
    File(filename).forEachLine {
        try {
            val spec = PasswordSpecification()
            // Separate string in diffrent parts
            // String input: 9-10 n: nnnnnngnzxnnn
            val parts = it.split("-", " ", ": ")
            spec.min = parts[0].toInt()
            spec.max = parts[1].toInt()
            spec.letter = parts[2].single()
            spec.password = parts[3]
            passwords.add(spec)

        } catch (e: java.lang.Exception) {
            println("Thats a Cast exception, sth went wrong while casting the string")
        }
    }
}

data class PasswordSpecification(
    var min: Int = 0,
    var max: Int = 0,
    var letter: Char = 'a',
    var password: String = ""
) {
    override fun toString(): String {
        println("Min: " + min)
        println("Max: " + max)
        println("letter: " + letter)
        println("password: " + password)
        return ""
    }
}
