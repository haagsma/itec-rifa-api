package br.com.itec.rifa.filters;

import br.com.itec.rifa.services.JwtService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean {

       private Logger logger = Logger.getLogger(JwtAuthenticationFilter.class);

       @Autowired
       private JwtService jwtService;

       @Override
       public void doFilter(
               ServletRequest request,
               ServletResponse response,
               FilterChain filterChain
       ) throws IOException, ServletException {
           HttpServletRequest req = (HttpServletRequest) request;
           try {
               Authentication authentication = JwtService.verifyRequest((HttpServletRequest) request);
               SecurityContextHolder.getContext().setAuthentication(authentication);
               filterChain.doFilter(request, response);
           } catch (Exception e) {
               e.printStackTrace();

               logger.warn("Token inválido: " + e + "\nPath: " + req.getRequestURI());
               HttpServletResponse res = (HttpServletResponse) response;
               res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
           }
       }

}
