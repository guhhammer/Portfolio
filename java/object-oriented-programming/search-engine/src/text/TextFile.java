package text;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Reads a whole document from disk. */
public class TextFile {

    public static String read(String folder, String fileName) {
        Path path = Paths.get(folder, fileName);
        try {
            return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("cannot read " + path, e);
        }
    }
}
