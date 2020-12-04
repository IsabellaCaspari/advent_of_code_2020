import java.io.File
import java.util.HashMap

const val GREEN_BOLD = "\u001b[1;32m"
const val BLACK = "\u001b[0;30m"
const val FILE_NAME = "04_puzzel_input.txt"

var passports: MutableList<Passport> = ArrayList()

fun main() {
    createPassports()
    partOne()
    partTwo()
}

private fun partOne() {
    println("""${GREEN_BOLD}Puzzel Day 4, Part 1 """)

    val result = passports.filter { it.valid }.size
    println("""${BLACK}The answer is $result""")

}

private fun partTwo() {
    println("""${GREEN_BOLD}Puzzel Day 4, Part 2 """)

    val result = passports.filter { it.validWithStrictRules }.size
    println("""${BLACK}The answer is $result""")
}

private fun createPassports(){
    val list = readInputFile(FILE_NAME)
    var passportLines = ""
    for (line in list) {
        if (line == "" || line == " ") {
            val passport = extractPassport(passportLines)
            passports.add(passport)
            passportLines = ""
        } else {
            passportLines = passportLines + line + " "
        }
    }
}

private fun extractPassport(line: String): Passport {
    val array = line.split(":", " ")
    val hashMap = hashMapOf<String, String>()
    for (i in 0..array.size - 2 step 2) {
        hashMap.put(array[i], array[i + 1])
    }

    return Passport(hashMap, isValid(hashMap), isValidStricterConditions(hashMap))
}

//Ignore cid field
private fun isValid(hashMap: HashMap<String, String>) =
    hashMap.containsKey("byr") &&
            hashMap.containsKey("iyr") &&
            hashMap.containsKey("eyr") &&
            hashMap.containsKey("hgt") &&
            hashMap.containsKey("hcl") &&
            hashMap.containsKey("ecl") &&
            hashMap.containsKey("pid")

private fun isValidStricterConditions(hashMap: HashMap<String, String>) =
    inRange(hashMap["byr"], 1920, 2002) &&
            inRange(hashMap["iyr"], 2010, 2020) &&
            inRange(hashMap["eyr"], 2020, 2030) &&
            isValidHeight(hashMap["hgt"]) &&
            isValidHairColor(hashMap["hcl"]) &&
            isValidEyeColor(hashMap["ecl"]) &&
            isValidPassPortId(hashMap["pid"])

private fun isValidHeight(value: String?): Boolean {
    value?.let {
        return (value.contains("cm") && isNumber(value.removeSuffix("cm")) && inRange(
            value.removeSuffix("cm"), 150, 193)) ||
                (value.contains("in") && isNumber(value.removeSuffix("in")) &&
                        inRange(value.removeSuffix("in"), 59, 76))
    }
    return false
}

private fun isValidHairColor(input: String?) : Boolean{
    input?.let{
        val regex = "^#([a-f0-9]{6})$".toRegex()
        val match = regex.find(input)
        return  match?.value != "" && input.length == 7
    }
    return false
}

private fun isValidEyeColor(input: String?) : Boolean{
    val eyeColors  = arrayOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    input?.let{
        for(color in eyeColors){
            if (input == color){
                return true
            }
        }
    }
    return false
}

private fun isValidPassPortId(input: String?) : Boolean {
    input?.let{
        val regex = "^(d{9})$".toRegex()
        val match = regex.find(input)
        return  match?.value != "" && input.length == 9
    }
    return false
}

private fun inRange(value: String?, start: Int, end: Int): Boolean {
    value?.let {
        try {
            val number = value.toInt()
            return number >= start && number <= end

        } catch (nfe: NumberFormatException) {
            return false
        }
    }
    return false

}

private fun isNumber(s: String?): Boolean {
    return if (s.isNullOrEmpty()) false else s.all { Character.isDigit(it) }
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

data class Passport(
    val fields: HashMap<String, String>,
    var valid: Boolean = false,
    var validWithStrictRules: Boolean = false
) {
    override fun toString(): String {
        println("Passport")
        fields.forEach { (key, value) -> println("$key = $value") }
        println("This Passport is " + valid)
        return ""
    }
}

