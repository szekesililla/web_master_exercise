package hu.ponte.hr.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Configuration
public class SignConfig {

    @Value("${primaryKey.path}")
    private String primaryKeyPath;
    private byte[] primaryKey;

    @Bean
    public byte[] getPrimaryKey() {
        if (primaryKey == null) {
            try {
                primaryKey = Files.readAllBytes(Paths.get(primaryKeyPath));
            } catch (IOException ioException) {
                log.error("Couldn't read primary key from file.", ioException);
            }
        }
        return primaryKey;
    }
}
