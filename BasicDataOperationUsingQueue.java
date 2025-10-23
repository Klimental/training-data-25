import java.util.Queue;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Клас BasicDataOperationUsingQueue реалізує роботу з колекціями типу Queue для long.
 * 
 * <p>Основні функції класу:</p>
 * <ul>
 *   <li>{@link #runDataProcessing()} - Запускає комплекс операцій з даними.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив long.</li>
 *   <li>{@link #findInArray()} - Пошук значення в масиві long.</li>
 *   <li>{@link #locateMinMaxInArray()} - Знаходить мінімальне і максимальне значення в масиві.</li>
 *   <li>{@link #findInQueue()} - Пошук значення в черзі long.</li>
 *   <li>{@link #locateMinMaxInQueue()} - Знаходить граничні значення в черзі.</li>
 *   <li>{@link #performQueueOperations()} - Виконує операції peek і poll з чергою.</li>
 * </ul>
 * 
 */
public class BasicDataOperationUsingQueue {
    private long longValueToSearch;
    private Long[] longArray;
    private Queue<Long> longQueue;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param longValueToSearch Значення для пошуку
     * @param longArray Масив long
     */
    BasicDataOperationUsingQueue(long longValueToSearch, Long[] longArray) {
        this.longValueToSearch = longValueToSearch;
        this.longArray = longArray;
        this.longQueue = new PriorityQueue<Long>(Arrays.asList(longArray));
    }
    
    /**
     * Запускає комплексну обробку даних з використанням черги.
     * 
     * Метод завантажує дані, виконує операції з чергою та масивом long.
     */
    public void runDataProcessing() {
        // спочатку обробляємо чергу дати та часу
        findInQueue();
        locateMinMaxInQueue();
        performQueueOperations();

        // потім працюємо з масивом
        findInArray();
        locateMinMaxInArray();

        performArraySorting();

        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до файлу
        DataFileHandler.writeArrayToFile(longArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив об'єктiв long та виводить початковий i вiдсортований масиви.
     * Вимiрює та виводить час, витрачений на сортування масиву в наносекундах.
     */
    private void performArraySorting() {
        // вимірюємо тривалість упорядкування масиву дати та часу
        long timeStart = System.nanoTime();

        Arrays.sort(longArray);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву дати i часу");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    private void findInArray() {
        // відстежуємо час виконання пошуку в масиві
        long timeStart = System.nanoTime();
        
        int position = Arrays.binarySearch(this.longArray, longValueToSearch);
        
        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в масивi дати i часу");

        if (position >= 0) {
            System.out.println("Елемент '" + longValueToSearch + "' знайдено в масивi за позицією: " + position);
        } else {
            System.out.println("Елемент '" + longValueToSearch + "' відсутній в масиві.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в масиві long.
     */
    private void locateMinMaxInArray() {
        if (longArray == null || longArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        // відстежуємо час на визначення граничних значень
        long timeStart = System.nanoTime();

        long minValue = longArray[0];
        long maxValue = longArray[0];

        for (long currentlong : longArray) {
            if (longValueToSearch  < minValue) {
                minValue = currentlong;
            }
            if (longValueToSearch  > maxValue) {
                maxValue = currentlong;
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в масивi");

        System.out.println("Найменше значення в масивi: " + minValue);
        System.out.println("Найбільше значення в масивi: " + maxValue);
    }

    /**
     * Здійснює пошук конкретного значення в черзі дати та часу.
     */
    private void findInQueue() {
        // вимірюємо час пошуку в черзі
        long timeStart = System.nanoTime();

        boolean elementExists = this.longQueue.contains(longValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в Queue дати i часу");

        if (elementExists) {
            System.out.println("Елемент '" + longValueToSearch + "' знайдено в Queue");
        } else {
            System.out.println("Елемент '" + longValueToSearch + "' відсутній в Queue.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в черзі long.
     */
    private void locateMinMaxInQueue() {
        if (longQueue == null || longQueue.isEmpty()) {
            System.out.println("Черга є пустою або не ініціалізованою.");
            return;
        }

        // відстежуємо час пошуку граничних значень
        long timeStart = System.nanoTime();

        long minValue = Collections.min(longQueue);
        long maxValue = Collections.max(longQueue);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в Queue");

        System.out.println("Найменше значення в Queue: " + minValue);
        System.out.println("Найбільше значення в Queue: " + maxValue);
    }

    /**
     * Виконує операції peek і poll з чергою long.
     */
    private void performQueueOperations() {
        if (longQueue == null || longQueue.isEmpty()) {
            System.out.println("Черга є пустою або не ініціалізованою.");
            return;
        }

        long headElement = longQueue.peek();
        System.out.println("Головний елемент черги (peek): " + headElement);

        headElement = longQueue.poll();
        System.out.println("Видалений елемент черги (poll): " + headElement);

        headElement = longQueue.peek();
        System.out.println("Новий головний елемент черги: " + headElement);
    }
}