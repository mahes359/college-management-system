$scriptGeneric = @'
        let GATEWAY_URL = localStorage.getItem('API_GATEWAY_URL') || '';

        document.addEventListener('DOMContentLoaded', () => {
            renderForm();
            renderTableHeaders();
            loadData();
            checkGatewayStatus();
        });

        function checkGatewayStatus() {
            const currentService = window.location.pathname.split('/')[1];
            const isAccessedViaGateway = ['student', 'faculty', 'course', 'enrollment', 'attendance', 'exam'].includes(currentService);
            
            if (!isAccessedViaGateway) {
                const banner = document.createElement('div');
                banner.style = "background: rgba(239, 68, 68, 0.2); border: 1px solid var(--danger); padding: 1rem; border-radius: 8px; margin-bottom: 2rem; display: flex; align-items: center; justify-content: space-between; gap: 1rem; flex-wrap: wrap;";
                
                banner.innerHTML = `
                    <div>
                        <strong style="color: #fca5a5;">⚠️ Direct Access Detected</strong><br>
                        <span style="font-size: 0.9rem; color: #f8fafc;">You are accessing this service directly. To load dropdowns from other services, please provide your API Gateway URL (e.g. https://api-gateway-xyz.onrender.com)</span>
                    </div>
                    <div style="display: flex; gap: 0.5rem; flex-grow: 1; max-width: 400px;">
                        <input type="text" id="gwUrlInput" placeholder="https://..." value="${GATEWAY_URL}" style="padding: 0.5rem; flex-grow: 1;">
                        <button class="btn" style="width: auto; padding: 0.5rem 1rem;" onclick="saveGatewayUrl()">Save & Reload</button>
                    </div>
                `;
                document.querySelector('header').insertAdjacentElement('afterend', banner);
            }
        }

        window.saveGatewayUrl = function() {
            let url = document.getElementById('gwUrlInput').value.trim();
            if (url) {
                if (url.endsWith('/')) url = url.slice(0, -1);
                localStorage.setItem('API_GATEWAY_URL', url);
                window.location.reload();
            }
        };

        async function fetchOptions(service, reqName, ns, extractArray, extractId, extractLabel) {
            const envelope = `<?xml version="1.0" encoding="utf-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tns="${ns}">
   <soapenv:Header/>
   <soapenv:Body><tns:${reqName}/></soapenv:Body>
</soapenv:Envelope>`;
            try {
                const currentService = window.location.pathname.split('/')[1];
                const isGateway = ['student', 'faculty', 'course', 'enrollment', 'attendance', 'exam'].includes(currentService);
                
                let url;
                if (isGateway) {
                    url = `/${service}/ws`;
                } else if (GATEWAY_URL) {
                    url = `${GATEWAY_URL}/${service}/ws`;
                } else {
                    throw new Error("No Gateway URL");
                }

                const response = await fetch(url, { method: 'POST', headers: { 'Content-Type': 'text/xml' }, body: envelope });
                if(!response.ok) return [];
                const text = await response.text();
                const parser = new DOMParser();
                const xml = parser.parseFromString(text, "text/xml");
                let nodes = [];
                const allElements = xml.getElementsByTagName('*');
                for (let i = 0; i < allElements.length; i++) {
                    if (extractArray.includes(allElements[i].localName) && !allElements[i].localName.includes('Response')) {
                        nodes.push(allElements[i]);
                    }
                }
                return nodes.map(node => {
                    const getText = (tag) => {
                        for (let i = 0; i < node.childNodes.length; i++) {
                            if (node.childNodes[i].nodeType === 1 && node.childNodes[i].localName === tag) return node.childNodes[i].textContent;
                        }
                        return '';
                    };
                    return { id: getText(extractId), label: extractLabel.map(l => getText(l)).join(' - ') };
                });
            } catch(e) { 
                console.error("Failed to fetch cross-service options.", e); 
                return []; 
            }
        }

        async function renderForm() {
            const container = document.getElementById('formFields');
            container.innerHTML = ''; // Clear existing
            for (let f of CFG.fields) {
                const group = document.createElement('div');
                group.className = 'form-group';
                
                if (f.type === 'select') {
                    group.innerHTML = `
                        <label for="${f.name}">${f.label}</label>
                        <select id="${f.name}" name="${f.name}" required style="width: 100%; padding: 0.8rem 1rem; background: rgba(15, 23, 42, 0.6); border: 1px solid rgba(255, 255, 255, 0.1); border-radius: 8px; color: white; outline: none; appearance: auto;">
                            <option value="">Loading...</option>
                        </select>
                    `;
                    container.appendChild(group);
                    
                    fetchOptions(f.sourceService, f.sourceReq, f.sourceNs, f.sourceTags, f.sourceId, f.sourceLabel).then(options => {
                        const select = document.getElementById(f.name);
                        select.innerHTML = `<option value="">Select ${f.label}</option>`;
                        options.forEach(opt => {
                            select.innerHTML += `<option value="${opt.id}">${opt.label}</option>`;
                        });
                        if(options.length === 0) {
                            select.innerHTML = `<option value="">No data found (Configure Gateway URL above)</option>`;
                        }
                    });
                } else {
                    let inputType = f.type;
                    let stepHtml = inputType === 'number' ? ' step="any"' : '';
                    group.innerHTML = `
                        <label for="${f.name}">${f.label}</label>
                        <input type="${inputType}" id="${f.name}" name="${f.name}" required${stepHtml}>
                    `;
                    container.appendChild(group);
                }
            }
            
            // Unbind existing listener to prevent duplicates
            const form = document.getElementById('createForm');
            const newForm = form.cloneNode(true);
            form.parentNode.replaceChild(newForm, form);
            
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
            // Clear existing dynamic headers
            while(tr.children.length > 2) {
                tr.removeChild(tr.children[1]);
            }
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
                const currentService = window.location.pathname.split('/')[1];
                const isGateway = ['student', 'faculty', 'course', 'enrollment', 'attendance', 'exam'].includes(currentService);
                const wsUrl = isGateway ? `/${currentService}/ws` : '/ws';
                
                const response = await fetch(wsUrl, {
                    method: 'POST',
                    headers: { 'Content-Type': 'text/xml' },
                    body: envelope
                });
                
                if (!response.ok) {
                    let errMsg = `HTTP Error: ${response.status}`;
                    try {
                        const text = await response.text();
                        const parser = new DOMParser();
                        const xml = parser.parseFromString(text, "text/xml");
                        const faultStrings = xml.getElementsByTagName('faultstring');
                        if (faultStrings.length > 0) {
                            errMsg = faultStrings[0].textContent;
                        }
                    } catch(e) {}
                    throw new Error(errMsg);
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
                    if (allElements[i].localName === CFG.pluralCamel || allElements[i].localName === CFG.entityCamel || allElements[i].localName === CFG.entityCamel + 's' || allElements[i].localName === 'faculties') {
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
                showNotification(err.message || "Failed to load data", "error");
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
                showNotification(err.message || "Failed to create record", "error");
            }
        }

        async function deleteRecord(id) {
            if (!confirm(`Are you sure you want to delete ID ${id}?`)) return;
            
            try {
                const idField = CFG.entityCamel + 'Id';
                const payload = `<tns:${idField}>${id}</tns:${idField}>`;
                
                await sendSoap(`delete${CFG.entityName}Request`, payload);
                showNotification(`${CFG.entityName} deleted successfully!`, "success");
                loadData();
            } catch (err) {
                showNotification(err.message || "Failed to delete record", "error");
            }
        }
    </script>
</body>
</html>
'@

# Define CFGs
$studentCfg = @'
    <script>
        const CFG = {
            ns: "http://college.com/student",
            entityName: "Student",
            entityCamel: "student",
            pluralCamel: "students",
            getAllRequest: "getAllStudentsRequest",
            fields: [
                {name: "studentNumber", label: "Student Number", type: "text"},
                {name: "firstName", label: "First Name", type: "text"},
                {name: "lastName", label: "Last Name", type: "text"},
                {name: "email", label: "Email", type: "email"},
                {name: "phone", label: "Phone", type: "text"},
                {name: "department", label: "Department", type: "text"},
                {name: "year", label: "Year", type: "number"}
            ]
        };
'@

$facultyCfg = @'
    <script>
        const CFG = {
            ns: "http://college.com/faculty",
            entityName: "Faculty",
            entityCamel: "faculty",
            pluralCamel: "faculty",
            getAllRequest: "getAllFacultyRequest",
            fields: [
                {name: "employeeNumber", label: "Employee Number", type: "text"},
                {name: "firstName", label: "First Name", type: "text"},
                {name: "lastName", label: "Last Name", type: "text"},
                {name: "email", label: "Email", type: "email"},
                {name: "phone", label: "Phone", type: "text"},
                {name: "department", label: "Department", type: "text"},
                {name: "designation", label: "Designation", type: "text"}
            ]
        };
'@

$courseCfg = @'
    <script>
        const CFG = {
            ns: "http://college.com/course",
            entityName: "Course",
            entityCamel: "course",
            pluralCamel: "courses",
            getAllRequest: "getAllCoursesRequest",
            fields: [
                {name: "courseCode", label: "Course Code", type: "text"},
                {name: "courseName", label: "Course Name", type: "text"},
                {name: "description", label: "Description", type: "text"},
                {name: "department", label: "Department", type: "text"},
                {name: "credits", label: "Credits", type: "number"},
                {name: "semester", label: "Semester", type: "number"},
                {name: "facultyId", label: "Faculty", type: "select", sourceService: "faculty", sourceNs: "http://college.com/faculty", sourceReq: "getAllFacultyRequest", sourceTags: ["faculty", "faculties"], sourceId: "id", sourceLabel: ["firstName", "lastName"]}
            ]
        };
'@

$enrollmentCfg = @'
    <script>
        const CFG = {
            ns: "http://college.com/enrollment",
            entityName: "Enrollment",
            entityCamel: "enrollment",
            pluralCamel: "enrollments",
            getAllRequest: "getAllEnrollmentsRequest",
            fields: [
                {name: "enrollmentCode", label: "Enrollment Code", type: "text"},
                {name: "studentId", label: "Student", type: "select", sourceService: "student", sourceNs: "http://college.com/student", sourceReq: "getAllStudentsRequest", sourceTags: ["student", "students"], sourceId: "id", sourceLabel: ["firstName", "lastName", "studentNumber"]},
                {name: "courseId", label: "Course", type: "select", sourceService: "course", sourceNs: "http://college.com/course", sourceReq: "getAllCoursesRequest", sourceTags: ["course", "courses"], sourceId: "id", sourceLabel: ["courseName", "courseCode"]},
                {name: "semester", label: "Semester", type: "number"},
                {name: "status", label: "Status", type: "text"}
            ]
        };
'@

$attendanceCfg = @'
    <script>
        const CFG = {
            ns: "http://college.com/attendance",
            entityName: "Attendance",
            entityCamel: "attendance",
            pluralCamel: "attendance",
            getAllRequest: "getAllAttendanceRequest",
            fields: [
                {name: "attendanceCode", label: "Attendance Code", type: "text"},
                {name: "studentId", label: "Student", type: "select", sourceService: "student", sourceNs: "http://college.com/student", sourceReq: "getAllStudentsRequest", sourceTags: ["student", "students"], sourceId: "id", sourceLabel: ["firstName", "lastName"]},
                {name: "courseId", label: "Course", type: "select", sourceService: "course", sourceNs: "http://college.com/course", sourceReq: "getAllCoursesRequest", sourceTags: ["course", "courses"], sourceId: "id", sourceLabel: ["courseName"]},
                {name: "attendanceDate", label: "Date (YYYY-MM-DD)", type: "date"},
                {name: "status", label: "Status (Present/Absent)", type: "text"}
            ]
        };
'@

$examCfg = @'
    <script>
        const CFG = {
            ns: "http://college.com/exam",
            entityName: "Exam",
            entityCamel: "exam",
            pluralCamel: "exams",
            getAllRequest: "getAllExamsRequest",
            fields: [
                {name: "examCode", label: "Exam Code", type: "text"},
                {name: "courseId", label: "Course", type: "select", sourceService: "course", sourceNs: "http://college.com/course", sourceReq: "getAllCoursesRequest", sourceTags: ["course", "courses"], sourceId: "id", sourceLabel: ["courseName"]},
                {name: "examType", label: "Exam Type", type: "text"},
                {name: "examDate", label: "Exam Date", type: "date"},
                {name: "semester", label: "Semester", type: "number"},
                {name: "totalMarks", label: "Total Marks", type: "number"}
            ]
        };
'@

$services = @{
    "student-service" = $studentCfg
    "faculty-service" = $facultyCfg
    "course-service" = $courseCfg
    "enrollment-service" = $enrollmentCfg
    "attendance-service" = $attendanceCfg
    "exam-service" = $examCfg
}

foreach ($svc in $services.Keys) {
    $path = "e:\CODING\CLOUD COMPUTING\WEBSERVICE\$svc\src\main\resources\static\index.html"
    if (Test-Path $path) {
        $content = Get-Content $path -Raw
        $idx = $content.IndexOf("<script>")
        if ($idx -gt -1) {
            $newContent = $content.Substring(0, $idx) + $services[$svc] + "`n" + $scriptGeneric
            Set-Content -Path $path -Value $newContent
            Write-Host "Updated UI for $svc"
        }
    }
}
