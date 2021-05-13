package com.example.ProjectBackEnd.Controller;

import com.example.ProjectBackEnd.Model.ErrorResponse;
import com.example.ProjectBackEnd.Model.User;
import com.example.ProjectBackEnd.Model.UserQueryModel;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SignupServlet", value = "/SignupServlet")
public class SignupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        response.setContentType("application/json");
        Middleware.setCORS(request, response);
        try {
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (firstname == null || lastname == null || username == null || password == null ){
                ErrorResponse errorResponse = new ErrorResponse("All blank need to be filled", 400);
                response.setStatus(400);
                 out.print(gson.toJson(errorResponse));
                 return;
            }

            UserQueryModel userQueryModel = new UserQueryModel();
            User existingUser = userQueryModel.getUser(username);
            if (existingUser != null){
                ErrorResponse errorResponse = new ErrorResponse("Username " + username + " is used", 400);
                response.setStatus(400);
                out.print(gson.toJson(errorResponse));
                return;
            }

            User user = userQueryModel.insetNewUser(firstname, lastname, username, password);
            String jsonOfUser = gson.toJson(user);
            response.setStatus(201);
            request.getSession(true);
            out.print(jsonOfUser);
        }catch(Exception e){
            ErrorResponse errorResponse = new ErrorResponse(e.toString(), 500);
            response.setStatus(500);
            out.print(gson.toJson(errorResponse));
        }
    }

    protected void doOption(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Middleware.setCORS(request, response);
        super.doOptions(request, response);
    }
}
