package main.java.ru.clevertec.check.util;

import main.java.ru.clevertec.check.exception.CustomException;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Utility {
    public static String readFile(String path) {
        try {
            return Files.readString(Path.of(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new CustomException("INTERNAL SERVER ERROR", "File not read : " + path ,e);
        }
    }

    public static void saveFile(String path, String content) {
        try (var writer = new PrintWriter(path, StandardCharsets.UTF_8)) {
            writer.println(content);
            writer.flush();
        } catch (IOException e) {
            throw new CustomException("INTERNAL SERVER ERROR", "The file is not written : " + path ,e);
        }
    }
}
