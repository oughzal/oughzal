package com.algocompiler

import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: java -jar algoc.jar <fichier.algo>")
        exitProcess(1)
    }

    val fileName = args[0]
    val file = File(fileName)

    if (!file.exists()) {
        println("Erreur: Le fichier '$fileName' n'existe pas")
        exitProcess(1)
    }

    if (!file.name.endsWith(".algo")) {
        println("Erreur: Le fichier doit avoir l'extension .algo")
        exitProcess(1)
    }

    try {
        val code = file.readText()
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val ast = parser.parse()
        val interpreter = Interpreter()
        interpreter.execute(ast)
    } catch (e: Exception) {
        println("Erreur: ${e.message}")
        exitProcess(1)
    }
}
