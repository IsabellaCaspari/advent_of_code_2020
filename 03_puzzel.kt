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
    println(GREEN_BOLD + "Puzzel Day 3, Part 1 ")
}

private fun partTwo() {
    println(GREEN_BOLD + "Puzzel Day 3, Part 2 ")
    println(BLACK + "There are " + numberOfValidPasswords + " passwords valid of " + passwords.size)
}

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

