Write-Host "Starting Maven compile test..." -ForegroundColor Green
Set-Location "C:\kalsi\code\git\migration-suit\practice-projects\insurance-company-MANUAL"
Write-Host "Current directory: $(Get-Location)" -ForegroundColor Yellow

# Try with Maven wrapper first
Write-Host "Attempting compilation with Maven wrapper..." -ForegroundColor Blue
& .\mvnw.cmd clean compile -e

Write-Host "Maven compile completed with exit code: $LASTEXITCODE" -ForegroundColor Cyan

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation successful!" -ForegroundColor Green
} else {
    Write-Host "Compilation failed with exit code: $LASTEXITCODE" -ForegroundColor Red
}

Read-Host "Press Enter to continue..."
