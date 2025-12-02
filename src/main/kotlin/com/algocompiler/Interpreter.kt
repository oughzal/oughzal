package com.algocompiler

import kotlin.math.*

class ReturnException(val value: Any?) : Exception()

class Interpreter {
    private val variables = mutableMapOf<String, Any>()
    private val constants = mutableSetOf<String>()
    private val functions = mutableMapOf<String, FunctionDeclaration>()

    // Normalisation des noms (case insensitive)
    private fun normalize(name: String): String {
        return name.lowercase()
                .replace("é", "e")
                .replace("è", "e")
                .replace("ê", "e")
                .replace("à", "a")
                .replace("â", "a")
                .replace("î", "i")
                .replace("ô", "o")
                .replace("ù", "u")
                .replace("û", "u")
                .replace("ç", "c")
    }

    fun execute(program: Program) {
        // Store functions
        for (func in program.functions) {
            functions[normalize(func.name)] = func
        }

        // Initialize variables
        for (varDecl in program.variables) {
            initializeVariable(varDecl)
        }

        // Initialize constants
        for (constDecl in program.constants) {
            initializeConstant(constDecl)
        }

        // Execute statements
        for (statement in program.statements) {
            executeStatement(statement)
        }
    }

    private fun initializeVariable(varDecl: VariableDeclaration) {
        val normalizedName = normalize(varDecl.name)
        if (varDecl.arraySize != null) {
            // Initialize array
            val defaultValue =
                    when (normalize(varDecl.type)) {
                        "entier" -> 0
                        "reel" -> 0.0
                        "chaine" -> ""
                        "booleen" -> false
                        else -> 0
                    }
            variables[normalizedName] = MutableList(varDecl.arraySize) { defaultValue }
        } else {
            // Initialize simple variable
            variables[normalizedName] =
                    when (normalize(varDecl.type)) {
                        "entier" -> 0
                        "reel" -> 0.0
                        "chaine" -> ""
                        "booleen" -> false
                        else -> 0
                    }
        }
    }

    private fun initializeConstant(constDecl: ConstantDeclaration) {
        val normalizedName = normalize(constDecl.name)
        val value = evaluateExpression(constDecl.value)
        variables[normalizedName] = value
        constants.add(normalizedName)
    }

    private fun executeStatement(statement: Statement) {
        when (statement) {
            is Assignment -> executeAssignment(statement)
            is ArrayAssignment -> executeArrayAssignment(statement)
            is IfStatement -> executeIfStatement(statement)
            is ForLoop -> executeForLoop(statement)
            is WhileLoop -> executeWhileLoop(statement)
            is RepeatUntilLoop -> executeRepeatUntilLoop(statement)
            is WriteStatement -> executeWriteStatement(statement)
            is ReadStatement -> executeReadStatement(statement)
            is ReturnStatement ->
                    throw ReturnException(
                            if (statement.expression != null)
                                    evaluateExpression(statement.expression)
                            else null
                    )
            is FunctionCall -> executeFunctionCall(statement.name, statement.arguments)
        }
    }

    private fun executeAssignment(assignment: Assignment) {
        val normalizedName = normalize(assignment.variable)
        if (constants.contains(normalizedName)) {
            throw Exception("Impossible de modifier la constante '${assignment.variable}'")
        }
        val value = evaluateExpression(assignment.expression)
        variables[normalizedName] = value
    }

    private fun executeArrayAssignment(assignment: ArrayAssignment) {
        val value = evaluateExpression(assignment.expression)
        val index = toInt(evaluateExpression(assignment.index))
        val normalizedName = normalize(assignment.arrayName)

        @Suppress("UNCHECKED_CAST")
        val array =
                variables[normalizedName] as? MutableList<Any>
                        ?: throw Exception(
                                "Variable '${assignment.arrayName}' n'est pas un tableau"
                        )

        if (index < 0 || index >= array.size) {
            throw Exception("Index $index hors limites pour le tableau '${assignment.arrayName}'")
        }

        array[index] = value
    }

    private fun executeIfStatement(ifStatement: IfStatement) {
        val condition = evaluateExpression(ifStatement.condition)

        if (toBoolean(condition)) {
            for (statement in ifStatement.thenBranch) {
                executeStatement(statement)
            }
        } else if (ifStatement.elseBranch != null) {
            for (statement in ifStatement.elseBranch) {
                executeStatement(statement)
            }
        }
    }

    private fun executeForLoop(forLoop: ForLoop) {
        val start = evaluateExpression(forLoop.start)
        val end = evaluateExpression(forLoop.end)

        val startInt = toInt(start)
        val endInt = toInt(end)

        for (i in startInt..endInt) {
            variables[normalize(forLoop.variable)] = i
            for (statement in forLoop.body) {
                executeStatement(statement)
            }
        }
    }

    private fun executeWhileLoop(whileLoop: WhileLoop) {
        while (toBoolean(evaluateExpression(whileLoop.condition))) {
            for (statement in whileLoop.body) {
                executeStatement(statement)
            }
        }
    }

    private fun executeRepeatUntilLoop(repeatLoop: RepeatUntilLoop) {
        do {
            for (statement in repeatLoop.body) {
                executeStatement(statement)
            }
        } while (!toBoolean(evaluateExpression(repeatLoop.condition)))
    }

    private fun executeWriteStatement(writeStatement: WriteStatement) {
        val values = writeStatement.expressions.map { evaluateExpression(it) }
        val output = values.joinToString("") { formatValue(it) }
        if (writeStatement.newline) {
            println(output)
        } else {
            print(output)
        }
    }

    private fun executeReadStatement(readStatement: ReadStatement) {
        for (target in readStatement.targets) {
            when (target) {
                is SimpleReadTarget -> {
                    print("${target.name}: ")
                    val input = readLine() ?: ""
                    val value = input.toDoubleOrNull() ?: input
                    variables[normalize(target.name)] = value
                }
                is ArrayReadTarget -> {
                    val index = toInt(evaluateExpression(target.index))
                    val normalizedName = normalize(target.arrayName)
                    print("${target.arrayName}[$index]: ")
                    val input = readLine() ?: ""
                    val value = input.toDoubleOrNull() ?: input

                    @Suppress("UNCHECKED_CAST")
                    val array =
                            variables[normalizedName] as? MutableList<Any>
                                    ?: throw Exception(
                                            "Variable '${target.arrayName}' n'est pas un tableau"
                                    )

                    if (index < 0 || index >= array.size) {
                        throw Exception(
                                "Index $index hors limites pour le tableau '${target.arrayName}'"
                        )
                    }

                    array[index] = value
                }
            }
        }
    }

    private fun executeFunctionCall(name: String, arguments: List<Expression>): Any? {
        // Check if it's a built-in function
        val normalizedName = normalize(name)
        val builtInResult = tryExecuteBuiltInFunction(normalizedName, arguments)
        if (builtInResult != null || isBuiltInFunction(normalizedName)) {
            return builtInResult
        }

        val function = functions[normalizedName] ?: throw Exception("Fonction '$name' non définie")

        // Save current variable context
        val savedVariables = variables.toMap()

        // Initialize function parameters
        for ((i, param) in function.parameters.withIndex()) {
            if (i < arguments.size) {
                val argValue = evaluateExpression(arguments[i])
                if (param.arraySize != null) {
                    variables[normalize(param.name)] = argValue
                } else {
                    variables[normalize(param.name)] = argValue
                }
            } else {
                initializeVariable(param)
            }
        }

        // Initialize function local variables
        for (varDecl in function.variables) {
            initializeVariable(varDecl)
        }

        // Execute function body
        var returnValue: Any? = null
        try {
            for (statement in function.body) {
                executeStatement(statement)
            }
        } catch (e: ReturnException) {
            returnValue = e.value
        }

        // Restore variable context
        variables.clear()
        variables.putAll(savedVariables)

        return returnValue
    }

    private fun isBuiltInFunction(name: String): Boolean {
        return name.lowercase() in
                listOf(
                        "abs",
                        "racine",
                        "puissance",
                        "arrondi",
                        "plancher",
                        "plafond",
                        "longueur",
                        "majuscule",
                        "minuscule",
                        "sousChaine",
                        "souschaine",
                        "aleatoire",
                        "sin",
                        "cos",
                        "tan",
                        "log",
                        "exp"
                )
    }

    private fun tryExecuteBuiltInFunction(name: String, arguments: List<Expression>): Any? {
        val nameLower = name.lowercase()
        val args = arguments.map { evaluateExpression(it) }

        return when (nameLower) {
            // Fonctions mathématiques
            "abs" -> {
                if (args.isEmpty()) throw Exception("abs() nécessite 1 argument")
                abs(toDouble(args[0]))
            }
            "racine" -> {
                if (args.isEmpty()) throw Exception("racine() nécessite 1 argument")
                sqrt(toDouble(args[0]))
            }
            "puissance" -> {
                if (args.size < 2) throw Exception("puissance() nécessite 2 arguments")
                toDouble(args[0]).pow(toDouble(args[1]))
            }
            "arrondi" -> {
                if (args.isEmpty()) throw Exception("arrondi() nécessite 1 argument")
                round(toDouble(args[0])).toInt()
            }
            "plancher" -> {
                if (args.isEmpty()) throw Exception("plancher() nécessite 1 argument")
                floor(toDouble(args[0])).toInt()
            }
            "plafond" -> {
                if (args.isEmpty()) throw Exception("plafond() nécessite 1 argument")
                ceil(toDouble(args[0])).toInt()
            }
            "sin" -> {
                if (args.isEmpty()) throw Exception("sin() nécessite 1 argument")
                sin(toDouble(args[0]))
            }
            "cos" -> {
                if (args.isEmpty()) throw Exception("cos() nécessite 1 argument")
                cos(toDouble(args[0]))
            }
            "tan" -> {
                if (args.isEmpty()) throw Exception("tan() nécessite 1 argument")
                tan(toDouble(args[0]))
            }
            "log" -> {
                if (args.isEmpty()) throw Exception("log() nécessite 1 argument")
                ln(toDouble(args[0]))
            }
            "exp" -> {
                if (args.isEmpty()) throw Exception("exp() nécessite 1 argument")
                exp(toDouble(args[0]))
            }

            // Fonctions de chaînes
            "longueur" -> {
                if (args.isEmpty()) throw Exception("longueur() nécessite 1 argument")
                args[0].toString().length
            }
            "majuscule" -> {
                if (args.isEmpty()) throw Exception("majuscule() nécessite 1 argument")
                args[0].toString().uppercase()
            }
            "minuscule" -> {
                if (args.isEmpty()) throw Exception("minuscule() nécessite 1 argument")
                args[0].toString().lowercase()
            }
            "souschaine", "sousChaine" -> {
                if (args.size < 3)
                        throw Exception("sousChaine() nécessite 3 arguments (chaine, debut, fin)")
                val str = args[0].toString()
                val debut = toInt(args[1])
                val fin = toInt(args[2])
                if (debut < 0 || fin > str.length || debut > fin) {
                    throw Exception("Indices invalides pour sousChaine")
                }
                str.substring(debut, fin)
            }

            // Fonction aléatoire
            "aleatoire" -> {
                if (args.isEmpty()) {
                    // Retourne un nombre entre 0.0 et 1.0
                    kotlin.random.Random.nextDouble()
                } else if (args.size == 1) {
                    // Retourne un entier entre 0 et max-1
                    kotlin.random.Random.nextInt(toInt(args[0]))
                } else {
                    // Retourne un entier entre min et max-1
                    val min = toInt(args[0])
                    val max = toInt(args[1])
                    kotlin.random.Random.nextInt(min, max)
                }
            }
            else -> null
        }
    }

    private fun evaluateExpression(expression: Expression): Any {
        return when (expression) {
            is NumberLiteral -> expression.value
            is StringLiteral -> expression.value
            is BooleanLiteral -> expression.value
            is Variable -> variables[normalize(expression.name)]
                            ?: throw Exception("Variable '${expression.name}' non définie")
            is ArrayAccess -> {
                val normalizedName = normalize(expression.name)
                @Suppress("UNCHECKED_CAST")
                val array =
                        variables[normalizedName] as? List<Any>
                                ?: throw Exception(
                                        "Variable '${expression.name}' n'est pas un tableau"
                                )
                val index = toInt(evaluateExpression(expression.index))
                if (index < 0 || index >= array.size) {
                    throw Exception(
                            "Index $index hors limites pour le tableau '${expression.name}'"
                    )
                }
                array[index]
            }
            is FunctionCallExpression -> {
                executeFunctionCall(expression.name, expression.arguments)
                        ?: throw Exception(
                                "La fonction '${expression.name}' ne retourne pas de valeur"
                        )
            }
            is BinaryOp -> evaluateBinaryOp(expression)
            is UnaryOp -> evaluateUnaryOp(expression)
        }
    }

    private fun evaluateBinaryOp(binaryOp: BinaryOp): Any {
        val left = evaluateExpression(binaryOp.left)
        val right = evaluateExpression(binaryOp.right)

        return when (binaryOp.operator) {
            "+" -> {
                if (left is String || right is String) {
                    left.toString() + right.toString()
                } else {
                    toDouble(left) + toDouble(right)
                }
            }
            "-" -> toDouble(left) - toDouble(right)
            "*" -> toDouble(left) * toDouble(right)
            "/" -> toDouble(left) / toDouble(right)
            "div" -> toInt(left) / toInt(right)
            "mod", "%" -> toInt(left) % toInt(right)
            "==" -> left == right || toDouble(left) == toDouble(right)
            "<>" -> left != right && toDouble(left) != toDouble(right)
            "!=" -> left != right && toDouble(left) != toDouble(right)
            "<" -> toDouble(left) < toDouble(right)
            ">" -> toDouble(left) > toDouble(right)
            "<=" -> toDouble(left) <= toDouble(right)
            ">=" -> toDouble(left) >= toDouble(right)
            "et" -> toBoolean(left) && toBoolean(right)
            "ou" -> toBoolean(left) || toBoolean(right)
            else -> throw Exception("Opérateur invalide: ${binaryOp.operator}")
        }
    }

    private fun evaluateUnaryOp(unaryOp: UnaryOp): Any {
        val operand = evaluateExpression(unaryOp.operand)

        return when (unaryOp.operator) {
            "-" -> -toDouble(operand)
            "non" -> !toBoolean(operand)
            else -> throw Exception("Opérateur unaire invalide: ${unaryOp.operator}")
        }
    }

    private fun toDouble(value: Any): Double {
        return when (value) {
            is Double -> value
            is Int -> value.toDouble()
            is String -> value.toDoubleOrNull() ?: 0.0
            is Boolean -> if (value) 1.0 else 0.0
            else -> 0.0
        }
    }

    private fun toInt(value: Any): Int {
        return when (value) {
            is Int -> value
            is Double -> value.toInt()
            is String -> value.toIntOrNull() ?: 0
            is Boolean -> if (value) 1 else 0
            else -> 0
        }
    }

    private fun toBoolean(value: Any): Boolean {
        return when (value) {
            is Boolean -> value
            is Double -> value != 0.0
            is Int -> value != 0
            is String -> value.isNotEmpty()
            else -> false
        }
    }

    private fun formatValue(value: Any): String {
        return when (value) {
            is Double -> {
                if (value % 1.0 == 0.0) {
                    value.toInt().toString()
                } else {
                    value.toString()
                }
            }
            else -> value.toString()
        }
    }
}
