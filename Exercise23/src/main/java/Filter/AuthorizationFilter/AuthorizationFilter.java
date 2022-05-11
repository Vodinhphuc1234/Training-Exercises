/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Filter.AuthorizationFilter;

import ThriftConnection.User;
import Utils.SessionUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vodinhphuc
 */
@WebFilter("/profile")
public class AuthorizationFilter implements Filter{

    @Override
    public void init(FilterConfig fc) throws ServletException {}

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) 
            throws IOException, ServletException {
        User user = (User) SessionUtil.getInstance().getValue((HttpServletRequest) sr, "user");
        
        if (user != null){
            fc.doFilter(sr, sr1);
        }
        else{
            HttpServletResponse httpServletResponse = (HttpServletResponse) sr1;
            httpServletResponse.sendRedirect("/login?alert=permissionDenied");
        }
    }

    @Override
    public void destroy() {}
    
}
