package jp.co.mukeisoftllc.ex.spring.world.services;

import jp.co.mukeisoftllc.ex.spring.world.Config;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = Config.class)
@ExtendWith({SpringExtension.class})
@ActiveProfiles({"test"})
class FileServiceTest {
    @Autowired
    private FileService fileService;
    @Autowired
    private Config config;

    @BeforeEach
    public void setup() throws IOException {
        Files.createDirectories(config.imagePath());

        Files.createDirectories(config.textPath());
    }

    @AfterEach
    public void clean() throws IOException {
        Files.deleteIfExists(config.imagePath().resolve("testFile"));
        Files.deleteIfExists(config.imagePath());

        Files.deleteIfExists(config.textPath().resolve("testFile"));
        Files.deleteIfExists(config.textPath());
    }

    @Test
    public void testForWritingTextFile() throws IOException {
        // Arrange.
        final var testFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("test.txt")).getFile());
        final var testContent = Files.readString(Path.of(testFile.getPath()));

        // Act.
        assertDoesNotThrow(() -> {
            fileService.writeText("testFile", testContent);
        });

        // Assert.
        final var actual = Files.readString(config.textPath().resolve("testFile"));
        assertEquals(testContent, actual);
    }

    @Test
    public void testForReadingTextFile() throws IOException {
        // Arrange.
        final var testFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("test.txt")).getFile()).toPath();
        Files.copy(testFile, config.textPath().resolve("testFile"));

        // Act && Assert
        assertDoesNotThrow(() -> {
            final var result = fileService.readText("testFile");
            assertEquals(Files.readString(testFile), result);
        });
    }

    @Test
    public void testForWritingImageFile() throws IOException {
        // Arrange.
        final var testFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("test.png")).getFile());
        final var testContent = Files.readAllBytes(Path.of(testFile.getPath()));

        // Act.
        assertDoesNotThrow(() -> {
            fileService.writeImage("testFile", testContent);
        });

        // Assert.
        final var actual = Files.readAllBytes(config.imagePath().resolve("testFile"));
        assertArrayEquals(testContent, actual);
    }

    @Test
    public void testForReadingImageFile() throws IOException {
        // Arrange.
        final var testFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("test.png")).getFile()).toPath();
        Files.copy(testFile, config.imagePath().resolve("testFile"));

        // Act && Assert
        assertDoesNotThrow(() -> {
            final var result = fileService.readImage("testFile");
            assertArrayEquals(Files.readAllBytes(testFile), result);
        });
    }

}
