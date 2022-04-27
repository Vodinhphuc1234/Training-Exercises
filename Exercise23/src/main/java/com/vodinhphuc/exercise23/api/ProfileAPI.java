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

/**
 *
 * @author vodinhphuc
 */
@WebServlet(name = "ProfileAPI", urlPatterns = {"/profile"})
public class ProfileAPI extends HttpServlet {

    HttpUtils<User> httpUtil = new HttpUtils<User>();
    UserService userService = new UserService();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProfileAPI</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProfileAPI at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("id"));

        User user = new User(id, "Vo Dinh Phuc", "PhucVo", "dinhphuc2009", true);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(user);

        out.write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

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

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();

        BufferedReader in = request.getReader();

        String line;

        while ((line = in.readLine()) != null) {
            sb.append(line);
        }

        response.setContentType("application/json");

        ObjectMapper objectMapper = new ObjectMapper();

        User user = objectMapper.readValue(sb.toString(), User.class);

        String json = objectMapper.writeValueAsString(user);

        PrintWriter out = response.getWriter();

        out.print(json);
    }

}
