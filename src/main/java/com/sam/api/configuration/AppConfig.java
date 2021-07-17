package com.sam.api.configuration;

import com.sam.api.db.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppConfig {
    @NotEmpty
    @Size(min = User.MIN_USERNAME_LENGTH, max = User.MAX_USERNAME_LENGTH)
    private String initialAdminUsername;

    @NotEmpty
    @Size(min = User.MIN_PASSWORD_LENGTH, max = User.MAX_PASSWORD_LENGTH)
    private String initialAdminPassword;
}
