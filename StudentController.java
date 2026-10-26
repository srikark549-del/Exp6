package com.example.controller;

import com.example.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/student")
public class StudentController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("""
            <!DOCTYPE html>
            <html>
            <head>
                <title>Student Registration</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 40px; }
                    form { max-width: 400px; }
                    input { margin-bottom: 10px; width: 100%; padding: 8px; }
                </style>
            </head>
            <body>
                <h1>Student Registration</h1>
                <form method="post">
                    <div>
                        <label for="name">Name:</label>
                        <input type="text" id="name" name="name" required>
                    </div>
                    <div>
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" required>
                    </div>
                    <div>
                        <input type="submit" value="Register">
                    </div>
                </form>
            </body>
            </html>
            """);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        if (name == null || name.trim().isEmpty() || 
            email == null || email.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
                "Name and email are required");
            return;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO students (name, email) VALUES (?, ?)")) {
            
            stmt.setString(1, name.trim());
            stmt.setString(2, email.trim());
            stmt.executeUpdate();
            
            response.sendRedirect("success.html");
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Database error occurred. Please try again later.");
        }
    }
}