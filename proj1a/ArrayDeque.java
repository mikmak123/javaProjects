public class ArrayDeque <type> {

    private type[] items;
    private int size;
    private int front;
    private int end;

    public ArrayDeque() {
        items = (type[]) new Object[8];
        front = 0;
        end = 1;
        size = 0;
    }

    public ArrayDeque(ArrayDeque other) {
        front = 0;
        end = 1;
        size =0;
        while(this.size < other.size) {
            this.addLast((type) other.get(this.size));
        }
    }

    public void resize() {
        if((double)size/items.length < .25) {
            type[] b = (type[]) new Object[(int) (items.length/ 2)];
            for (int i = 0; i < size; i++) {
                b[i] = items[(front + i) % items.length];
            }
            items = b;
            front = 0;
            end = size;
        }
        else if (size >= items.length) {
            type[] a = (type[]) new Object[size * 2];
            for (int i = 0; i < size; i++) {
                a[i] = items[(front + i) % items.length];

            }
            items = a;
            front = 0;
            end = size;
        }
    }
    public void frontforward() {
        front += 1;
        if (front > items.length - 1) {
            front = 0;
        }
    }

    public void frontback() {
        front -= 1;
        if (front < 0) {
            front = items.length - 1;
        }
    }

    public void endforward() {
        end += 1;
        if (end > items.length - 1) {
            end = 0;
        }
    }

    public void endback() {
        end -= 1;
        if (end < 0) {
            end = items.length - 1;
        }
    }

    public void addFirst(type item) {
        if(items.length > 16) {
            resize();
        }
        items[front] = item;
        frontback();
        size++;
    }

    public void addLast(type item) {
        if(items.length > 16) {
            resize();
        }
        items[end] = item;
        endforward();
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int count = front + 1;
        while (count != end) {
            if (count == items.length) {
                count = 0;
            }
            System.out.print(items[count] + " ");
            count++;
        }
        System.out.println();
    }

    public type removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        type a = items[front];
        items[front] = null;
        frontforward();
        return a;
    }

    public type removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        type a = items[end];
        items[end] = null;
        endback();
        return a;
    }

    public type get(int index) {
        if (this.isEmpty() || index < 0 || index >= size) {
            return null;
        } else {
            int pointer = front;
            for(int i = 0; i<= index; i++) {
                pointer = (pointer + 1) % items.length;
            }
            return items[pointer];
        }
    }
    public static void main(String[] args) {

        ArrayDeque <Integer> test = new ArrayDeque<Integer>();
        test.addLast(2);
        test.addFirst(4);
        test.removeFirst();
        test.removeLast();
        test.addFirst(7);
        test.addLast(8);
        test.addLast(9);
        test.addFirst(10);
        test.printDeque();
        System.out.println(test.get(1));
    }
}
