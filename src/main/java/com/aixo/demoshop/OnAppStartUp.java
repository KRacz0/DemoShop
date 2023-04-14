package com.aixo.demoshop;

import com.aixo.demoshop.model.Role;
import com.aixo.demoshop.model.User;
import com.aixo.demoshop.repository.RoleRepository;
import com.aixo.demoshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class OnAppStartUp implements ApplicationListener<ApplicationStartedEvent> {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        Role role = roleRepository.findByName("ROLE_USER");
        if(roleRepository.findByName("ROLE_USER") == null){
            role = new Role();
            role.setName("ROLE_USER");
            role = roleRepository.save(role);
        }
        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
        if(roleRepository.findByName("ROLE_ADMIN") == null){
            roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleAdmin = roleRepository.save(roleAdmin);
        }
        Role roleOpenId = roleRepository.findByName("ROLE_openId");
        if(roleOpenId == null){
            roleOpenId = new Role();
            roleOpenId.setName("ROLE_openId");
            roleOpenId = roleRepository.save(roleOpenId);
        }
        if(userRepository.findByEmail("user@user1").isEmpty() ){
            User user = new User();
            user.setEmail("user@user1");
            user.setRoles(Arrays.asList(role,roleOpenId));
            user.setFirstName("user1");
            user.setLastName("user1");
            user.setPassword(passwordEncoder.encode("user"));
            userRepository.save(user);
        }
        if(userRepository.findByEmail("admin@admin").isEmpty() ){
            User user = new User();
            user.setEmail("admin@admin");
            user.setRoles(Arrays.asList(roleAdmin,roleOpenId));
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(user);
        }
    }
}
