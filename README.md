# Workflow Project

A full-stack workflow engine with Java backend and Vue frontend.

## Project Structure

```
workflow-project/
├── backend/           # Java Spring Boot backend
│   └── src/main/java/com/workflow/backend/
└── frontend/          # Vue 3 frontend
    └── src/
```

## Backend (Java/Spring Boot)

### Requirements
- Java 17+
- Maven 3.6+

### Run Backend
```bash
cd backend
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

### API Endpoints
- `GET /api/workflows` - List all workflows
- `GET /api/workflows/{id}` - Get workflow by ID
- `POST /api/workflows` - Create new workflow
- `PUT /api/workflows/{id}` - Update workflow
- `DELETE /api/workflows/{id}` - Delete workflow
- `POST /api/workflows/{id}/execute` - Execute workflow

## Frontend (Vue 3)

### Requirements
- Node.js 18+
- npm or yarn

### Run Frontend
```bash
cd frontend
npm install
npm run dev
```

The frontend will be available at `http://localhost:5173`

## Features

- Create and manage workflows
- Define custom steps for each workflow
- Execute workflows and view results
- Real-time status updates
- Modern UI with Element Plus
