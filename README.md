# Project Management System
A comprehensive backend system for project management built using Spring Boot, Maven, and MySQL.

## AWS Deployment
**Public IPv4 DNS:** `ec2-65-2-73-178.ap-south-1.compute.amazonaws.com`

### Accessing Endpoints
- **Direct Access:** Replace `localhost` with the provided Public IPv4 DNS when testing in Postman
- **Example:** If the local endpoint is `http://localhost:8080/signin`, use `http://ec2-65-2-73-178.ap-south-1.compute.amazonaws.com:8080/signin`

## Features
- **User Management**
  - User registration and authentication
  - User profile management
  
- **Project Management**
  - Create and manage projects
  - Search projects by keyword
  - View project details
  - Delete projects
  
- **Issue Tracking**
  - Create and assign issues
  - Track issue status
  - Delete issues
  
- **Communication**
  - In-project messaging system
  - Comment system on issues
  - Get project-specific messages

## Tech Stack
- **Backend:** Spring Boot
- **Build Tool:** Maven
- **Database:** MySQL
- **API Testing:** Postman
- **Deployment:** AWS EC2

## API Endpoints

### Authentication
- `POST /signup` - Register a new user
- `POST /signin` - User login

### User Management
- `GET /get_users_profile` - Get user profile information

### Project Operations
- `POST /create_project` - Create a new project
- `GET /get_project_By_ID` - Get project details
- `GET /get_all_Projects` - List all projects
- `DELETE /delete_project_By_ID` - Delete a project
- `GET /search_project_By_Keyword` - Search projects

### Issue Management
- `POST /create_Issues` - Create a new issue
- `GET /get_issue_By_Project_ID` - Get project issues
- `PUT /change_issue_Status` - Update issue status
- `PUT /assigne_user_To_Issue` - Assign user to issue
- `DELETE /delete_Issue` - Delete an issue

### Communication
- `POST /send_message` - Send a message
- `GET /get_message_using_projectId` - Get project messages
- `POST /add_comments` - Add a comment
- `GET /get_all_comments` - Get all comments
- `DELETE /delete_comment` - Delete a comment

## Setup and Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/project-management-system-backend.git
   ```

2. **Configure MySQL**
   - Create a MySQL database
   - Update `application.properties` with your database credentials

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

## API Testing

To test the APIs:

1. Import the Postman collection from the `postman` directory
2. Update the environment variables if necessary
3. Execute the requests

## Postman Collection
### Downloading the Postman Collection
1. Navigate to the GitHub repository
2. Locate the `ProjectManagementSystem.postman_collection.json` file
3. Click on the file
4. Click the "Raw" button
5. Right-click and select "Save As" to download the file

### Importing the Postman Collection
1. Open Postman
2. Click on the "Import" button in the top-left corner
3. Choose the "File" tab
4. Select the downloaded `ProjectManagementSystem.postman_collection.json` file
5. Click "Import"

### Using the Postman Collection
- The collection contains all pre-configured API endpoints
- Replace `localhost` with the AWS EC2 Public IPv4 DNS when testing
- Update environment variables (if required) to match your setup
- Each request is labeled and organized by functionality

### Recommended Setup
- Ensure you have the latest version of Postman installed
- Set up environment variables for:
  - Base URL
  - Authentication tokens
  - Any project-specific configurations
