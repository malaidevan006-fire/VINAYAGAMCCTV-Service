# 🎥 Vinayagam CCTV — Spring Boot Project Guide

## Complete Step-by-Step Instructions

---

## 📁 Project Structure

```
vinayagam-cctv/
├── pom.xml
└── src/
    └── main/
        ├── java/com/vinayagam/cctv/
        │   ├── VinayagamCctvApplication.java   ← Main class
        │   ├── controller/
        │   │   └── HomeController.java          ← All page routes
        │   ├── model/
        │   │   └── QuoteRequest.java            ← Database entity
        │   └── repository/
        │       └── QuoteRepository.java         ← DB operations
        └── resources/
            ├── application.properties           ← Config
            ├── templates/
            │   ├── index.html                   ← Home page
            │   └── admin/
            │       └── quotes.html              ← Admin panel
            └── static/
                ├── css/style.css
                └── js/main.js
```

---

## 🛠️ STEP 1: Install Required Tools

### Install Java 21
1. Go to: https://adoptium.net/
2. Download **Java 21 (LTS)** for Windows/Mac/Linux
3. Install it and verify:
   ```bash
   java -version
   # Should show: openjdk version "21.x.x"
   ```

### Install Maven
1. Go to: https://maven.apache.org/download.cgi
2. Download **apache-maven-3.9.x-bin.zip**
3. Extract to `C:\maven` (Windows) or `/opt/maven` (Linux/Mac)
4. Add to PATH environment variable
5. Verify:
   ```bash
   mvn -version
   # Should show: Apache Maven 3.9.x
   ```

### Install IntelliJ IDEA (Recommended IDE)
1. Go to: https://www.jetbrains.com/idea/download/
2. Download **Community Edition** (free)
3. Install and open it

---

## 📦 STEP 2: Create the Project

### Option A: Using Spring Initializr (Easiest)
1. Go to: https://start.spring.io/
2. Fill in:
   - **Project**: Maven
   - **Language**: Java
   - **Spring Boot**: 3.2.0
   - **Group**: com.vinayagam
   - **Artifact**: cctv
   - **Java**: 21
3. Click **Add Dependencies** and add:
   - Spring Web
   - Thymeleaf
   - Spring Data JPA
   - H2 Database
   - Validation
   - Spring Boot DevTools
4. Click **Generate** → download ZIP
5. Extract and open folder in IntelliJ IDEA

### Option B: Copy Files Directly
Copy all provided files into the project structure shown above.

---

## 📂 STEP 3: Copy All Files

Place each file in its correct location:

| File | Location |
|------|----------|
| `pom.xml` | Root of project |
| `VinayagamCctvApplication.java` | `src/main/java/com/vinayagam/cctv/` |
| `HomeController.java` | `src/main/java/com/vinayagam/cctv/controller/` |
| `QuoteRequest.java` | `src/main/java/com/vinayagam/cctv/model/` |
| `QuoteRepository.java` | `src/main/java/com/vinayagam/cctv/repository/` |
| `application.properties` | `src/main/resources/` |
| `index.html` | `src/main/resources/templates/` |
| `quotes.html` | `src/main/resources/templates/admin/` |
| `style.css` | `src/main/resources/static/css/` |
| `main.js` | `src/main/resources/static/js/` |

---

## ▶️ STEP 4: Run the Application

### Using IntelliJ IDEA:
1. Open the project in IntelliJ
2. Wait for Maven to download dependencies (first time takes 2–3 min)
3. Find `VinayagamCctvApplication.java`
4. Click the **green ▶ Run button**
5. Wait for: `Started VinayagamCctvApplication in X seconds`

### Using Command Line (Terminal):
```bash
# Navigate to project folder
cd vinayagam-cctv

# Build and run
mvn spring-boot:run

# OR build a JAR first
mvn clean package
java -jar target/cctv-1.0.0.jar
```

---

## 🌐 STEP 5: Open in Browser

Once running, open:

| Page | URL |
|------|-----|
| 🏠 Home Page | http://localhost:8080/ |
| 🔧 Services | http://localhost:8080/services |
| 🏗️ Projects | http://localhost:8080/projects |
| 📞 Contact | http://localhost:8080/contact |
| 👤 Admin Panel | http://localhost:8080/admin/quotes |
| 🗄️ H2 Database | http://localhost:8080/h2-console |

**H2 Console Login:**
- JDBC URL: `jdbc:h2:mem:vinayagamdb`
- Username: `sa`
- Password: (leave blank)

---

## 🗄️ STEP 6: Switch to MySQL (Production)

When you're ready for real deployment:

### 1. Add MySQL Dependency to `pom.xml`:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

### 2. Update `application.properties`:
```properties
# Comment out H2 lines and use these instead:
spring.datasource.url=jdbc:mysql://localhost:3306/vinayagam_cctv
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

### 3. Create MySQL Database:
```sql
CREATE DATABASE vinayagam_cctv;
```

---

## 🚀 STEP 7: Deploy to Server (Optional)

### Build JAR file:
```bash
mvn clean package -DskipTests
```
This creates: `target/cctv-1.0.0.jar`

### Run on server:
```bash
java -jar cctv-1.0.0.jar --server.port=80
```

---

## 📱 Pages Available

| Page | What It Shows |
|------|--------------|
| **Home** | Hero, Services, Why Us, Stats, Projects, Quote Form, Team, Testimonials |
| **Admin Panel** | All submitted quote requests in a table |

---

## 🔧 Customization

### Change Company Phone/Email:
Edit `index.html` — search for `+91 98765 43210` and replace with your number.

### Change Address:
Edit `index.html` — search for `Anna Nagar` and update to your address.

### Change Colors:
Edit `style.css` — change `--green: #2ecc71` to any color you want.

### Add WhatsApp Float Button:
Add this before `</body>` in `index.html`:
```html
<a href="https://wa.me/919876543210" target="_blank"
   style="position:fixed;bottom:80px;right:25px;background:#25d366;
          color:#fff;border-radius:50%;width:50px;height:50px;
          display:flex;align-items:center;justify-content:center;
          font-size:1.5rem;z-index:999;">
   <i class="bi bi-whatsapp"></i>
</a>
```

---

## ❓ Common Errors & Fixes

| Error | Fix |
|-------|-----|
| `Port 8080 already in use` | Change to `server.port=9090` in application.properties |
| `Java version mismatch` | Make sure Java 21 is installed |
| `BUILD FAILURE` | Run `mvn clean` then `mvn spring-boot:run` again |
| Thymeleaf template not found | Check file is in `src/main/resources/templates/` |
| CSS not loading | Check file is in `src/main/resources/static/css/` |

---

*Built for Vinayagam CCTV — Chennai's Trusted Security Partner 🔒*
