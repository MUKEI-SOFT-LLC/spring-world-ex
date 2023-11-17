package jp.co.mukeisoftllc.ex.spring.world;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class TestConfigurer {
    @Bean
    @Qualifier("additionalProperties")
    public Properties testProperties() {
        final var result = new Properties();
        result.setProperty("spring.active.profiles", "test");
        return result;
    }
}
