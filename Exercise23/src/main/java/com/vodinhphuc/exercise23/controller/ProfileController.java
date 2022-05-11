/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vodinhphuc.exercise23.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vodinhphuc
 */

@WebServlet (name = "ProfileController", urlPatterns = "/profile")
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String action = req.getParameter("action");
        
        RequestDispatcher rd;
        
        if (action != null)
        {
        if (action.equals("update"))
           rd = req.getRequestDispatcher("/views/ProfileUpdating.jsp");
        else if (action.equals("view"))
            rd = req.getRequestDispatcher("/views/Profile.jsp");
        else if (action.equals("changepassword"))
            rd = req.getRequestDispatcher("/views/ProfileChangingPassword.jsp");
        else
            rd = req.getRequestDispatcher("/views/Profile.jsp");
        } else rd = req.getRequestDispatcher("/views/Profile.jsp");
        rd.forward(req, resp);
    }
    
}
