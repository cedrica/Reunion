package com.reunion.filter;
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
import javax.servlet.http.HttpSession;

import com.reunion.util.SessionUtil;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class FiltreDAutorisation implements Filter {
    
   public FiltreDAutorisation() {
   }

   @Override
   public void init(FilterConfig filterConfig) throws ServletException {
        
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {

           // check whether session variable is set
           HttpServletRequest req = (HttpServletRequest) request;
           HttpServletResponse res = (HttpServletResponse) response;
           HttpSession ses = req.getSession(false);
           String reqURI = req.getRequestURI();
           if ( reqURI.indexOf("/index.xhtml") >= 0 || (ses != null && ses.getAttribute(SessionUtil.MEMBRE_ACTUEL) != null)
                                      || reqURI.indexOf("/public/") >= 0 || reqURI.contains("javax.faces.resource") )
                  chain.doFilter(request, response);
           else   // user didn't log in but asking for a page that is not allowed so take user to login page
                  res.sendRedirect(req.getContextPath() + "/index.xhtml");  // Anonymous user. Redirect to login page
     }
    catch(Throwable t) {
        System.out.println( t.getMessage());
    }
   } //doFilter

   @Override
   public void destroy() {
        
   }
}