# PowerShell script to complete backend/frontend restructure
# Run this from the ease-travel-microservices root folder

$root = $PSScriptRoot
if (-not $root) { $root = Get-Location }

Write-Host "Moving remaining services to backend folder..." -ForegroundColor Cyan

# Services to move
$services = @("trip-service", "booking-service", "payment-service", "notification-service")

foreach ($service in $services) {
    $source = Join-Path $root $service
    $dest = Join-Path $root "backend"

    if (Test-Path $source) {
        Write-Host "  Moving $service..." -ForegroundColor Yellow
        Copy-Item -Path $source -Destination $dest -Recurse -Force
        Write-Host "  Copied $service to backend/" -ForegroundColor Green
    } else {
        Write-Host "  $service not found at root, skipping..." -ForegroundColor Gray
    }
}

Write-Host ""
Write-Host "Done! Services moved to backend/" -ForegroundColor Green
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Cyan
Write-Host "  1. Verify backend/ contains all 7 services"
Write-Host "  2. Delete old service folders from root (optional):"
Write-Host "     Remove-Item -Recurse -Force trip-service, booking-service, payment-service, notification-service"
Write-Host "  3. Delete old root files (optional):"
Write-Host "     Remove-Item pom.xml, docker-compose.yml, Dockerfile -ErrorAction SilentlyContinue"
Write-Host "  4. Test build:"
Write-Host "     cd backend; mvn clean install -DskipTests"

