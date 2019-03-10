import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        private K key;
        private V value;
        private Node left, right;
        private int size;

        private Node(K k, V v, int s) {
            key = k;
            value = v;
            size = s;
            left = null;
            right = null;
        }
    }

    private Node root;

    private V get(Node r, K k) {
        int comp = k.compareTo(r.key);
        if (r == null) {
            return null;
        }
        if (comp == 0) {
            return r.value;
        }
        if (comp < 0) {
            return get(r.left, k);
        } else {
            return get(r.right, k);
        }
    }

    private Node put(Node r, K k, V v) {
        if (r == null) {
            return new Node(k, v, 1);
        }
        int comp = k.compareTo(r.key);
        if (comp < 0) {
            r.left = put(r.left, k, v);
        } else if (comp > 0) {
            r.right = put(r.right, k, v);
        } else {
            r.value = v;
        }
        r.size = 1 + size(r.left) + size(r.right);
        return r;
    }

    private int size(Node r) {
        if (r == null) {
            return 0;
        }
        return r.size;
    }

    public BSTMap() {
        root = null;
    }


    public void clear() {
        root = null;
    }


    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        return get(key) != null;
    }


    public V get(K key) {
        if (key == null) {
            return null;
        }
        return get(root, key);
    }


    public int size() {
        return size(root);
    }

    public void put(K key, V value) {
        root = put(root, key, value);
    }


    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    public V remove(K key) {
        throw new UnsupportedOperationException();
    }


    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }


    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }


    public void printIntOrder() {
        print(root);
    }
    public void print(Node r) {
        if (r == null) {
            return;
        }
        if (r.left == null) {
            System.out.println(get(r.key));
            print(r.right);
        } else {
            print(r.left);
            System.out.println(r.value);
            print(r.right);
        }
    }

}

