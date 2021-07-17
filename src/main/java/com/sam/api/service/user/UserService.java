package com.sam.api.service.user;

import com.sam.api.configuration.AppConfig;
import com.sam.api.db.entity.User;
import com.sam.api.db.entity.enums.RoleEnum;
import com.sam.api.db.repository.UserRepository;
import com.sam.api.service.user.dto.UserCreateRequest;
import com.sam.api.service.user.dto.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserResponse findById(int id) {
        return null;
    }

    public void create(UserCreateRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        this.userRepository.save(user);
    }

    public void initAdmin() {
        boolean present = this.userRepository.findByUsername(this.appConfig.getInitialAdminUsername()).isPresent();
        if (present) {
            logger.info("Admin user already setup");
            return;
        }
        User user = new User();
        user.setName("Administrator");
        user.setUsername(this.appConfig.getInitialAdminUsername());
        user.setPassword(passwordEncoder.encode(this.appConfig.getInitialAdminPassword()));
        user.setRole(RoleEnum.ROLE_SYSTEM_ADMIN);
        this.userRepository.save(user);
        logger.info("Setup initial admin user completed");
    }
}
