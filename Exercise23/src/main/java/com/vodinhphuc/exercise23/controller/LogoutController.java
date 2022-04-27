/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.vodinhphuc.exercise23.controller;

import ThriftConnection.User;
import Utils.CookieUtil;
import Utils.SessionUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vodinhphuc
 */
@WebServlet(name = "LogoutController", urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        try {
            String USessionID = CookieUtil.getInstance().getCookie(request, "u_session");
            Base64.Decoder decoder = Base64.getDecoder();
            
            byte[] decodedBytes = decoder.decode(USessionID);
            String DecodedUSessionID = new String(decodedBytes);
            
            User savedUser = SessionUtil.getInstance().getUserFromDB(DecodedUSessionID);
            
            SessionUtil.getInstance().removeUserFromDB(DecodedUSessionID);
            CookieUtil.getInstance().removeCookie(response, "u_session");
            SessionUtil.getInstance().removeValue(request, "user");
            response.sendRedirect("/");
        } catch (SQLException ex) {
            Logger.getLogger(LogoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
