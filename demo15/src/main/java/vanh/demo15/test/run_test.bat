@echo off
REM
jmeter -n -t "Summary Report.jmx" -l "results.jtl"

REM
powershell -ExecutionPolicy Bypass -File ".\calculate_timeout.ps1"

REM
timeout /t 3
start "test_report" ".\test_report.txt"