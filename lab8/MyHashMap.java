import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V>  {

    int initSize;
    double loadF;
    HashSet<K> buckets;
    ArrayList<Entry> store;


    public MyHashMap() {
        initSize = 16;
        loadF = 0.75;
        buckets = new HashSet<>(initSize, (float) loadF);
        store = new ArrayList<>();
    }

    public MyHashMap(int initialSize) {
        initSize = initialSize;
        loadF = 0.75;
        buckets = new HashSet<>(initSize, (float) loadF);
        store = new ArrayList<>();
    }

    public MyHashMap(int initialSize, double loadFactor) {
        initSize = initialSize;
        loadF = loadFactor;
        buckets = new HashSet<>(initSize, (float) loadF);
        store = new ArrayList<>();
    }
    private class Entry {
        private K key;
        private V val;
        Entry(K k, V v) {
            key = k;
            val = v;
        }
    }

    @Override
    public void clear() {
        buckets.clear();
        store.clear();
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        if (buckets.contains(key)) {
            for (int i = 0; i <store.size(); i++) {
                if (store.get(i).key == key) {
                    return store.get(i).val;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return buckets.size();
    }

    @Override
    public void put(K key, V value) {
        if (buckets.contains(key)) {
            for (int i = 0; i <store.size(); i++) {
                if (store.get(i).key == key) {
                    store.get(i).val = value;
                }
            }
        } else {
            buckets.add(key);
            Entry st = new Entry(key, value);
            store.add(st);
        }
    }

    @Override
    public Set<K> keySet() {
        return buckets;
    }

    @Override
    public Iterator<K> iterator() {
        return buckets.iterator();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
}
