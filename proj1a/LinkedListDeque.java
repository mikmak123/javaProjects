public class LinkedListDeque <type> {


    private class Node {

        public type first;
        public Node prev;
        public Node next;

        public Node(type x, Node n) {
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
        size =0;
        while(this.size < other.size) {
            this.addLast((type) other.get(this.size));
        }
    }

    public void addFirst(type item) {
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

    public void addLast(type item) {
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
        if(this.isEmpty()) {
            System.out.println("Null");
        }
        else {
            while (a != sentinel) {
                System.out.print(a.next.first + " ");
                a = a.next;
            }
        }
        System.out.println();
    }

    public type removeFirst() {

        if (this.isEmpty()) {
            return null;
        }
        type one = sentinel.next.first;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return one;
    }
    public type removeLast() {
        if(this.isEmpty()) {
            return null;
        }
        type last = sentinel.prev.first;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -=1;
        return last;
    }
    public type get(int index) {

        if (index >= size || index < 0 || isEmpty()) {
            return null;
        }
        Node a = sentinel;

        for (int i = 0; i <= index; i++) {
            a = a.next;
        }
        return a.first;
    }
    public type getRecursive(int index) {

        return getRec(new Node(sentinel.next.first, sentinel.next.next), index);

    }

    public type getRec(Node a, int ind) {
        if (ind == 0) {
            return a.first;
        } else {
            return getRec(a.next, ind - 1);
        }
    }
}