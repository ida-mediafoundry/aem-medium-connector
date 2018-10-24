package be.ida;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestResourceUtil {
    public static String getRawTestResource(String fileName) throws IOException {
        Path path = Paths.get("src", "test", "resources", fileName);
        byte[] fileBytes = Files.readAllBytes(path);
        return new String(fileBytes);
    }
}
