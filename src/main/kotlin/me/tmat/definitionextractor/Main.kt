package me.tmat.definitionextractor

import org.jetbrains.kotlin.spec.grammar.tools.KotlinLexerException
import org.jetbrains.kotlin.spec.grammar.tools.KotlinParserException
import org.jetbrains.kotlin.spec.grammar.tools.parseKotlinCode
import java.io.File
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    if (args.size != 1) {
        printError("You should run the program with the input filename as an argument")
        exitProcess(1)
    }

    val filepath = args[0];
    val inputCode = File(filepath).bufferedReader().use { it.readText() }

    val parseTree = try {
        parseKotlinCode(inputCode)
    } catch (e: KotlinLexerException) {
        printError("Tokenization failed :(")
        exitProcess(1)
    } catch (e: KotlinParserException) {
        printError("Parsing the code failed :(")
        exitProcess(1)
    }

    println(extract(parseTree))
}


fun printError(message: String) {
    System.err.println(message)
}
