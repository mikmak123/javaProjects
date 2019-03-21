import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61B {

    private static final int R = 128;
    private Node root;
    private int n;


    private static class Node {
        private Object val;
        private HashMap<Integer, Node> map = new HashMap<>();
        private boolean isKey = false;
    }

    @Override
    public boolean contains(String key) {
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                return false;
            } else {
                curr = curr.map.get(c);
            }
        }
        return curr.isKey;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        return null;
    }

    @Override
    public String longestPrefixOf(String key) {
        Node curr = root;
        String ret = "";
        for (int i = 0; i < key.length(); i++) {
            if (contains(key.substring(1, i))) {
                ret = key.substring(1, i);
            } else {
                break;
            }
        }
        return ret;
    }

    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }


    @Override
    public void clear() {
        root.val = -1;
        root.map = new HashMap<>();
    }
}
