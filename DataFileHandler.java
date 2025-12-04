import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Клас DataFileHandler управляє роботою з файлами даних long.
 */
public class DataFileHandler {

    /**
     * Завантажує масив об'єктів long з файлу.
     *
     * @param filePath Шлях до файлу з даними.
     * @return Масив об'єктів long.
     */
    public static Long[] loadArrayFromFile(String filePath) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            return fileReader.lines()
                    // прибираємо пробіли та можливий BOM на початку рядка
                    .map(currentLine -> currentLine.trim().replaceAll("^\\uFEFF", ""))
                    // ігноруємо порожні рядки
                    .filter(currentLine -> !currentLine.isEmpty())
                    // конвертуємо текст у Long
                    .map(Long::parseLong)
                    // збираємо в масив Long[]
                    .toArray(Long[]::new);
        } catch (IOException ioException) {
            throw new RuntimeException("Помилка читання даних з файлу: " + filePath, ioException);
        }
    }

    /**
     * Записує масив об'єктів long у файл.
     *
     * @param longArray Масив об'єктів long.
     * @param filePath  Шлях до файлу для збереження.
     */
    public static void writeArrayToFile(Long[] longArray, String filePath) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            String content = Arrays.stream(longArray)
                    // конвертуємо long у String
                    .map(String::valueOf)
                    // об'єднуємо всі елементи через розрив рядка
                    .collect(Collectors.joining(System.lineSeparator()));
            fileWriter.write(content);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
