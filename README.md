# Innolabs_Test_Oauth2 - Nguyen Tan Dung


# 📝 **Article Management System**  

## 🌟 **Overview**  
This is a basic backend application designed to manage a system for **article posting**, **moderation**, and **reading**.

---

## 🔧 **Technology Stack**  
- **Java Spring Boot**  
- **MySQL**  
- **Docker**

---

## 👥 **Roles and Permissions**  

| Role   | Permissions                                                                                      |
|--------|--------------------------------------------------------------------------------------------------|
| **Admin** | - Default account created upon system initialization. <br> Email: `admin@gmail.com` <br> Password: `PassAdmin123@` <br>- Full system permissions. |
| **Staff** | - Accounts created by admin. <br> Permissions: <br> &nbsp; - View and approve articles. <br> &nbsp; - Create articles (categorized as admin-created). |
| **User**  | - Can post articles (categorized as user-created). <br>- Can read approved articles. <br>- Can delete their own articles. |

---

# 📖 **API Documentation**

---

## **User APIs**

| HTTP Method | Endpoint                     | Description                                                                                     | Constraints                                                                                                                  |
|-------------|-------------------------------|-------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| **PUT**     | `/user/forgotPassword`       | Enter email, password, and token sent via email to change the password.                        | - **Role**: Public <br> - Email must be in the correct format. <br> - Password must have at least one special character, uppercase, lowercase, and be at least 8 characters. <br> - Token must be at least 8 characters. |
| **POST**    | `/user/signUp`               | Users fill in email, password, name, and date of birth to register an account.                 | - **Role**: Public <br> - Email must be in the correct format. <br> - Password must meet complexity requirements. <br> - Name must have at least 3 characters. <br> - User must be ≥ 18 years old.                  |
| **POST**    | `/user/login`                | User fills email and password to log in.                                                       | - **Role**: Public <br> - Email must be in the correct format. <br> - Password must meet complexity requirements.                                                 |
| **POST**    | `/user/createStaff`          | Admin fills in email, password, name, and date of birth to create a staff account.             | - **Role**: Admin <br> - Email must be in the correct format. <br> - Password must meet complexity requirements. <br> - Staff must be ≥ 18 years old.                                                  |
| **GET**     | `/user/getUserInfo`          | User provides user ID to get info of the specified user.                                        | - **Role**: User, admin, staff <br> - User ID must be in UUID format.                                                                                                             |
| **GET**     | `/user/getTokenResetPassword`| User fills email to get a password reset token sent via email.                                 | - **Role**: Public <br> - Email must be in the correct format.                                                                                                      |
| **GET**     | `/user/getAllStaff`          | Admin gets info of all staff.                                                                  | - **Role**: Admin                                                                                                            |
| **DELETE**  | `/user/delete`               | Admin provides staff ID to delete the staff account.                                           | - **Role**: Admin <br> - Staff ID must be in UUID format.                                                                                                           |

---

## **Article APIs**

| HTTP Method | Endpoint                     | Description                                                                                     | Constraints                                                                                                                  |
|-------------|-------------------------------|-------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| **PUT**     | `/article/editArticle`       | User edits article by providing ID, content, category, title, and reference link.              | - **Role**: User (owner of the article) <br> - Article ID must be UUID. <br> - Title must be 10–20 characters. <br> - Category must be Technology, Health, or Business. <br> - Content must be ≥ 300 characters. <br> - Reference link must follow URL format. |
| **PUT**     | `/article/approve`           | Admin or staff approves an article by providing its ID.                                        | - **Role**: Admin, Staff <br> - Article ID must be UUID.                                                                                                          |
| **POST**    | `/article/createArticle`     | User creates an article saved for themselves; admin/staff create directly approved articles.   | - **Role**: Admin, Staff, User <br> - Title must be 10–20 characters. <br> - Category must be Technology, Health, or Business. <br> - Content must be ≥ 300 characters. <br> - Reference link must follow URL format. |
| **GET**     | `/article/get`               | Retrieves a paginated list of articles (10 articles per page).                                  | - **Role**: Admin, Staff, User <br> - Page must be an integer ≥ 1.                                                                                                |
| **GET**     | `/article/getUnapprovedList` | Retrieves a list of unapproved articles.                                                       | - **Role**: Admin, Staff                                                                                                    |
| **DELETE**  | `/article/delete`            | Deletes an article by its ID.                                                                  | - **Role**: Admin, User (owner of the article) <br> - Article ID must be UUID.                                                                                   |

---

# 🚀 **How to Run the Project**

Follow these steps to set up and run the project on your local machine:

1. **Open Terminal**  
    Launch the terminal or command prompt.

2. **Navigate to the Desired Directory**  
    Move to the directory where you want to clone the project:
   ```bash  
    cd <your-desired-directory>

4. **Clone the Project**
    Clone the project from GitHub:
   ```bash
    git clone https://github.com/NguyenTanDung-2004/Innolabs_Test_Oauth2.git

6. **Move to the Project Directory**
    Change to the project directory:
   ```bash
    cd Innolabs_Test_Oauth2

8. **Run Docker Compose**
    Build and start the Docker containers using Docker Compose:
   ```bash
    docker-compose -f DockerCompose.yml up --build

10. **Access Swagger UI**
    After completing step 5, open your browser and navigate to the following link to access the Swagger UI:
    ```bash
    http://localhost:8080/Mini_Project1-0.0.1-SNAPSHOT/swagger-ui/index.html#
