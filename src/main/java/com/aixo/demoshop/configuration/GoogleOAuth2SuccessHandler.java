package com.aixo.demoshop.configuration;

import com.aixo.demoshop.model.Role;
import com.aixo.demoshop.model.User;
import com.aixo.demoshop.repository.RoleRepository;
import com.aixo.demoshop.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        System.out.println(token.toString());
        String email = token.getPrincipal().getAttributes().get("email").toString();
        Set<GrantedAuthority> roles = token.getAuthorities().stream().filter(a -> !a.getAuthority().contains("https")).map(
                a -> {
                    if(a.getAuthority().contains("OIDC")){
                        return new SimpleGrantedAuthority(a.getAuthority().replace("OIDC","ROLE"));
                    }else{
                        return new SimpleGrantedAuthority(a.getAuthority().replace("SCOPE","ROLE"));
                    }
                }
        ).collect(Collectors.toSet());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email,"",roles);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        System.out.println("CURRENT AUTHENTICATED USER : "+ SecurityContextHolder.getContext().getAuthentication().toString());
        /*
        if(userRepository.findUserByEmail(email).isPresent()){


        } else {
            User user = new User();
            user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
            user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
            user.setEmail(email);
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findById(2).get());
            roles = roleRepository.saveAll(roles);
            user.setRoles(roles);
            userRepository.save(user);
        }*/
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/cart");
    }
}
