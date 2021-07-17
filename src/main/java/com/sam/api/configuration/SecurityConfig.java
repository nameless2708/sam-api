package com.sam.api.configuration;

import com.sam.api.db.entity.enums.RoleEnum;
import com.sam.api.security.JsonAccessDeniedHandler;
import com.sam.api.security.JsonAuthenticationFailureHandler;
import com.sam.api.security.JsonLogoutSuccessHandler;
import com.sam.api.security.JsonUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                .accessDeniedHandler(new JsonAccessDeniedHandler());
        http.authorizeRequests()
                .antMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/v2/api-docs/**",
                        "/swagger-resources/**",
                        "/auth/**")
                .permitAll();
        http.authorizeRequests().antMatchers("/demands**").hasAnyAuthority(
                RoleEnum.ROLE_DEPARTMENT_ADMIN.getAuthority(),
                RoleEnum.ROLE_DEPARTMENT_USER.getAuthority());
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterAt(
                new JsonUsernamePasswordAuthenticationFilter(this.authenticationManager()),
                UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();
        http.formLogin().loginProcessingUrl("/auth/login")
                .successForwardUrl("/auth/session")
                .failureHandler(new JsonAuthenticationFailureHandler());
        http.logout().logoutUrl("/auth/logout")
                .logoutSuccessHandler(new JsonLogoutSuccessHandler());
        http.cors();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://stage-sam.devstack.cloud"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH")
                .allowedOrigins(
                        "http://localhost:4200",
                        // TODO move this to config please
                        "https://stage-sam.devstack.cloud"
                );
    }

    @Override
    public void init(WebSecurity web) throws Exception {
        super.init(web);
    }
}
