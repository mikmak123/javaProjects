import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V>  {

    int initSize;
    double loadF;
    HashSet<K> buckets;
    ArrayList<V> values;


    public MyHashMap() {
        initSize = 16;
        loadF = 0.75;
        buckets = new HashSet<>(initSize, (float) loadF);
        values = new ArrayList<>();
    }

    public MyHashMap(int initialSize) {
        initSize = initialSize;
        loadF = 0.75;
        buckets = new HashSet<>(initSize, (float) loadF);
        values = new ArrayList<>();

    }

    public MyHashMap(int initialSize, double loadFactor) {
        initSize = initialSize;
        loadF = loadFactor;
        buckets = new HashSet<>(initSize, (float) loadF);
        values = new ArrayList<>();
    }

    @Override
    public void clear() {
        buckets.clear();
        values.clear();
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        return values.get(key.hashCode());
    }

    @Override
    public int size() {
        return buckets.size();
    }

    @Override
    public void put(K key, V value) {
        buckets.add(key);
        values.add(key.hashCode(), value);
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
