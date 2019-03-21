import java.util.*;

public class MyTrieSet implements TrieSet61B {

    private static final int R = 128;
    private Node root;
    private int n;


    private static class Node {

        public Node(char c, boolean key) {
            val = c;
            isKey = key;
        }
        private Object val;
        private HashMap<Character, Node> map = new HashMap<>();
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
        return keysWithPrefixHelp("", prefix, new ArrayList<String>(), root);
    }

    public List<String> keysWithPrefixHelp(String ret, String prefix, List<String> x, Node n) {
        if (n.isKey) {
            x.add(ret);
        }
        for (char c : n.map.keySet()) {
            keysWithPrefixHelp(ret + c, prefix.substring(1), x, n.map.get(c));
        }
        return x;
    }



    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
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
