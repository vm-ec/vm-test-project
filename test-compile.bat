@echo off
echo Starting Maven compile test...
cd /d "C:\kalsi\code\git\migration-suit\practice-projects\insurance-company-MANUAL"
echo Current directory: %CD%
mvn clean compile -e
echo Maven compile completed with exit code: %ERRORLEVEL%
pause

