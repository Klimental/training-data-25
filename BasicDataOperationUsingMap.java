import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Клас BasicDataOperationUsingMap реалізує операції з колекціями типу Map для зберігання пар ключ-значення.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними Map.</li>
 *   <li>{@link #findByKey()} - Здійснює пошук елемента за ключем в Map.</li>
 *   <li>{@link #findByValue()} - Здійснює пошук елемента за значенням в Map.</li>
 *   <li>{@link #addEntry()} - Додає новий запис до Map.</li>
 *   <li>{@link #removeByKey()} - Видаляє запис з Map за ключем.</li>
 *   <li>{@link #removeByValue()} - Видаляє записи з Map за значенням.</li>
 *   <li>{@link #sortByKey()} - Сортує Map за ключами.</li>
 *   <li>{@link #sortByValue()} - Сортує Map за значеннями.</li>
 * </ul>
 */
public class BasicDataOperationUsingMap {
    private final Sheep KEY_TO_SEARCH_AND_DELETE = new Sheep("Кудря", 22.3);
    private final Sheep KEY_TO_ADD = new Sheep("М'ята", 24.8);

    private final String VALUE_TO_SEARCH_AND_DELETE = "Андрій";
    private final String VALUE_TO_ADD = "Ірина";


    private Hashtable<Sheep, String> hashtable;
    private HashMap<Sheep, String> hashMap;

    /**
     * Компаратор для сортування Map.Entry за значеннями String.
     * Використовує метод String.compareTo() для порівняння імен власників.
     */
    static class OwnerValueComparator implements Comparator<Map.Entry<Sheep, String>> {
        @Override
        public int compare(Map.Entry<Sheep, String> e1, Map.Entry<Sheep, String> e2) {
            String v1 = e1.getValue();
            String v2 = e2.getValue();
            if (v1 == null && v2 == null) return 0;
            if (v1 == null) return -1;
            if (v2 == null) return 1;
            return v1.compareTo(v2);
        }
    }

    /**
     * Внутрішній клас Sheep для зберігання інформації про домашню тварину.
     * 
     * Реалізує Comparable<Sheep> для визначення природного порядку сортування.
     * Природний порядок: спочатку за кличкою (nickname) за зростанням, потім за видом (woolLength) за спаданням.
     */
    public static class Sheep implements Comparable<Sheep> {
        private final String nickname;
        private final Double woolLength;

        public Sheep(String nickname) {
            this.nickname = nickname;
            this.woolLength = null;
        }

        public Sheep(String nickname, Double woolLength) {
            this.nickname = nickname;
            this.woolLength = woolLength;
        }

        public String getNickname() { 
            return nickname; 
        }

        public Double getWoolLength() {
            return woolLength;
        }

        /**
         * Порівнює цей об'єкт Sheep з іншим для визначення порядку сортування.
         * Природний порядок: спочатку за кличкою (nickname) за зростанням, потім за видом (woolLength) за спаданням.
         * 
         * @param other Sheep об'єкт для порівняння
         * @return негативне число, якщо цей Sheep < other; 
         *         0, якщо цей Sheep == other; 
         *         позитивне число, якщо цей Sheep > other
         * 
         * Критерій порівняння: поля nickname (кличка) за зростанням та woolLength (вид) за спаданням.
         * 
         * Цей метод використовується:
         * - HashMap для автоматичного сортування ключів Sheep за nickname (зростання), потім за woolLength (спадання)
         * - Collections.sort() для сортування Map.Entry за ключами Sheep
         * - Collections.binarySearch() для пошуку в відсортованих колекціях
         */
        @Override
        public int compareTo(Sheep other) {
            if (other == null) return 1;
            
            // Спочатку порівнюємо за кличкою
            int nicknameComparison = 0;
            if (this.nickname == null && other.nickname == null) {
                nicknameComparison = 0;
            } else if (this.nickname == null) {
                nicknameComparison = -1;
            } else if (other.nickname == null) {
                nicknameComparison = 1;
            } else {
                nicknameComparison = other.nickname.compareTo(this.nickname);
            }
            
            // Якщо клички різні, повертаємо результат
            if (nicknameComparison != 0) {
                return nicknameComparison;
            }
            
            // Якщо клички однакові, порівнюємо за розміром вовни (за спаданням - інвертуємо результат)
            if (this.woolLength == null && other.woolLength == null) return 0;
            if (this.woolLength == null) return 1;  // null йде в кінець при спаданні
            if (other.woolLength == null) return -1;
            return other.woolLength.compareTo(this.woolLength);  // Інвертоване порівняння для спадання
        }

        /**
         * Перевіряє рівність цього Sheep з іншим об'єктом.
         * Два Sheep вважаються рівними, якщо їх клички (nickname) та види (woolLength) однакові.
         * 
         * @param obj об'єкт для порівняння
         * @return true, якщо об'єкти рівні; false в іншому випадку
         * 
         * Критерій рівності: поля nickname (кличка) та woolLength (довжина вовни).
         * 
         * Важливо: метод узгоджений з compareTo() - якщо equals() повертає true,
         * то compareTo() повертає 0, оскільки обидва методи порівнюють за nickname та woolLength.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Sheep sheep = (Sheep) obj;
            
            boolean nicknameEquals = nickname != null ? nickname.equals(sheep.nickname) : sheep.nickname == null;
            boolean woolLengthEquals = woolLength != null ? woolLength.equals(sheep.woolLength) : sheep.woolLength == null;
            
            return nicknameEquals && woolLengthEquals;
        }

        /**
         * Повертає хеш-код для цього Sheep.
         * 
         * @return хеш-код, обчислений на основі nickname та woolLength
         * 
         * Базується на полях nickname та woolLength для узгодженості з equals().
         * 
         * Важливо: узгоджений з equals() - якщо два Sheep рівні за equals()
         * (мають однакові nickname та woolLength), вони матимуть однаковий hashCode().
         */
        @Override
        public int hashCode() {
            // Початкове значення: хеш-код поля nickname (або 0, якщо nickname == null)
            int result = nickname != null ? nickname.hashCode() : 0;
            
            // Комбінуємо хеш-коди полів за формулою: result = 31 * result + hashCode(поле)
            // Множник 31 - просте число, яке дає хороше розподілення хеш-кодів
            // і оптимізується JVM як (result << 5) - result
            // Додаємо хеш-код виду (або 0, якщо woolLength == null) до загального результату
            result = 31 * result + (woolLength != null ? woolLength.hashCode() : 0);
            
            return result;
        }

        /**
         * Повертає строкове представлення Sheep.
         * 
         * @return кличка тварини (nickname), Довжина вовни (woolLength) та hashCode
         */
        @Override
        public String toString() {
            if (woolLength != null) {
                return "Sheep{nickname='" + nickname + "', woolLength='" + woolLength + "', hashCode=" + hashCode() + "}";
            }
            return "Sheep{nickname='" + nickname + "', hashCode=" + hashCode() + "}";
        }
    }

    /**
     * Конструктор, який ініціалізує об'єкт з готовими даними.
     * 
     * @param hashtable Hashtable з початковими даними (ключ: Sheep, значення: ім'я власника)
     * @param hashMap HashMap з початковими даними (ключ: Sheep, значення: ім'я власника)
     */
    BasicDataOperationUsingMap(Hashtable<Sheep, String> hashtable, HashMap<Sheep, String> hashMap) {
        this.hashtable = hashtable;
        this.hashMap = hashMap;
    }
    
    /**
     * Виконує комплексні операції з Map.
     * 
     * Метод виконує різноманітні операції з Map: пошук, додавання, видалення та сортування.
     */
    public void executeDataOperations() {
        // Спочатку працюємо з Hashtable
        System.out.println("========= Операції з Hashtable =========");
        System.out.println("Початковий розмір Hashtable: " + hashtable.size());
        
        // Пошук до сортування
        findByKeyInHashtable();
        findByValueInHashtable();

        printHashtable();
        sortHashtable();
        printHashtable();

        // Пошук після сортування
        findByKeyInHashtable();
        findByValueInHashtable();

        addEntryToHashtable();
        
        removeByKeyFromHashtable();
        removeByValueFromHashtable();
               
        System.out.println("Кінцевий розмір Hashtable: " + hashtable.size());

        // Потім обробляємо HashMap
        System.out.println("\n\n========= Операції з HashMap =========");
        System.out.println("Початковий розмір HashMap: " + hashMap.size());
        
        findByKeyInHashMap();
        findByValueInHashMap();

        printHashMap();

        addEntryToHashMap();
        
        removeByKeyFromHashMap();
        removeByValueFromHashMap();
        
        System.out.println("Кінцевий розмір HashMap: " + hashMap.size());
    }


    // ===== Методи для Hashtable =====

    /**
     * Виводить вміст Hashtable без сортування.
     * Hashtable не гарантує жодного порядку елементів.
     */
    private void printHashtable() {
        System.out.println("\n=== Пари ключ-значення в Hashtable ===");
        long timeStart = System.nanoTime();

        for (Map.Entry<Sheep, String> entry : hashtable.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пари ключ-значення в Hashtable");
    }

    /**
     * Сортує Hashtable за ключами.
     * Використовує Collections.sort() з природним порядком Sheep (Sheep.compareTo()).
     * Перезаписує hashtable відсортованими даними.
     */
    private void sortHashtable() {
        long timeStart = System.nanoTime();

        // Створюємо список ключів і сортуємо за природним порядком Sheep
        List<Sheep> sortedKeys = new ArrayList<>(hashtable.keySet());
        Collections.sort(sortedKeys);
        
        // Створюємо нову Hashtable з відсортованими ключами
        Hashtable<Sheep, String> sortedHashtable = new Hashtable<>();
        for (Sheep key : sortedKeys) {
            sortedHashtable.put(key, hashtable.get(key));
        }
        
        // Перезаписуємо оригінальну hashtable
        hashtable = sortedHashtable;

        PerformanceTracker.displayOperationTime(timeStart, "сортування Hashtable за ключами");
    }

    /**
     * Здійснює пошук елемента за ключем в Hashtable.
     * Використовує Sheep.hashCode() та Sheep.equals() для пошуку.
     */
    void findByKeyInHashtable() {
        long timeStart = System.nanoTime();

        boolean found = hashtable.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в Hashtable");

        if (found) {
            String value = hashtable.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в Hashtable.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в Hashtable.
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInHashtable() {
        long timeStart = System.nanoTime();

        // Створюємо список Entry та сортуємо за значеннями
        List<Map.Entry<Sheep, String>> entries = new ArrayList<>(hashtable.entrySet());
        OwnerValueComparator comparator = new OwnerValueComparator();
        Collections.sort(entries, comparator);

        // Створюємо тимчасовий Entry для пошуку
        Map.Entry<Sheep, String> searchEntry = new Map.Entry<Sheep, String>() {
            public Sheep getKey() { return null; }
            public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            public String setValue(String value) { return null; }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в Hashtable");

        if (position >= 0) {
            Map.Entry<Sheep, String> foundEntry = entries.get(position);
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Sheep: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в Hashtable.");
        }
    }

    /**
     * Додає новий запис до Hashtable.
     */
    void addEntryToHashtable() {
        long timeStart = System.nanoTime();

        hashtable.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до Hashtable");

        System.out.println("Додано новий запис: Sheep='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з Hashtable за ключем.
     */
    void removeByKeyFromHashtable() {
        long timeStart = System.nanoTime();

        String removedValue = hashtable.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з Hashtable");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з Hashtable за значенням.
     */
    void removeByValueFromHashtable() {
        long timeStart = System.nanoTime();

        List<Sheep> keysToRemove = new ArrayList<>();
        for (Map.Entry<Sheep, String> entry : hashtable.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE)) {
                keysToRemove.add(entry.getKey());
            }
        }
        
        for (Sheep key : keysToRemove) {
            hashtable.remove(key);
        }

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з Hashtable");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    // ===== Методи для HashMap =====

    /**
     * Виводить вміст HashMap.
     * HashMap автоматично відсортована за ключами (Sheep nickname за зростанням, woolLength за спаданням).
     */
    private void printHashMap() {
        System.out.println("\n=== Пари ключ-значення в HashMap ===");

        long timeStart = System.nanoTime();
        for (Map.Entry<Sheep, String> entry : hashMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пар ключ-значення в HashMap");
    }

    /**
     * Здійснює пошук елемента за ключем в HashMap.
     * Використовує Sheep.compareTo() для навігації по дереву.
     */
    void findByKeyInHashMap() {
        long timeStart = System.nanoTime();

        boolean found = hashMap.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в HashMap");

        if (found) {
            String value = hashMap.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в HashMap.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в HashMap.
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInHashMap() {
        long timeStart = System.nanoTime();

        // Створюємо список Entry та сортуємо за значеннями
        List<Map.Entry<Sheep, String>> entries = new ArrayList<>(hashMap.entrySet());
        OwnerValueComparator comparator = new OwnerValueComparator();
        Collections.sort(entries, comparator);

        // Створюємо тимчасовий Entry для пошуку
        Map.Entry<Sheep, String> searchEntry = new Map.Entry<Sheep, String>() {
            public Sheep getKey() { return null; }
            public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            public String setValue(String value) { return null; }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в HashMap");

        if (position >= 0) {
            Map.Entry<Sheep, String> foundEntry = entries.get(position);
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Sheep: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в HashMap.");
        }
    }

    /**
     * Додає новий запис до HashMap.
     */
    void addEntryToHashMap() {
        long timeStart = System.nanoTime();

        hashMap.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до HashMap");

        System.out.println("Додано новий запис: Sheep='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з HashMap за ключем.
     */
    void removeByKeyFromHashMap() {
        long timeStart = System.nanoTime();

        String removedValue = hashMap.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з HashMap");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з HashMap за значенням.
     */
    void removeByValueFromHashMap() {
        long timeStart = System.nanoTime();

        List<Sheep> keysToRemove = new ArrayList<>();
        for (Map.Entry<Sheep, String> entry : hashMap.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE)) {
                keysToRemove.add(entry.getKey());
            }
        }
        
        for (Sheep key : keysToRemove) {
            hashMap.remove(key);
        }

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з HashMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
     * Головний метод для запуску програми.
     */
public static void main(String[] args) {
    Hashtable<Sheep, String> hashtable = new Hashtable<>();
    hashtable.put(new Sheep("Вовна", 25.5), "Дарина");
    hashtable.put(new Sheep("Кудря", 22.3), "Петро");
    hashtable.put(new Sheep("Бяша", 28.7), "Андрій");
    hashtable.put(new Sheep("Овечка", 20.1), "Галина");
    hashtable.put(new Sheep("Кудря", 26.4), "Михайло");
    hashtable.put(new Sheep("Пухна", 18.9), "Андрій");
    hashtable.put(new Sheep("Рунко", 23.6), "Олена");
    hashtable.put(new Sheep("Барашек", 19.8), "Галина");
    hashtable.put(new Sheep("Пухна", 27.2), "Іван");
    hashtable.put(new Sheep("Сніжинка", 15.4), "Марія");

    HashMap<Sheep, String> hashMap = new HashMap<Sheep, String>() {{
        put(new Sheep("Вовна", 25.5), "Дарина");
        put(new Sheep("Кудря", 22.3), "Петро");
        put(new Sheep("Бяша", 28.7), "Андрій");
        put(new Sheep("Овечка", 20.1), "Галина");
        put(new Sheep("Кудря", 26.4), "Михайло");
        put(new Sheep("Пухна", 18.9), "Андрій");
        put(new Sheep("Рунко", 23.6), "Олена");
        put(new Sheep("Барашек", 19.8), "Галина");
        put(new Sheep("Пухна", 27.2), "Іван");
        put(new Sheep("Сніжинка", 15.4), "Марія");
    }};


        // Створюємо об'єкт і виконуємо операції
        BasicDataOperationUsingMap operations = new BasicDataOperationUsingMap(hashtable, hashMap);
        operations.executeDataOperations();
    }
}
