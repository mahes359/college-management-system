$services = @(
    @{ Name = "attendance-service"; Title = "Attendance Service"; Scope = "student attendance tracking and reporting"; Icon = "<path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-6 9l2 2 4-4`" />" },
    @{ Name = "course-service"; Title = "Course Service"; Scope = "course catalog, curriculum, and class scheduling"; Icon = "<path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253`" />" },
    @{ Name = "enrollment-service"; Title = "Enrollment Service"; Scope = "student enrollment and course registration"; Icon = "<path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z`" />" },
    @{ Name = "exam-service"; Title = "Exam Service"; Scope = "examination scheduling, grading, and results"; Icon = "<path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z`" />" },
    @{ Name = "faculty-service"; Title = "Faculty Service"; Scope = "faculty profiles, assignments, and workload"; Icon = "<path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z`" />" },
    @{ Name = "student-service"; Title = "Student Service"; Scope = "student profiles, records, and information"; Icon = "<path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M12 14l9-5-9-5-9 5 9 5z`" /><path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M12 14l6.16-3.422a12.083 12.083 0 01.665 6.479A11.952 11.952 0 0012 20.055a11.952 11.952 0 00-6.824-2.998 12.078 12.078 0 01.665-6.479L12 14z`" /><path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M12 14l9-5-9-5-9 5 9 5zm0 0l6.16-3.422a12.083 12.083 0 01.665 6.479A11.952 11.952 0 0012 20.055a11.952 11.952 0 00-6.824-2.998 12.078 12.078 0 01.665-6.479L12 14zm-4 6v-7.5l4-2.222`" />" },
    @{ Name = "api-gateway"; Title = "API Gateway"; Scope = "routing requests, load balancing, and cross-cutting concerns"; Icon = "<path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M8 9l3 3-3 3m5 0h3M5 20h14a2 2 0 002-2V6a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z`" />" },
    @{ Name = "eureka-server"; Title = "Service Registry"; Scope = "service discovery, registration, and health monitoring"; Icon = "<path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 002-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10`" />" }
)

$template = @"
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>{{TITLE}} - College Management System</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --bg-color: #0f172a;
            --text-color: #f8fafc;
            --card-bg: rgba(30, 41, 59, 0.7);
            --primary: #3b82f6;
            --primary-hover: #2563eb;
            --accent: #8b5cf6;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', sans-serif;
        }

        body {
            background-color: var(--bg-color);
            color: var(--text-color);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background-image: 
                radial-gradient(at 0% 0%, hsla(253,16%,7%,1) 0, transparent 50%), 
                radial-gradient(at 50% 0%, hsla(225,39%,30%,0.2) 0, transparent 50%), 
                radial-gradient(at 100% 0%, hsla(339,49%,30%,0.2) 0, transparent 50%);
            background-attachment: fixed;
            background-size: cover;
        }

        .container {
            width: 90%;
            max-width: 1000px;
            padding: 2rem;
            margin: 2rem auto;
        }

        header {
            text-align: center;
            margin-bottom: 3rem;
            animation: fadeInDown 1s ease-out;
        }

        h1 {
            font-size: 3.5rem;
            font-weight: 700;
            background: linear-gradient(to right, #60a5fa, #c084fc);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            margin-bottom: 1rem;
        }

        p.subtitle {
            font-size: 1.2rem;
            color: #cbd5e1;
            font-weight: 300;
        }

        .glass-card {
            background: var(--card-bg);
            backdrop-filter: blur(12px);
            -webkit-backdrop-filter: blur(12px);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 24px;
            padding: 3rem;
            box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
            animation: fadeInUp 1s ease-out 0.2s both;
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
        }

        .icon-container {
            width: 80px;
            height: 80px;
            background: linear-gradient(135deg, var(--primary), var(--accent));
            border-radius: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 2rem;
            box-shadow: 0 10px 20px rgba(0,0,0,0.3);
            transform: rotate(-10deg);
            transition: transform 0.3s ease;
        }

        .glass-card:hover .icon-container {
            transform: rotate(0deg) scale(1.1);
        }

        .icon-container svg {
            width: 40px;
            height: 40px;
            color: white;
        }

        .status {
            display: inline-flex;
            align-items: center;
            background: rgba(16, 185, 129, 0.1);
            color: #10b981;
            padding: 0.5rem 1rem;
            border-radius: 9999px;
            font-weight: 600;
            font-size: 0.9rem;
            margin-bottom: 2rem;
            border: 1px solid rgba(16, 185, 129, 0.2);
        }

        .status-dot {
            width: 8px;
            height: 8px;
            background-color: #10b981;
            border-radius: 50%;
            margin-right: 8px;
            box-shadow: 0 0 10px #10b981;
            animation: pulse 2s infinite;
        }

        .btn-primary {
            background: linear-gradient(135deg, var(--primary), var(--accent));
            color: white;
            border: none;
            padding: 1rem 2rem;
            font-size: 1.1rem;
            font-weight: 600;
            border-radius: 12px;
            cursor: pointer;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(59, 130, 246, 0.4);
            text-decoration: none;
            display: inline-block;
        }

        .btn-primary:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 25px rgba(59, 130, 246, 0.6);
        }

        @keyframes fadeInDown {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @keyframes fadeInUp {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @keyframes pulse {
            0% { transform: scale(0.95); box-shadow: 0 0 0 0 rgba(16, 185, 129, 0.7); }
            70% { transform: scale(1); box-shadow: 0 0 0 10px rgba(16, 185, 129, 0); }
            100% { transform: scale(0.95); box-shadow: 0 0 0 0 rgba(16, 185, 129, 0); }
        }
        
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1.5rem;
            width: 100%;
            margin-top: 2.5rem;
        }

        .metric-card {
            background: rgba(255, 255, 255, 0.03);
            border: 1px solid rgba(255, 255, 255, 0.05);
            padding: 1.5rem;
            border-radius: 16px;
            text-align: left;
            transition: all 0.3s ease;
        }

        .metric-card:hover {
            background: rgba(255, 255, 255, 0.05);
            transform: translateY(-2px);
            border-color: rgba(255, 255, 255, 0.1);
        }

        .metric-title {
            font-size: 0.9rem;
            color: #94a3b8;
            margin-bottom: 0.5rem;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }

        .metric-value {
            font-size: 2rem;
            font-weight: 700;
            color: white;
            display: flex;
            align-items: center;
        }

        .metric-indicator {
            font-size: 1rem;
            margin-left: 0.5rem;
            color: #4ade80;
        }

    </style>
</head>
<body>
    <div class="container">
        <header>
            <h1>{{TITLE}}</h1>
            <p class="subtitle">College Management System</p>
        </header>

        <div class="glass-card">
            <div class="icon-container">
                <svg fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    {{ICON}}
                </svg>
            </div>
            
            <div class="status">
                <span class="status-dot"></span> Service is online and operational
            </div>
            
            <p style="color: #cbd5e1; margin-bottom: 2.5rem; max-width: 600px; line-height: 1.7; font-size: 1.1rem;">
                Welcome to the <strong>{{TITLE}}</strong>. This microservice is responsible for handling all operations related to {{SCOPE}} within the distributed architecture.
            </p>

            <a href="/swagger-ui.html" class="btn-primary">View API Documentation</a>

            <div class="dashboard-grid">
                <div class="metric-card">
                    <div class="metric-title">System Status</div>
                    <div class="metric-value" style="color: #4ade80;">Healthy</div>
                </div>
                <div class="metric-card">
                    <div class="metric-title">Uptime</div>
                    <div class="metric-value">99.99%</div>
                </div>
                <div class="metric-card">
                    <div class="metric-title">Active Connections</div>
                    <div class="metric-value">24<span class="metric-indicator">↑</span></div>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        document.addEventListener('mousemove', (e) => {
            const cards = document.querySelectorAll('.glass-card');
            cards.forEach(card => {
                const rect = card.getBoundingClientRect();
                const x = e.clientX - rect.left;
                const y = e.clientY - rect.top;
                card.style.setProperty('--mouse-x', `${x}px`);
                card.style.setProperty('--mouse-y', `${y}px`);
            });
        });
    </script>
</body>
</html>
"@

foreach ($service in $services) {
    $dir = "$($PWD.Path)\$($service.Name)\src\main\resources\static"
    if (!(Test-Path $dir)) {
        New-Item -ItemType Directory -Force -Path $dir | Out-Null
    }
    
    $fileContent = $template -replace '\{\{TITLE\}\}', $service.Title `
                             -replace '\{\{SCOPE\}\}', $service.Scope `
                             -replace '\{\{ICON\}\}', $service.Icon
                             
    $filePath = "$dir\index.html"
    Set-Content -Path $filePath -Value $fileContent -Encoding UTF8
    Write-Host "Created UI for $($service.Name) at $filePath"
}
