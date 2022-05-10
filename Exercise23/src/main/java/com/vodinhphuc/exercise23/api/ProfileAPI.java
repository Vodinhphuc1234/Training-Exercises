/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.vodinhphuc.exercise23.api;

import Service.UserService;
import ThriftConnection.User;
import Utils.CookieUtil;
import Utils.HttpUtils;
import Utils.SessionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author vodinhphuc
 */
@WebServlet(name = "ProfileAPI", urlPatterns = {"/api/profile"})
public class ProfileAPI extends HttpServlet {

    HttpUtils<User> httpUtil = new HttpUtils<User>();
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  int id = Integer.valueOf(request.getParameter("id"));

        User user = (User) SessionUtil.getInstance().getValue(request, "user");

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(user);

        response.setStatus(HttpServletResponse.SC_OK);

        out.write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        
        //handle registration
        if (action == null) {
            User user = httpUtil.getModel(request.getReader(), User.class);
            boolean success = false;

            success = userService.register(user);
            if (success) {
                try {
                    response.setStatus(HttpServletResponse.SC_OK);

                    String USessionID = UUID.randomUUID().toString(); //Generates random UUID

                    Base64.Encoder encoder = Base64.getEncoder();
                    String encodedUSessionID = encoder.encodeToString(USessionID.getBytes());

                    user.setName(user.getName());

                    SessionUtil.getInstance().putUserToDB(USessionID, user);

                    CookieUtil.getInstance().setCookie(response, "u_session", encodedUSessionID);
                } catch (SQLException ex) {
                    Logger.getLogger(ProfileAPI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } 
        //hanle checking password
        else if (action.equals("checkpassword")) {
            BufferedReader br = request.getReader();

            String line = br.readLine();

            User user = (User) SessionUtil.getInstance().getValue(request, "user");

            if (BCrypt.checkpw(line, user.getPassword())) {
                response.sendError(HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            User user = httpUtil.getModel(request.getReader(), User.class);

            String action = request.getParameter("action");
            
            if (action != null)
                if (action.equals("changepassword"))
                    user.password=BCrypt.hashpw(user.password, BCrypt.gensalt(12));
            
            boolean success = userService.updateUser(user);

            if (success) {
                SessionUtil.getInstance().putValue(request, "user", user);

                String USessionID = UUID.randomUUID().toString(); //Generates random UUID

                Base64.Encoder encoder = Base64.getEncoder();
                String encodedUSessionID = encoder.encodeToString(USessionID.getBytes());

                SessionUtil.getInstance().putUserToDB(USessionID, user);

                CookieUtil.getInstance().setCookie(response, "u_session", encodedUSessionID);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (TException ex) {
            Logger.getLogger(ProfileAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProfileAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
