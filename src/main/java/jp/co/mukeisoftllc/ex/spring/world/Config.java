package jp.co.mukeisoftllc.ex.spring.world;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

@Configuration
@ComponentScan("jp.co.mukeisoftllc.ex.spring.world")
public class Config {

    @Autowired
    private Environment env;

    @Bean
    public Path textPath() {
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            return Path.of( "/tmp", "texts");
        } else {
            return Path.of(Objects.requireNonNull(env.getProperty("path.for.texts")));
        }
    }

    @Bean
    public Path imagePath() {
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            return Path.of("/tmp", "images");
        } else {
            return Path.of(Objects.requireNonNull(env.getProperty("path.for.images")));
        }
    }
}
