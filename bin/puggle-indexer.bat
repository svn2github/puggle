REM java -Dswing.defaultlaf=com.sun.java.swing.plaf.windows.WindowsLookAndFeel -Xms2M -Xmx512M -cp ..\dist\Puggle.jar puggle.ui.IndexerFrame

@echo off

REM java -Dswing.defaultlaf=com.sun.java.swing.plaf.windows.WindowsLookAndFeel -Xms2M -Xmx512M -jar "..\dist\Puggle.jar"

REM ------------------------
REM -- Check for debug run
REM ------------------------
SET JAVA_BIN=javaw
CALL "%BIN%\debug.bat" %*
IF "%DEBUG"=="YES" SET JAVA_BIN=java


REM ------------------------
REM -- Run Puggle
REM ------------------------
START %JAVA_BIN%  -Dswing.defaultlaf=com.sun.java.swing.plaf.windows.WindowsLookAndFeel -Xms2M -Xmx512M -cp ..\dist\Puggle.jar puggle.ui.IndexerFrame  %*

if "Windows_NT"=="%OS%" endlocal

