# Load environment variables from .env file
if (Test-Path .env) {
    Get-Content .env | ForEach-Object {
        $line = $_.Trim()
        if ($line -ne "" -and -not $line.StartsWith("#")) {
            $parts = $line.Split("=", 2)
            if ($parts.Length -eq 2) {
                $key = $parts[0].Trim()
                $val = $parts[1].Trim()
                [System.Environment]::SetEnvironmentVariable($key, $val, "Process")
                Set-Item -Path ("env:" + $key) -Value $val
            }
        }
    }
    Write-Host "Loaded environment variables from .env" -ForegroundColor Green
}

mvn spring-boot:run
