package tech.paramount.andrzejvimn.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class InputReader {

    private InputReader() {}

    public static List<String> readLines(String filename) {
        try (var lines = Files.lines(Path.of(ClassLoader.getSystemResource(filename).toURI()))) {
            return lines.toList();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
