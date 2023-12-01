package nl.rwslinkman.adventofcode2023

enum class AnsiColor(val colorCode: String) {
    Default("\u001b[0m"),
    Black("\u001b[30m"),
    Yellow("\u001b[33m"),
    Red("\u001b[31m"),
    Green("\u001b[32m"),
    Blue("\u001b[34m"),
    Purple("\u001b[35m"),
    Cyan("\u001b[36m"),
    White("\u001b[37m"),
}

