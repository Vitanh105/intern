$logPath = "D:\Project\intern\demo9\src\main\java\vanh\demo9\logs\gift-service.log"
$outputPath = "D:\Project\intern\demo9\src\main\java\vanh\demo9\test\test_report.txt"

if (Test-Path $logPath) {
    $logContent = Get-Content $logPath
    $success = ($logContent | Select-String -Pattern "SUCCESS").Count
    $fail = ($logContent | Select-String -Pattern "FAIL").Count
    $reject = ($logContent | Select-String -Pattern "REJECT").Count
    $total = $success + $fail + $reject

    if ($total -gt 0) {
        $timeoutRate = [math]::Round(($fail / $total) * 100, 2)
        $rejectRate = [math]::Round(($reject / $total) * 100, 2)
        "=== Ket Qua ===" | Out-File $outputPath
        "Tong request: $total" | Out-File $outputPath -Append
        "Thanh cong: $success" | Out-File $outputPath -Append
        "Timeout: $fail" | Out-File $outputPath -Append
        "Tu choi do het qua: $reject" | Out-File $outputPath -Append
        "Ti le timeout: $timeoutRate%" | Out-File $outputPath -Append
        "Ti le tu choi: $rejectRate%" | Out-File $outputPath -Append
    }
} else {
    "File log khong ton tai" | Out-File $outputPath
}