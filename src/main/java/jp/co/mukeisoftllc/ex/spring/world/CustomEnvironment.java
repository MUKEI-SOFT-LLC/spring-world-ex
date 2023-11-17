package jp.co.mukeisoftllc.ex.spring.world;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Properties;

@Component
public class CustomEnvironment {

    private final Properties additionalProperties;

    public CustomEnvironment(@Autowired @Qualifier("additionalProperties") Properties additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public String[] getActiveProfiles() {
        final var profiles = getOrDefault("spring.active.profiles", "test");
        if (profiles.contains(",")) {
            return profiles.split(",");
        }
        return new String[]{profiles};
    }

    public String getProperty(String key) {
        // @Formatter:off
        return switch (key) {
            case "path.for.texts" -> getOrDefault("path.for.texts", "/path/for/texts");
            case "path.for.images" -> getOrDefault("path.for.images", "/path/for/images");
            default -> getOrDefault(key, null);
        };
        // @Formatter:on
    }

    private String getOrDefault(String key, String defaultValue) {
        return Optional.ofNullable(this.additionalProperties.getProperty(key))
                .or(() -> Optional.ofNullable(System.getProperty(key)))
                .orElse(defaultValue);
    }

}
