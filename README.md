# book++
A draggable 2D bookshelf

## Features

* Add new books
* Rearrange existing books by dragging them around
* Edit existing books (not yet)
* Delete unwanted books (not yet)
* Add additional bookshelves if necessary (not yet)
* Load initial data from an SQL file
* Backend built with Java + Spring Boot
* Frontend built with React + Canvas
* Uses PostgreSQL as the database

## Prerequisites
* Java 17+
* Node.js + npm
* Gradle (or use the included `./gradlew`)
* An IDE or code editor (e.g., IntelliJ IDEA)
* Docker


## Installation

### Database (PostgreSQL via Docker)
1. Run the container:
   ```bash
1. Run the container:
      ```bash
   docker run -d \
     --name postgres-db \
     -e POSTGRES_USER=postgres \
     -e POSTGRES_PASSWORD=postgres \
     -e POSTGRES_DB=bookplusplus \
     -p 5432:5432 \
     postgres:15

### Backend (Spring Boot)
1. Clone the repository:
   ```bash
   git clone https://github.com/palllaura/bookplusplus.git
   cd bookplusplus

2. Open the project in your IDE.

3. Build and run the backend:
   ```bash
   ./gradlew bootRun
4. The backend server will start at:
   http://localhost:8080

### Frontend (React + Canvas)
1. Navigate to the frontend folder:
   ```bash
   cd frontend
2. Install dependencies:
   ```bash
   npm install
3. Start the development server:
   ```bash
   npm run dev
4. The frontend will be available at:
   http://localhost:5173

<img width="1079" height="814" alt="Screenshot 2025-08-13 at 12 06 06" src="https://github.com/user-attachments/assets/dfb9ef65-11c3-4dad-ae24-483e63e8d736" />

