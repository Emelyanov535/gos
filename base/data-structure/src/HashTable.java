import java.util.*;
import java.util.function.Function;

interface CollisionResolution<K, V> {
    void put(HashTable<K, V> table, K key, V value);
    V get(HashTable<K, V> table, K key);
    void remove(HashTable<K, V> table, K key);
}

public class HashTable<K, V> {
    public int capacity = 16;
    private int size = 0;
    private final float loadFactor = 0.75f;

    private final Function<K, Integer> hashFunction;
    private final Function<K, Integer> secondHashFunction; // для двойного хеширования

    private final CollisionResolution<K, V> collisionResolution;

    List<Entry<K, V>>[] chainTable;
    Entry<K, V>[] probeTable;

    @SuppressWarnings("unchecked")
    public HashTable(Function<K, Integer> hashFunction, CollisionResolution<K, V> collisionResolution) {
        this(hashFunction, null, collisionResolution);
    }

    @SuppressWarnings("unchecked")
    public HashTable(Function<K, Integer> hashFunction,
                     Function<K, Integer> secondHashFunction,
                     CollisionResolution<K, V> collisionResolution) {
        this.hashFunction = hashFunction;
        this.secondHashFunction = secondHashFunction;
        this.collisionResolution = collisionResolution;

        if (collisionResolution instanceof ChainingResolution) {
            chainTable = new List[capacity];
            for (int i = 0; i < capacity; i++) {
                chainTable[i] = new LinkedList<>();
            }
        } else {
            probeTable = new Entry[capacity];
        }
    }

    static class Entry<K, V> {
        K key;
        V value;
        boolean deleted = false;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    int hash(K key) {
        int h = hashFunction.apply(key);
        return (h & 0x7fffffff) % capacity;
    }

    // Вторая хеш-функция для двойного хеширования (если null — fallback)
    int secondHash(K key) {
        if (secondHashFunction == null) {
            // Простейшая вторая хеш-функция, должна быть !=0 и меньше capacity
            int h = (key == null ? 0 : key.hashCode());
            int val = 1 + ((h & 0x7fffffff) % (capacity - 1));
            return val;
        }
        int h = secondHashFunction.apply(key);
        int val = h == 0 ? 1 : Math.abs(h) % capacity;
        return val;
    }

    public void put(K key, V value) {
        if (size >= capacity * loadFactor) {
            resize();
        }
        collisionResolution.put(this, key, value);
    }

    public V get(K key) {
        return collisionResolution.get(this, key);
    }

    public void remove(K key) {
        collisionResolution.remove(this, key);
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        capacity *= 2;
        size = 0;

        if (collisionResolution instanceof ChainingResolution) {
            List<Entry<K, V>>[] oldTable = chainTable;
            chainTable = new List[capacity];
            for (int i = 0; i < capacity; i++) {
                chainTable[i] = new LinkedList<>();
            }
            for (List<Entry<K, V>> bucket : oldTable) {
                for (Entry<K, V> e : bucket) {
                    put(e.key, e.value);
                }
            }
        } else {
            Entry<K, V>[] oldTable = probeTable;
            probeTable = new Entry[capacity];
            for (Entry<K, V> e : oldTable) {
                if (e != null && !e.deleted) {
                    put(e.key, e.value);
                }
            }
        }
    }

    void incrementSize() {
        size++;
    }

    void decrementSize() {
        size--;
    }

    public int size() {
        return size;
    }

    // --- Пример хеш-функций ---

    public static <K> Function<K, Integer> standardHash() {
        return key -> key == null ? 0 : key.hashCode();
    }

    public static Function<String, Integer> simpleStringHash() {
        return s -> {
            if (s == null) return 0;
            int sum = 0;
            for (char c : s.toCharArray()) sum += c;
            return sum;
        };
    }

    public static Function<Integer, Integer> modHash() {
        return k -> k == null ? 0 : k % 97;
    }
}

// --- Реализации ---

class ChainingResolution<K, V> implements CollisionResolution<K, V> {

    @Override
    public void put(HashTable<K, V> table, K key, V value) {
        int idx = table.hash(key);
        List<HashTable.Entry<K, V>> bucket = table.chainTable[idx];

        for (HashTable.Entry<K, V> entry : bucket) {
            if (Objects.equals(entry.key, key)) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new HashTable.Entry<>(key, value));
        table.incrementSize();
    }

    @Override
    public V get(HashTable<K, V> table, K key) {
        int idx = table.hash(key);
        List<HashTable.Entry<K, V>> bucket = table.chainTable[idx];

        for (HashTable.Entry<K, V> entry : bucket) {
            if (Objects.equals(entry.key, key)) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public void remove(HashTable<K, V> table, K key) {
        int idx = table.hash(key);
        List<HashTable.Entry<K, V>> bucket = table.chainTable[idx];
        Iterator<HashTable.Entry<K, V>> it = bucket.iterator();
        while (it.hasNext()) {
            HashTable.Entry<K, V> entry = it.next();
            if (Objects.equals(entry.key, key)) {
                it.remove();
                table.decrementSize();
                return;
            }
        }
    }
}

class LinearProbingResolution<K, V> implements CollisionResolution<K, V> {

    @Override
    public void put(HashTable<K, V> table, K key, V value) {
        int idx = table.hash(key);
        int startIdx = idx;

        while (true) {
            HashTable.Entry<K, V> entry = table.probeTable[idx];

            if (entry == null || entry.deleted) {
                table.probeTable[idx] = new HashTable.Entry<>(key, value);
                table.incrementSize();
                return;
            }

            if (Objects.equals(entry.key, key)) {
                entry.value = value;
                return;
            }

            idx = (idx + 1) % table.capacity;
            if (idx == startIdx) {
                throw new RuntimeException("HashTable is full");
            }
        }
    }

    @Override
    public V get(HashTable<K, V> table, K key) {
        int idx = table.hash(key);
        int startIdx = idx;

        while (true) {
            HashTable.Entry<K, V> entry = table.probeTable[idx];
            if (entry == null) return null;
            if (!entry.deleted && Objects.equals(entry.key, key)) {
                return entry.value;
            }
            idx = (idx + 1) % table.capacity;
            if (idx == startIdx) break;
        }
        return null;
    }

    @Override
    public void remove(HashTable<K, V> table, K key) {
        int idx = table.hash(key);
        int startIdx = idx;

        while (true) {
            HashTable.Entry<K, V> entry = table.probeTable[idx];
            if (entry == null) return;
            if (!entry.deleted && Objects.equals(entry.key, key)) {
                entry.deleted = true;
                table.decrementSize();
                return;
            }
            idx = (idx + 1) % table.capacity;
            if (idx == startIdx) break;
        }
    }
}

class QuadraticProbingResolution<K, V> implements CollisionResolution<K, V> {

    @Override
    public void put(HashTable<K, V> table, K key, V value) {
        int baseIdx = table.hash(key);
        int i = 0;

        while (i < table.capacity) {
            int idx = (baseIdx + i * i) % table.capacity;
            HashTable.Entry<K, V> entry = table.probeTable[idx];

            if (entry == null || entry.deleted) {
                table.probeTable[idx] = new HashTable.Entry<>(key, value);
                table.incrementSize();
                return;
            }

            if (Objects.equals(entry.key, key)) {
                entry.value = value;
                return;
            }
            i++;
        }
        throw new RuntimeException("HashTable is full");
    }

    @Override
    public V get(HashTable<K, V> table, K key) {
        int baseIdx = table.hash(key);
        int i = 0;

        while (i < table.capacity) {
            int idx = (baseIdx + i * i) % table.capacity;
            HashTable.Entry<K, V> entry = table.probeTable[idx];

            if (entry == null) return null;
            if (!entry.deleted && Objects.equals(entry.key, key)) {
                return entry.value;
            }
            i++;
        }
        return null;
    }

    @Override
    public void remove(HashTable<K, V> table, K key) {
        int baseIdx = table.hash(key);
        int i = 0;

        while (i < table.capacity) {
            int idx = (baseIdx + i * i) % table.capacity;
            HashTable.Entry<K, V> entry = table.probeTable[idx];

            if (entry == null) return;
            if (!entry.deleted && Objects.equals(entry.key, key)) {
                entry.deleted = true;
                table.decrementSize();
                return;
            }
            i++;
        }
    }
}

// --- Новый класс двойного хеширования ---

class DoubleHashingResolution<K, V> implements CollisionResolution<K, V> {

    @Override
    public void put(HashTable<K, V> table, K key, V value) {
        int h1 = table.hash(key);
        int h2 = table.secondHash(key);
        int idx = h1;
        int i = 0;

        while (i < table.capacity) {
            HashTable.Entry<K, V> entry = table.probeTable[idx];
            if (entry == null || entry.deleted) {
                table.probeTable[idx] = new HashTable.Entry<>(key, value);
                table.incrementSize();
                return;
            }
            if (Objects.equals(entry.key, key)) {
                entry.value = value;
                return;
            }
            i++;
            idx = (h1 + i * h2) % table.capacity;
        }
        throw new RuntimeException("HashTable is full");
    }

    @Override
    public V get(HashTable<K, V> table, K key) {
        int h1 = table.hash(key);
        int h2 = table.secondHash(key);
        int idx = h1;
        int i = 0;

        while (i < table.capacity) {
            HashTable.Entry<K, V> entry = table.probeTable[idx];
            if (entry == null) return null;
            if (!entry.deleted && Objects.equals(entry.key, key)) {
                return entry.value;
            }
            i++;
            idx = (h1 + i * h2) % table.capacity;
        }
        return null;
    }

    @Override
    public void remove(HashTable<K, V> table, K key) {
        int h1 = table.hash(key);
        int h2 = table.secondHash(key);
        int idx = h1;
        int i = 0;

        while (i < table.capacity) {
            HashTable.Entry<K, V> entry = table.probeTable[idx];
            if (entry == null) return;
            if (!entry.deleted && Objects.equals(entry.key, key)) {
                entry.deleted = true;
                table.decrementSize();
                return;
            }
            i++;
            idx = (h1 + i * h2) % table.capacity;
        }
    }

    public static void main(String[] args) {
        HashTable<String, Integer> htChain = new HashTable<>(
            HashTable.standardHash(), new ChainingResolution<>());
        htChain.put("apple", 5);
        htChain.put("banana", 10);
        System.out.println("Chain get apple: " + htChain.get("apple"));
        htChain.remove("apple");
        System.out.println("Chain get apple after removal: " + htChain.get("apple"));

        // 2) Линейное пробирование + простая строковая сумма
        HashTable<String, Integer> htLinear = new HashTable<>(
            HashTable.simpleStringHash(), new LinearProbingResolution<>());
        htLinear.put("abc", 123);
        htLinear.put("cab", 321); // коллизия почти гарантирована
        System.out.println("Linear get abc: " + htLinear.get("abc"));
        System.out.println("Linear get cab: " + htLinear.get("cab"));

        // 3) Квадратичное пробирование + modHash для Integer
        HashTable<Integer, String> htQuad = new HashTable<>(
            HashTable.modHash(), new QuadraticProbingResolution<>());
        htQuad.put(10, "ten");
        htQuad.put(107, "one hundred seven"); // возможна коллизия
        System.out.println("Quadratic get 10: " + htQuad.get(10));
        System.out.println("Quadratic get 107: " + htQuad.get(107));

        HashTable<String, Integer> table = new HashTable<>(
            HashTable.standardHash(),
            key -> {
                // Вторая хеш-функция - должна возвращать не 0
                int h = key == null ? 1 : key.hashCode();
                int val = 1 + Math.abs(h) % 15;  // capacity - 1 = 15 (если capacity =16)
                return val;
            },
            new DoubleHashingResolution<>()
        );

        table.put("one", 1);
        table.put("two", 2);
        System.out.println(table.get("one")); // 1
    }

}
