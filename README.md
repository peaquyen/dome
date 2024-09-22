# XBakery Website

Welcome to the **XBakery**! This project is an online bakery platform built with a focus on efficiency, maintainability, and scalability. The application allows users to browse through various bakery products, place orders, and manage bakery operations.

## Table of Contents
- [Introduction](#introduction)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Installation](#installation)
- [Database Structure](#database-structure)
- [Contributing](#contributing)
- [License](#license)

## Introduction
**XBakery** is a dynamic web application designed for bakery shops to manage their products and customer orders effectively. The platform provides features such as adding, updating, and deleting bakery products, along with handling customer orders. The focus is on delivering a seamless user experience with fast and efficient database management.

The project demonstrates the integration of front-end and back-end technologies, implementing secure and scalable practices to ensure a robust application for real-world use.

## Technologies Used
XBakery employs several technologies and programming languages to create a full-stack application. These include:

### 1. **PHP (Backend)**
   PHP is used to handle server-side logic, such as managing database interactions, handling form submissions, and dynamically generating pages. Its flexibility and large community support make it ideal for building dynamic web applications.

### 2. **MySQL (Database)**
   MySQL is used as the database management system to store information about products, users, and orders. The `Database` class provides methods for performing CRUD operations efficiently, including inserting, updating, deleting, and querying data.

### 3. **HTML, CSS, and JavaScript (Frontend)**
   - **HTML** provides the structure for web pages, organizing content in a semantic way.
   - **CSS** is used to style the application, giving it a visually appealing layout, which is critical for user engagement.
   - **JavaScript** adds interactivity and improves the user experience by enabling features such as form validation and dynamic updates without reloading the page.

### 4. **PDO (PHP Data Objects)**
   PDO is utilized in the `Database` class to interact with the MySQL database. It abstracts database operations and ensures security by preventing SQL injection attacks.

### 5. **MVC Architecture (Model-View-Controller)**
   The project follows the MVC architecture, ensuring a clear separation between logic (Model), presentation (View), and user interaction (Controller). This modularity enhances the maintainability of the codebase and allows for easier scaling.

## Features
- **Product Management**: Admins can add, edit, and delete bakery products.
- **Order Management**: Customers can place orders, and admins can manage them efficiently.
- **Dynamic Interface**: Responsive design ensures the website is accessible on all devices, improving usability.
- **Secure Transactions**: The system is designed with secure database operations to prevent SQL injection and other vulnerabilities.

## Database Structure

The Database class manages CRUD operations through methods like query(), insert(), update(), and delete(). PDO is used for database interactions, enhancing security and performance.

## Installation
To run the XBakery website locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/XBakery.git
2. Configure your database by updating the connection settings in the configuration file (config.php):
    ```bash
    $db_config = [
        'host' => 'localhost',
        'dbname' => 'xbakery_db',
        'user' => 'root',
        'password' => '',
    ];
3. Run the SQL script to set up the database schema and populate initial data.

4. Start the server using a local PHP environment like XAMPP or MAMP.

5. Access the website by navigating to http://localhost/xbakery.
