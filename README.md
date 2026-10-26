# Experiment 6: Student Registration Servlet

This experiment is a small Java web application for registering students. It
uses a Java Servlet to display a registration form, stores submitted student
names and email addresses in MySQL, and shows a confirmation page after a
successful registration.

## How it works

- The servlet is mapped to `/student`.
- A `GET` request to `/student` displays a form for a student's name and email.
- A `POST` request validates that both values are present, then inserts them
  into the `students` table using a prepared SQL statement.
- After a successful insert, the browser is redirected to `success.html`.

## Technologies

- Java Servlets (`javax.servlet` API)
- Maven WAR packaging
- MySQL with MySQL Connector/J
- Apache Tomcat 9

## Database setup

Create the database and table in MySQL before using the registration form:

```sql
CREATE DATABASE studentdb;
USE studentdb;

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);
```

The connection settings are in `src/DBConnection.java`. Configure the JDBC
URL, username, and password for your local MySQL installation there. Do not
commit real database credentials to source control.

## Project files

- `src/StudentController.java` — servlet and registration form; handles form
  submissions and database inserts.
- `src/DBConnection.java` — loads the MySQL JDBC driver and opens connections.
- `src/main/webapp/success.html` — registration confirmation page.
- `src/index.html` — an additional static registration form.
- `WEB-INF/pom.xml` — Maven configuration for a WAR, Servlet API, and MySQL
  Connector/J.
- `WEB-INF/web.xml` — web application metadata and the configured welcome file.
- `apache-tomcat-9.0.105/` — bundled Apache Tomcat distribution.

## Build and run notes

The files currently use a nonstandard Maven layout: the `pom.xml` is under
`WEB-INF/`, while the Java sources and confirmation page are in the sibling
`src/` directory. Maven will not automatically compile or package those
files from that layout. Arrange the sources and web resources under the Maven
project's standard directories (or configure those directories in the POM)
before relying on a Maven build and deploying its WAR to Tomcat.

There is also a Java-version mismatch to resolve: `StudentController.java`
uses a Java text block, while the POM sets the compiler source and target to
Java 8. Use a Java version that supports text blocks and configure Maven
accordingly, or rewrite the text block using Java 8-compatible syntax.

The servlet form is served at `/student`, but `src/index.html` posts to
`StudentController`, which does not match the servlet mapping. Use the
`/student` endpoint if you make that static page the entry point. The POM
declares Servlet API 4.0.1 as provided and includes MySQL Connector/J for the
application.
