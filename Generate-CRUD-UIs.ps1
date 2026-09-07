$services = @(
    @{
        Name = "student-service"
        Title = "Student Service"
        Namespace = "http://college.com/student"
        Entity = "Student"
        PluralEntity = "Students"
        EntityCamel = "student"
        PluralCamel = "students"
        GetAllRequest = "getAllStudentsRequest"
        Icon = "<path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M12 14l9-5-9-5-9 5 9 5z`" /><path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M12 14l6.16-3.422a12.083 12.083 0 01.665 6.479A11.952 11.952 0 0012 20.055a11.952 11.952 0 00-6.824-2.998 12.078 12.078 0 01.665-6.479L12 14z`" /><path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M12 14l9-5-9-5-9 5 9 5zm0 0l6.16-3.422a12.083 12.083 0 01.665 6.479A11.952 11.952 0 0012 20.055a11.952 11.952 0 00-6.824-2.998 12.078 12.078 0 01.665-6.479L12 14zm-4 6v-7.5l4-2.222`" />"
        Fields = '[{name: "studentNumber", label: "Student Number", type: "text"}, {name: "firstName", label: "First Name", type: "text"}, {name: "lastName", label: "Last Name", type: "text"}, {name: "email", label: "Email", type: "email"}, {name: "phone", label: "Phone", type: "text"}, {name: "department", label: "Department", type: "text"}, {name: "year", label: "Year", type: "number"}]'
    },
    @{
        Name = "course-service"
        Title = "Course Service"
        Namespace = "http://college.com/course"
        Entity = "Course"
        PluralEntity = "Courses"
        EntityCamel = "course"
        PluralCamel = "courses"
        GetAllRequest = "getAllCoursesRequest"
        Icon = "<path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253`" />"
        Fields = '[{name: "courseCode", label: "Course Code", type: "text"}, {name: "courseName", label: "Course Name", type: "text"}, {name: "description", label: "Description", type: "text"}, {name: "department", label: "Department", type: "text"}, {name: "credits", label: "Credits", type: "number"}, {name: "semester", label: "Semester", type: "number"}]'
    },
    @{
        Name = "attendance-service"
        Title = "Attendance Service"
        Namespace = "http://college.com/attendance"
        Entity = "Attendance"
        PluralEntity = "Attendance"
        EntityCamel = "attendance"
        PluralCamel = "attendance"
        GetAllRequest = "getAllAttendanceRequest"
        Icon = "<path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-6 9l2 2 4-4`" />"
        Fields = '[{name: "attendanceCode", label: "Attendance Code", type: "text"}, {name: "studentId", label: "Student ID", type: "number"}, {name: "courseId", label: "Course ID", type: "number"}, {name: "attendanceDate", label: "Date (YYYY-MM-DD)", type: "date"}, {name: "status", label: "Status (Present/Absent)", type: "text"}]'
    },
    @{
        Name = "enrollment-service"
        Title = "Enrollment Service"
        Namespace = "http://college.com/enrollment"
        Entity = "Enrollment"
        PluralEntity = "Enrollments"
        EntityCamel = "enrollment"
        PluralCamel = "enrollments"
        GetAllRequest = "getAllEnrollmentsRequest"
        Icon = "<path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z`" />"
        Fields = '[{name: "enrollmentCode", label: "Enrollment Code", type: "text"}, {name: "studentId", label: "Student ID", type: "number"}, {name: "courseId", label: "Course ID", type: "number"}, {name: "semester", label: "Semester", type: "number"}, {name: "status", label: "Status", type: "text"}]'
    },
    @{
        Name = "exam-service"
        Title = "Exam Service"
        Namespace = "http://college.com/exam"
        Entity = "Exam"
        PluralEntity = "Exams"
        EntityCamel = "exam"
        PluralCamel = "exams"
        GetAllRequest = "getAllExamsRequest"
        Icon = "<path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z`" />"
        Fields = '[{name: "examCode", label: "Exam Code", type: "text"}, {name: "courseId", label: "Course ID", type: "number"}, {name: "examType", label: "Exam Type", type: "text"}, {name: "examDate", label: "Exam Date", type: "date"}, {name: "semester", label: "Semester", type: "number"}, {name: "totalMarks", label: "Total Marks", type: "number"}]'
    },
    @{
        Name = "faculty-service"
        Title = "Faculty Service"
        Namespace = "http://college.com/faculty"
        Entity = "Faculty"
        PluralEntity = "Faculty"
        EntityCamel = "faculty"
        PluralCamel = "faculty"
        GetAllRequest = "getAllFacultyRequest"
        Icon = "<path stroke-linecap=`"round`" stroke-linejoin=`"round`" stroke-width=`"2`" d=`"M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z`" />"
        Fields = '[{name: "employeeNumber", label: "Employee Number", type: "text"}, {name: "firstName", label: "First Name", type: "text"}, {name: "lastName", label: "Last Name", type: "text"}, {name: "email", label: "Email", type: "email"}, {name: "phone", label: "Phone", type: "text"}, {name: "department", label: "Department", type: "text"}, {name: "designation", label: "Designation", type: "text"}]'
    }
)

$template = @"
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>{{TITLE}} GUI - College Management System</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --bg-color: #0f172a;
            --text-color: #f8fafc;
            --card-bg: rgba(30, 41, 59, 0.7);
            --primary: #3b82f6;
            --primary-hover: #2563eb;
            --accent: #8b5cf6;
            --danger: #ef4444;
            --danger-hover: #dc2626;
            --success: #10b981;
            --border: rgba(255, 255, 255, 0.1);
        }

        * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Inter', sans-serif; }
        
        body {
            background-color: var(--bg-color);
            color: var(--text-color);
            min-height: 100vh;
            background-image: 
                radial-gradient(at 0% 0%, hsla(253,16%,7%,1) 0, transparent 50%), 
                radial-gradient(at 50% 0%, hsla(225,39%,30%,0.2) 0, transparent 50%), 
                radial-gradient(at 100% 0%, hsla(339,49%,30%,0.2) 0, transparent 50%);
            background-attachment: fixed;
            background-size: cover;
            padding: 2rem;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
        }

        header {
            display: flex;
            align-items: center;
            gap: 1.5rem;
            margin-bottom: 2rem;
            padding-bottom: 1.5rem;
            border-bottom: 1px solid var(--border);
        }

        .icon-container {
            width: 60px; height: 60px;
            background: linear-gradient(135deg, var(--primary), var(--accent));
            border-radius: 16px;
            display: flex; align-items: center; justify-content: center;
            box-shadow: 0 10px 20px rgba(0,0,0,0.3);
        }
        .icon-container svg { width: 30px; height: 30px; color: white; }
        
        h1 { font-size: 2.5rem; font-weight: 700; background: linear-gradient(to right, #60a5fa, #c084fc); -webkit-background-clip: text; -webkit-text-fill-color: transparent; }
        p.subtitle { color: #94a3b8; font-size: 1.1rem; }

        .layout {
            display: grid;
            grid-template-columns: 1fr 2fr;
            gap: 2rem;
        }

        @media (max-width: 900px) {
            .layout { grid-template-columns: 1fr; }
        }

        .glass-card {
            background: var(--card-bg);
            backdrop-filter: blur(12px);
            -webkit-backdrop-filter: blur(12px);
            border: 1px solid var(--border);
            border-radius: 20px;
            padding: 2rem;
            box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
        }

        h2 { font-size: 1.5rem; margin-bottom: 1.5rem; border-bottom: 1px solid var(--border); padding-bottom: 0.5rem; color: white; }

        .form-group { margin-bottom: 1.2rem; }
        label { display: block; margin-bottom: 0.5rem; font-size: 0.9rem; color: #cbd5e1; }
        input {
            width: 100%;
            padding: 0.8rem 1rem;
            background: rgba(15, 23, 42, 0.6);
            border: 1px solid var(--border);
            border-radius: 8px;
            color: white;
            outline: none;
            transition: all 0.3s ease;
        }
        input:focus { border-color: var(--primary); box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.2); }

        .btn {
            background: linear-gradient(135deg, var(--primary), var(--accent));
            color: white; border: none; padding: 0.8rem 1.5rem; font-size: 1rem; font-weight: 600;
            border-radius: 8px; cursor: pointer; transition: all 0.3s ease; width: 100%;
        }
        .btn:hover { transform: translateY(-2px); box-shadow: 0 8px 20px rgba(59, 130, 246, 0.4); }
        .btn:disabled { opacity: 0.5; cursor: not-allowed; }
        
        .btn-danger { background: var(--danger); }
        .btn-danger:hover { background: var(--danger-hover); box-shadow: 0 8px 20px rgba(239, 68, 68, 0.4); }

        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 1rem; text-align: left; border-bottom: 1px solid var(--border); }
        th { color: #94a3b8; font-size: 0.85rem; text-transform: uppercase; letter-spacing: 0.05em; }
        td { color: #f8fafc; font-size: 0.95rem; }
        tr:hover { background: rgba(255, 255, 255, 0.02); }

        .notification {
            position: fixed; top: 20px; right: 20px; padding: 1rem 1.5rem; border-radius: 8px;
            background: var(--card-bg); border: 1px solid var(--border); color: white;
            box-shadow: 0 10px 30px rgba(0,0,0,0.5); transform: translateX(120%); transition: all 0.3s ease;
            z-index: 1000;
        }
        .notification.show { transform: translateX(0); }
        .notification.success { border-left: 4px solid var(--success); }
        .notification.error { border-left: 4px solid var(--danger); }

        .loading { display: none; margin: 2rem auto; border: 4px solid rgba(255,255,255,0.1); border-top: 4px solid var(--primary); border-radius: 50%; width: 40px; height: 40px; animation: spin 1s linear infinite; }
        @keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }
        
        .empty-state { text-align: center; padding: 3rem; color: #94a3b8; }
    </style>
</head>
<body>
    <div id="notification" class="notification"></div>

    <div class="container">
        <header>
            <div class="icon-container">
                <svg fill="none" viewBox="0 0 24 24" stroke="currentColor">{{ICON}}</svg>
            </div>
            <div>
                <h1>{{TITLE}}</h1>
                <p class="subtitle">Management Dashboard Endpoint</p>
            </div>
        </header>

        <div class="layout">
            <div class="glass-card">
                <h2>Add {{ENTITY}}</h2>
                <form id="createForm">
                    <div id="formFields"></div>
                    <button type="submit" class="btn" id="submitBtn">Save {{ENTITY}}</button>
                </form>
            </div>

            <div class="glass-card" style="overflow-x: auto;">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem;">
                    <h2 style="margin-bottom: 0; border: none;">{{PLURAL_ENTITY}} Directory</h2>
                    <button class="btn" style="width: auto; padding: 0.5rem 1rem;" onclick="loadData()">Refresh</button>
                </div>
                
                <div id="loading" class="loading"></div>
                
                <table id="dataTable" style="display: none;">
                    <thead>
                        <tr id="tableHeadRow">
                            <th>ID</th>
                            <!-- Dynamic headers -->
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody id="tableBody"></tbody>
                </table>
                <div id="emptyState" class="empty-state" style="display: none;">
                    No records found.
                </div>
            </div>
        </div>
    </div>

    <script>
        const CFG = {
            ns: "{{NAMESPACE}}",
            entityName: "{{ENTITY}}",
            entityCamel: "{{ENTITY_CAMEL}}",
            pluralCamel: "{{PLURAL_CAMEL}}",
            getAllRequest: "{{GET_ALL_REQUEST}}",
            fields: {{FIELDS}}
        };

        document.addEventListener('DOMContentLoaded', () => {
            renderForm();
            renderTableHeaders();
            loadData();
        });

        function renderForm() {
            const container = document.getElementById('formFields');
            CFG.fields.forEach(f => {
                const group = document.createElement('div');
                group.className = 'form-group';
                group.innerHTML = `
                    <label for="${f.name}">${f.label}</label>
                    <input type="${f.type}" id="${f.name}" name="${f.name}" required>
                `;
                container.appendChild(group);
            });
            
            document.getElementById('createForm').addEventListener('submit', async (e) => {
                e.preventDefault();
                const btn = document.getElementById('submitBtn');
                btn.disabled = true;
                btn.innerText = 'Saving...';
                
                const formData = {};
                CFG.fields.forEach(f => {
                    formData[f.name] = document.getElementById(f.name).value;
                });
                
                await createRecord(formData);
                
                btn.disabled = false;
                btn.innerText = `Save ${CFG.entityName}`;
            });
        }

        function renderTableHeaders() {
            const tr = document.getElementById('tableHeadRow');
            const actionsTh = tr.lastElementChild;
            CFG.fields.forEach(f => {
                const th = document.createElement('th');
                th.innerText = f.label;
                tr.insertBefore(th, actionsTh);
            });
        }

        function showNotification(msg, type) {
            const n = document.getElementById('notification');
            n.innerText = msg;
            n.className = `notification show ${type}`;
            setTimeout(() => n.className = 'notification', 3000);
        }

        async function sendSoap(requestName, payload) {
            const envelope = `<?xml version="1.0" encoding="utf-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tns="${CFG.ns}">
   <soapenv:Header/>
   <soapenv:Body>
      <tns:${requestName}>
         ${payload}
      </tns:${requestName}>
   </soapenv:Body>
</soapenv:Envelope>`;

            try {
                const response = await fetch('/ws', {
                    method: 'POST',
                    headers: { 'Content-Type': 'text/xml' },
                    body: envelope
                });
                
                if (!response.ok) {
                    throw new Error(`HTTP Error: ${response.status}`);
                }
                
                const text = await response.text();
                const parser = new DOMParser();
                return parser.parseFromString(text, "text/xml");
            } catch (error) {
                console.error("SOAP Error:", error);
                throw error;
            }
        }

        async function loadData() {
            document.getElementById('loading').style.display = 'block';
            document.getElementById('dataTable').style.display = 'none';
            document.getElementById('emptyState').style.display = 'none';
            
            try {
                const xml = await sendSoap(CFG.getAllRequest, '');
                
                let nodes = [];
                const allElements = xml.getElementsByTagName('*');
                for (let i = 0; i < allElements.length; i++) {
                    if (allElements[i].localName === CFG.pluralCamel || allElements[i].localName === CFG.entityCamel) {
                        if (!allElements[i].localName.includes('Response')) {
                            nodes.push(allElements[i]);
                        }
                    }
                }
                
                const tbody = document.getElementById('tableBody');
                tbody.innerHTML = '';
                
                if (nodes.length === 0) {
                    document.getElementById('emptyState').style.display = 'block';
                } else {
                    nodes.forEach(node => {
                        const tr = document.createElement('tr');
                        
                        const getText = (tagName) => {
                            for (let i = 0; i < node.childNodes.length; i++) {
                                let child = node.childNodes[i];
                                if (child.nodeType === 1 && child.localName === tagName) return child.textContent;
                            }
                            return '';
                        };
                        
                        const id = getText('id') || 'N/A';
                        tr.innerHTML = `<td>${id}</td>`;
                        
                        CFG.fields.forEach(f => {
                            tr.innerHTML += `<td>${getText(f.name)}</td>`;
                        });
                        
                        const actionTd = document.createElement('td');
                        actionTd.innerHTML = `<button class="btn btn-danger" style="padding: 0.4rem 0.8rem; font-size: 0.8rem; width: auto;" onclick="deleteRecord('${id}')">Delete</button>`;
                        tr.appendChild(actionTd);
                        
                        tbody.appendChild(tr);
                    });
                    document.getElementById('dataTable').style.display = 'table';
                }
            } catch (err) {
                showNotification("Failed to load data", "error");
            } finally {
                document.getElementById('loading').style.display = 'none';
            }
        }

        async function createRecord(data) {
            let payload = '';
            CFG.fields.forEach(f => {
                payload += `<tns:${f.name}>${data[f.name]}</tns:${f.name}>\n`;
            });
            
            try {
                await sendSoap(`create${CFG.entityName}Request`, payload);
                showNotification(`${CFG.entityName} created successfully!`, "success");
                document.getElementById('createForm').reset();
                loadData();
            } catch (err) {
                showNotification("Failed to create record", "error");
            }
        }

        async function deleteRecord(id) {
            if (!confirm(`Are you sure you want to delete ${CFG.entityName} ID ${id}?`)) return;
            
            try {
                const idField = CFG.entityCamel + 'Id';
                const payload = `<tns:${idField}>${id}</tns:${idField}>`;
                
                await sendSoap(`delete${CFG.entityName}Request`, payload);
                showNotification(`${CFG.entityName} deleted successfully!`, "success");
                loadData();
            } catch (err) {
                showNotification("Failed to delete record", "error");
            }
        }
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
                             -replace '\{\{NAMESPACE\}\}', $service.Namespace `
                             -replace '\{\{ENTITY\}\}', $service.Entity `
                             -replace '\{\{PLURAL_ENTITY\}\}', $service.PluralEntity `
                             -replace '\{\{ENTITY_CAMEL\}\}', $service.EntityCamel `
                             -replace '\{\{PLURAL_CAMEL\}\}', $service.PluralCamel `
                             -replace '\{\{GET_ALL_REQUEST\}\}', $service.GetAllRequest `
                             -replace '\{\{FIELDS\}\}', $service.Fields `
                             -replace '\{\{ICON\}\}', $service.Icon
                             
    $filePath = "$dir\index.html"
    Set-Content -Path $filePath -Value $fileContent -Encoding UTF8
    Write-Host "Created CRUD UI for $($service.Name) at $filePath"
}
