public class LinkedListDeque<T> {


    private class Node {

        private T first;
        private Node prev;
        private Node next;

        Node(T x, Node n) {
            first = x;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new Node(null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
        while (this.size < other.size) {
            this.addLast((T) other.get(this.size));
        }
    }

    public void addFirst(T item) {
        Node a = new Node(item, sentinel.next);
        if (size == 0) {
            sentinel.prev = a;
            a.next = sentinel;
        } else {
            sentinel.next.prev = a;
            a.next = sentinel.next;
        }
        sentinel.next = a;
        a.prev = sentinel;
        size += 1;
    }

    public void addLast(T item) {
        Node a = new Node(item, sentinel);
        if (size == 0) {
            sentinel.next = a;
            a.prev = sentinel;
        } else {
            a.prev = sentinel.prev;
            sentinel.prev.next = a;
        }
        sentinel.prev = a;
        a.next = sentinel;
        size += 1;
    }

    public boolean isEmpty() {

        return size == 0;
    }

    public int size() {
        return size;
    }



    public void printDeque() {
        Node a = sentinel;
        if (this.isEmpty()) {
            System.out.println("Null");
        } else {
            while (a != sentinel) {
                System.out.print(a.next.first + " ");
                a = a.next;
            }
        }
        System.out.println();
    }

    public T removeFirst() {

        if (this.isEmpty()) {
            return null;
        }
        T one = sentinel.next.first;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return one;
    }
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        T last = sentinel.prev.first;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return last;
    }
    public T get(int index) {

        if (index >= size || index < 0 || isEmpty()) {
            return null;
        }
        Node a = sentinel;

        for (int i = 0; i <= index; i++) {
            a = a.next;
        }
        return a.first;
    }
    public T getRecursive(int index) {

        return getRec(new Node(sentinel.next.first, sentinel.next.next), index);

    }

    public T getRec(Node a, int ind) {
        if (ind == 0) {
            return a.first;
        } else {
            return getRec(a.next, ind - 1);
        }
    }
}
