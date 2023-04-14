package com.aixo.demoshop.configuration;
import com.aixo.demoshop.model.CustomUserDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetail user = ( CustomUserDetail ) authentication.getPrincipal();
        if(user.getAuthorities().stream().map(a ->a.getAuthority()).filter(a ->a.equals("ROLE_ADMIN")).findFirst().isPresent()){
            redirectStrategy.sendRedirect(request, response, "/admin");
        }else{
            redirectStrategy.sendRedirect(request, response, "/cart");
        }
    }
}
