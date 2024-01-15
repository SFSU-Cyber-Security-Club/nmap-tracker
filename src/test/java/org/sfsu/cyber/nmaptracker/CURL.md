Certainly! To test each endpoint of your `ScanController` using `curl`, you'll need different commands for each operation. Below are the `curl` commands for each mapping:

### 1. `curl` Command to Get All Scans

This command retrieves all scans:

```bash
curl -s http://localhost:8080/api/scans
```

### 2. `curl` Command to Post a New Scan

This command posts a new scan. Replace `your_xml_file.xml` with the path to your XML file:

```bash
curl -s -X POST http://localhost:8080/api/scans \
     -H 'Content-Type: application/xml' \
     --data-binary @subnet-top-50
```

### 3. `curl` Command to Get a Scan by HashCode

This command retrieves a specific scan by its hashCode. Replace `12345` with the actual hashCode:

```bash
curl -s http://localhost:8080/api/scans/413214151
```

### 4. `curl` Command to Delete a Scan by HashCode

This command deletes a specific scan by its hashCode. Replace `12345` with the hashCode of the scan you want to delete:

```bash
curl -s -X DELETE http://localhost:8080/api/scans/413214151
```

### Important Notes:

- Ensure your Spring Boot application is running and accessible at the specified URL (`http://localhost:8080` by default).
- Adjust the port in the URL if your application runs on a different one.
- The XML data for the `POST` request should be properly formatted. If you encounter issues with command-line escaping, it's often easier to place the XML content in a file and use `--data-binary @filename.xml` with `curl`.
- These commands are basic and assume no additional security (like authentication) is required by your API.

With these commands, you can test each of the RESTful endpoints exposed by your `ScanController` using `curl`.
