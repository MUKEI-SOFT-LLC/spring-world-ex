package jp.co.mukeisoftllc.ex.spring.world.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class FileService {

    @NonNull
    private final Path textPath;
    @NonNull
    private final Path imagePath;
    public FileService(@Autowired @Qualifier("textPath") Path textPath,
                       @Autowired @Qualifier("imagePath") Path imagePath) {
        this.textPath = textPath;
        this.imagePath = imagePath;
    }

    public String readText(String fileName) throws IOException {
        return new String(read(textPath, fileName), StandardCharsets.UTF_8);
    }

    public byte[] readImage(String fileName) throws IOException {
        return read(imagePath, fileName);
    }

    byte[] read(Path path, String fileName) throws IOException {
        final var file = path.resolve(fileName).toAbsolutePath();
        return Files.readAllBytes(file);
    }

    public void writeText(String fileName, String text) throws IOException {
        write(textPath, fileName, text.getBytes(StandardCharsets.UTF_8));
    }

    public void writeImage(String fileName, byte[] content) throws IOException {
        write(imagePath, fileName, content);
    }

    void write(Path path, String fileName, byte[] content) throws IOException {
        final var file = path.resolve(fileName).toAbsolutePath();
        Files.createFile(file);
        Files.write(file, content);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("FileService now initializing...");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("FileService now destroying...");
    }

}
