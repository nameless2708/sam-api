package com.sam.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sam.api.service.auth.dto.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.BufferedReader;
import java.lang.invoke.MethodHandles;
import java.util.Set;

public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ObjectMapper objectMapper;

    private final Validator validator;

    private final AuthenticationManager authenticationManager;


    public JsonUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = new ObjectMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.setFilterProcessesUrl("/auth/login");
        this.setAuthenticationSuccessHandler(new ForwardAuthenticationSuccessHandler("/auth/session"));
        this.setAuthenticationFailureHandler(new JsonAuthenticationFailureHandler());
        logger.info("Init json auth filter");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginRequest loginRequest;
        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String parsedReq = sb.toString();
            loginRequest = objectMapper.readValue(parsedReq, LoginRequest.class);
        } catch (Exception exception) {
            throw new AuthenticationServiceException("Failed to parse authentication request body");
        }
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        if (violations.size() > 0) {
            throw new AuthenticationServiceException("Invalid request");
        }
        return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    }
}
