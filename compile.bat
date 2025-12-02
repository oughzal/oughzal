@echo off
setlocal

echo Compilation du compilateur Algo...

rem Créer les répertoires de sortie
if not exist "build\classes" mkdir build\classes
if not exist "build\libs" mkdir build\libs

rem Trouver le compilateur Kotlin
where kotlinc >nul 2>&1
if %errorlevel% neq 0 (
    echo Erreur: kotlinc n'est pas installé ou n'est pas dans le PATH
    echo Veuillez installer Kotlin depuis https://kotlinlang.org/
    exit /b 1
)

rem Compiler les fichiers Kotlin
echo Compilation des sources...
kotlinc src\main\kotlin\com\algocompiler\Token.kt src\main\kotlin\com\algocompiler\AST.kt src\main\kotlin\com\algocompiler\Lexer.kt src\main\kotlin\com\algocompiler\Parser.kt src\main\kotlin\com\algocompiler\Interpreter.kt src\main\kotlin\com\algocompiler\Main.kt -include-runtime -d build\libs\algo-compiler.jar

if %errorlevel% neq 0 (
    echo Erreur lors de la compilation
    exit /b 1
)

echo.
echo Compilation réussie!
echo Le fichier JAR est dans: build\libs\algo-compiler.jar
echo.
echo Pour exécuter un fichier .algo:
echo java -jar build\libs\algo-compiler.jar examples\hello.algo
