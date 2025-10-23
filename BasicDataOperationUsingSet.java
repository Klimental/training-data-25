import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Клас BasicDataOperationUsingSet реалізує операції з множиною LinkedHashSet для long.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataAnalysis()} - Запускає аналіз даних.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив long.</li>
 *   <li>{@link #findInArray()} - Пошук значення в масиві long.</li>
 *   <li>{@link #locateMinMaxInArray()} - Знаходить граничні значення в масиві.</li>
 *   <li>{@link #findInSet()} - Пошук значення в множині long.</li>
 *   <li>{@link #locateMinMaxInSet()} - Знаходить мінімальне і максимальне значення в множині.</li>
 *   <li>{@link #analyzeArrayAndSet()} - Аналізує елементи масиву та множини.</li>
 * </ul>
 */
public class BasicDataOperationUsingSet {
    long longValueToSearch;
    Long[] longArray;
    Set<Long> longSet = new LinkedHashSet<Long>();

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param longValueToSearch Значення для пошуку
     * @param longArray Масив long
     */
    BasicDataOperationUsingSet(long longValueToSearch, Long[] longArray) {
        this.longValueToSearch = longValueToSearch;
        this.longArray = longArray;
        this.longSet = new LinkedHashSet<Long>(Arrays.asList(longArray));
    }
    
    /**
     * Запускає комплексний аналіз даних з використанням множини LinkedHashSet.
     * 
     * Метод завантажує дані, виконує операції з множиною та масивом long.
     */
    public void executeDataAnalysis() {
        // спочатку аналізуємо множину дати та часу
        findInSet();
        locateMinMaxInSet();
        analyzeArrayAndSet();

        // потім обробляємо масив
        findInArray();
        locateMinMaxInArray();

        performArraySorting();

        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до файлу
        DataFileHandler.writeArrayToFile(longArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Упорядковує масив об'єктів long за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    private void performArraySorting() {
        long timeStart = System.nanoTime();

        Arrays.sort(longArray);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву дати i часу");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    private void findInArray() {
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
     * Здійснює пошук конкретного значення в множині дати та часу.
     */
    private void findInSet() {
        long timeStart = System.nanoTime();

        boolean elementExists = this.longSet.contains(longValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в LinkedHashSet дати i часу");

        if (elementExists) {
            System.out.println("Елемент '" + longValueToSearch + "' знайдено в LinkedHashSet");
        } else {
            System.out.println("Елемент '" + longValueToSearch + "' відсутній в LinkedHashSet.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в множині long.
     */
    private void locateMinMaxInSet() {
        if (longSet == null || longSet.isEmpty()) {
            System.out.println("LinkedHashSet є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        long minValue = Collections.min(longSet);
        long maxValue = Collections.max(longSet);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в LinkedHashSet");

        System.out.println("Найменше значення в LinkedHashSet: " + minValue);
        System.out.println("Найбільше значення в LinkedHashSet: " + maxValue);
    }

    /**
     * Аналізує та порівнює елементи масиву та множини.
     */
    private void analyzeArrayAndSet() {
        System.out.println("Кiлькiсть елементiв в масивi: " + longArray.length);
        System.out.println("Кiлькiсть елементiв в LinkedHashSet: " + longSet.size());

        boolean allElementsPresent = true;
        for (long longElement : longArray) {
            if (!longSet.contains(longElement)) {
                allElementsPresent = false;
                break;
            }
        }

        if (allElementsPresent) {
            System.out.println("Всi елементи масиву наявні в LinkedHashSet.");
        } else {
            System.out.println("Не всi елементи масиву наявні в LinkedHashSet.");
        }
    }
}