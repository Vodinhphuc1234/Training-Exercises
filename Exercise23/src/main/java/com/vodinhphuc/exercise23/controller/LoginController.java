package com.vodinhphuc.exercise23.controller;

import Cache.CacheHelper;
import Messenger.Message;
import Service.UserService;
import ThriftConnection.User;
import Utils.CookieUtil;
import Utils.HttpUtils;
import Utils.SessionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vodinhphuc
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private CacheHelper cacheHelper;
    UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        userService = new UserService();
        Cookie[] cookies = request.getCookies();
        boolean loggedIn = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("u_session")) {

                    try {
                        String USessionID = CookieUtil.getInstance()
                                .getCookie(request, "u_session");

                        Decoder decoder = Base64.getDecoder();

                        byte[] decodedBytes = decoder.decode(USessionID);
                        String DecodedUSessionID = new String(decodedBytes);

                        User savedUser = SessionUtil.getInstance()
                                .getUserFromDB(DecodedUSessionID);

                        User user = userService.findUser(
                                savedUser.getUsername(), savedUser.getPassword());

                        if (user != null) {
                            loggedIn = true;
                            SessionUtil.getInstance().putValue(request, "user", user);
                            response.sendRedirect("/home");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        if (!loggedIn) {

            try {
                RequestDispatcher rd = request.getRequestDispatcher("views/Login.jsp");
                rd.forward(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService = new UserService();

        try {
            HttpUtils<User> httpUtil = new HttpUtils<>();
            User user = httpUtil.getModel(request.getReader(), User.class);

            UserService userService = new UserService();

            User loggedUser = null;
            loggedUser = userService.findUser(user.getUsername(), user.getPassword());

            if (loggedUser != null) {
                response.setStatus(HttpServletResponse.SC_OK);

                String USessionID = UUID.randomUUID().toString(); //Generates random UUID

                Encoder encoder = Base64.getEncoder();
                String encodedUSessionID = encoder.encodeToString(USessionID.getBytes());

                user.setName(loggedUser.getName());

                SessionUtil.getInstance().putUserToDB(USessionID, user);

                CookieUtil.getInstance().setCookie(response, "u_session", encodedUSessionID);
            } else {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                Message message = new Message(501, "Error, Username or password is wrong", "");
                response.getWriter().print(new ObjectMapper().writeValueAsString(message));

            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
