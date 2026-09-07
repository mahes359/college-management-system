$exceptions = Get-ChildItem -Path "e:\CODING\CLOUD COMPUTING\WEBSERVICE" -Recurse -Filter "*Exception.java"

foreach ($file in $exceptions) {
    $content = Get-Content $file.FullName -Raw
    
    if ($content -notmatch "org.springframework.ws.soap.server.endpoint.annotation.SoapFault") {
        
        $faultCode = "SERVER"
        if ($file.Name -match "NotFound|Validation|Duplicate") {
            $faultCode = "CLIENT"
        }
        
        $newContent = $content -replace "public class", "import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;`nimport org.springframework.ws.soap.server.endpoint.annotation.FaultCode;`n`n@SoapFault(faultCode = FaultCode.$faultCode)`npublic class"
        
        Set-Content -Path $file.FullName -Value $newContent
        Write-Host "Updated $($file.Name) with FaultCode.$faultCode"
    } else {
        Write-Host "Skipped $($file.Name), already annotated."
    }
}
