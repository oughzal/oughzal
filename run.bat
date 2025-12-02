@echo off
if "%~1"=="" (
    echo Usage: run.bat fichier.algo
    exit /b 1
)

java -jar build\libs\algo-compiler.jar %1
