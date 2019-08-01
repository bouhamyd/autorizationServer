package com.api.microservices.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;


@Component
@ConfigurationProperties("auth.cert")
public class CertProperties {

    @NotNull
    private String filePath;

    @NotNull
    private String password;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
