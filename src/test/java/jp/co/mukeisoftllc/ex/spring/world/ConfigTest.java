package jp.co.mukeisoftllc.ex.spring.world;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = Config.class)
@ExtendWith({SpringExtension.class})
@ActiveProfiles({"test"})
class ConfigTest {

    private static final String PATH_SEPARATOR = System.getProperty("file.separator");

    @Autowired
    private Config config;

    @Test
    public void testForTextPathConfigured() {
        final var expected = PATH_SEPARATOR + "tmp" + PATH_SEPARATOR + "texts";
        assertEquals(expected, config.textPath().toFile().getPath());
    }

    @Test
    public void testForImagePathConfigured() {
        final var expected = PATH_SEPARATOR + "tmp" + PATH_SEPARATOR + "images";
        assertEquals(expected, config.imagePath().toFile().getPath());
    }

}