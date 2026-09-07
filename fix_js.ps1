$scriptContent = @'
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
            if (!confirm(`Are you sure you want to delete ID ${id}?`)) return;
            
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
'@

$files = Get-ChildItem -Path "e:\CODING\CLOUD COMPUTING\WEBSERVICE" -Filter "index.html" -Recurse

foreach ($file in $files) {
    if ($file.FullName -match "src\\main\\resources\\static") {
        $content = Get-Content $file.FullName -Raw
        $idx = $content.IndexOf("document.addEventListener('DOMContentLoaded'")
        if ($idx -gt -1) {
            $newContent = $content.Substring(0, $idx) + $scriptContent
            Set-Content -Path $file.FullName -Value $newContent
            Write-Host "Fixed HTML script in $($file.FullName)"
        }
    }
}
