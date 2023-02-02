package hu.ponte.hr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignConfig {

    @Value("${primaryKey.path}")
    private String primaryKey;

    @Bean
    public String getPrimaryKeyPath() {
        return primaryKey;
    }
}
