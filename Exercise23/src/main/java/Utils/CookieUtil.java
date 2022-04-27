/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vodinhphuc
 */
public class CookieUtil {
    private static CookieUtil instance;
    
    public static CookieUtil getInstance(){
        if (instance == null)
            instance = new CookieUtil();
        return instance;
    }
    
    private CookieUtil(){
        
    }
    
    public String getCookie (HttpServletRequest httpReq, String key){
        Cookie[] cookies = httpReq.getCookies();
        
        for (Cookie cookie : cookies){
            if (cookie.getName().equals(key))
                return cookie.getValue();
        }
        return null;
    }
    
    public void setCookie (HttpServletResponse response, String key, String value){
        Cookie cookie = new Cookie(key, value);
        
        cookie.setMaxAge(24*60*60);
        
        response.addCookie(cookie);
    }
    
    public void removeCookie (HttpServletResponse response, String key){
        Cookie cookie = new Cookie(key, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
