# Project Structure

## Directory Layout

```
workflow/
├── backend/                    # Spring Boot Backend Application
│   ├── src/main/
│   │   ├── java/com/workflow/backend/
│   │   │   ├── WorkflowApplication.java    # Main application entry point
│   │   │   ├── config/                      # Configuration classes (empty)
│   │   │   ├── controller/                  # REST API controllers
│   │   │   │   └── AuthController.java      # Authentication endpoints
│   │   │   ├── service/                     # Business logic layer
│   │   │   │   ├── UserService.java         # Service interface
│   │   │   │   └── impl/
│   │   │   │       └── UserServiceImpl.java # Service implementation
│   │   │   ├── repository/                  # Data access layer (JPA)
│   │   │   │   └── UserRepository.java      # User repository
│   │   │   ├── model/                       # Domain entities
│   │   │   │   └── User.java                # User entity
│   │   │   └── dto/                         # Data Transfer Objects
│   │   │       ├── LoginRequest.java        # Login request DTO
│   │   │       └── LoginResponse.java       # Login response DTO
│   │   └── resources/
│   │       └── application.properties       # Application configuration
│   └── pom.xml                               # Maven build configuration
│
├── frontend/                   # Vue 3 Frontend Application
│   ├── src/
│   │   ├── views/                           # Page components
│   │   │   ├── Login.vue                    # Login page
│   │   │   └── Home.vue                     # Home page
│   │   ├── components/                      # Reusable components
│   │   │   ├── HelloWorld.vue               # Example component
│   │   │   └── ...                         # Other components
│   │   ├── router/                          # Vue Router configuration
│   │   ├── utils/                           # Utility functions
│   │   │   └── request.js                   # Axios HTTP client wrapper
│   │   ├── App.vue                          # Root component
│   │   └── main.js                          # Application entry point
│   ├── index.html                           # HTML template
│   ├── package.json                         # npm dependencies
│   └── vite.config.js                       # Vite configuration
│
├── database.sql                # Database schema and initial data
├── README.md                   # Project documentation
└── .kiro/                      # Kiro configuration (steering rules)
```

## Package Structure Conventions

### Backend (Java)
- **Package base**: `com.workflow.backend`
- **Layered architecture**: controller → service → repository → model
- **DTO pattern**: Separate request/response objects from domain models
- **Validation**: Use Jakarta annotations (`@Valid`, `@NotBlank`)
- **Lombok**: Use `@Data` for POJOs to reduce boilerplate

### Frontend (Vue)
- **Component style**: Composition API with `<script setup>`
- **State management**: Local reactive state (`ref`, `reactive`)
- **HTTP client**: Axios with centralized config (`request.js`)
- **UI library**: Element Plus components
- **Routing**: Vue Router for navigation

## Key Conventions

### Backend
1. **Controller layer**: REST endpoints with `@RestController`, `@RequestMapping`
2. **Service layer**: Interface + implementation pattern
3. **Repository layer**: Spring Data JPA repositories
4. **Entity naming**: PascalCase with `@Entity` annotation
5. **Database**: MySQL with UTF8MB4 charset

### Frontend
1. **Component naming**: PascalCase for files and component names
2. **Style scope**: Use `scoped` CSS when component-specific styles needed
3. **HTTP**: All API calls go through `/api` proxy
4. **State persistence**: localStorage for token and user info
