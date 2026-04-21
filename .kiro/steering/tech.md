# Technology Stack

## Backend

### Framework & Runtime
- **Spring Boot**: 3.2.5
- **Java**: 17
- **Build Tool**: Maven

### Dependencies
- `spring-boot-starter-web`: REST API framework
- `spring-boot-starter-data-jpa`: ORM and database access
- `spring-boot-starter-security`: Authentication and authorization
- `spring-boot-starter-validation`: Bean validation (Jakarta)
- `mysql-connector-java`: 8.0.33
- `lombok`: Code generation (optional dependency)

### Database
- **MySQL**: 8.0
- **Schema Management**: Hibernate auto DDL (update mode in development)

### Key Libraries
- **BCrypt**: Password hashing via Spring Security
- **Jakarta Validation**: `@NotBlank`, `@Valid` annotations

## Frontend

### Framework & Build
- **Vue**: 3.4.29
- **Build Tool**: Vite: 5.3.1
- **Package Manager**: npm

### Dependencies
- `vue-router`: 4.3.3 - Client-side routing
- `axios`: 1.7.2 - HTTP client
- `element-plus`: 2.7.6 - UI component library
- `@element-plus/icons-vue`: 2.3.1 - Icon components

### Development Server
- **Port**: 5173
- **Proxy**: `/api` → `http://localhost:8080`

## Common Commands

### Backend
```bash
# Run the application
cd backend
mvn spring-boot:run

# Build JAR
mvn clean package

# Run tests
mvn test
```

### Frontend
```bash
# Install dependencies
cd frontend
npm install

# Start development server
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview
```

### Database
```bash
# Create database and initial data
mysql -u root -p < database.sql
```

## Configuration Files
- **Backend**: `backend/src/main/resources/application.properties`
- **Frontend**: `frontend/vite.config.js`
