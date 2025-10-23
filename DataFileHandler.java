import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


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
        Long[] temporaryArray = new Long[1000];
        int currentIndex = 0;

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = fileReader.readLine()) != null) {
                // Видаляємо можливі невидимі символи та BOM
                currentLine = currentLine.trim().replaceAll("^\\uFEFF", "");
                if (!currentLine.isEmpty()) {
                    long parsedlong = Long.parseLong(currentLine);
                    temporaryArray[currentIndex++] = parsedlong;
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Long[] resultArray = new Long[currentIndex];
        System.arraycopy(temporaryArray, 0, resultArray, 0, currentIndex);

        return resultArray;
    }

    /**
     * Зберігає масив об'єктів long у файл.
     * 
     * @param longArray Масив об'єктів long.
     * @param filePath Шлях до файлу для збереження.
     */
    public static void writeArrayToFile(Long[] longArray, String filePath) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (Long longElement : longArray) {
                fileWriter.write(longElement.toString());
                fileWriter.newLine();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
