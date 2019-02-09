public class ArrayDeque<T> {

    private T[] items;
    private int size;
    private int front;
    private int end;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        front = 0;
        end = 1;
        size = 0;
    }

    public ArrayDeque(ArrayDeque other) {
        front = 0;
        end = 1;
        size = 0;
        for (int i = 0; i < size; i++) {
            this.addLast((T) other.get((other.front + i) % other.items.length));
        }
    }

    private void resize() {
        if ((double) size / items.length < .25 && items.length >= 16) {
            T[] b = (T[]) new Object[(int) (items.length / 2)];
            for (int i = 0; i < size; i++) {
                b[i] = items[(front + i) % items.length];
            }
            items = b;
            front = 0;
            end = size;
        } else if (size >= items.length) {
            T[] a = (T[]) new Object[size * 2];
            for (int i = 0; i < size; i++) {
                a[i] = items[(front + i) % items.length];

            }
            items = a;
            front = 0;
            end = size;
        }
    }

    private void frontforward() {
        front += 1;
        if (front > items.length - 1) {
            front = 0;
        }
    }

    private void frontback() {
        front -= 1;
        if (front < 0) {
            front = items.length - 1;
        }
    }

    private void endforward() {
        end += 1;
        if (end > items.length - 1) {
            end = 0;
        }
    }

    private void endback() {
        end -= 1;
        if (end < 0) {
            end = items.length - 1;
        }
    }

    public void addFirst(T item) {
        if (items.length > 16 || size == items.length) {
            resize();
        }
        if (front <= 0) {
            front = items.length - 1;
        }
        items[front] = item;
        frontback();
        size++;
    }

    public void addLast(T item) {
        if (items.length >= 16 || size == items.length) {
            resize();
        }
        if (end >= items.length - 1) {
            end = 0;
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
        if (count == end) {
            for (int i = 0; i < size; i++) {
                if (count == items.length) {
                    count = 0;
                }
                System.out.print(items[count] + " ");
                count++;
            }
            System.out.println();
        } else {
            for (int i = 0; i < size; i++) {
                if (count == items.length) {
                    count = 0;
                }
                System.out.print(items[count] + " ");
                count++;
            }
            System.out.println();
        }
    }

    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        if (front + 1 == items.length) {
            front = 0;
            T a = items[front];
            items[front] = null;
            size--;
            resize();
            return a;
        } else {
            T a = items[front + 1];
            items[front + 1] = null;
            frontforward();
            size--;
            resize();
            return a;
        }
    }

    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        resize();
        if (end == 0) {
            end = items.length - 1;
            T a = items[end];
            items[end] = null;
            size--;
            return a;
        }
        T a = items[end - 1];
        items[end - 1] = null;
        endback();
        size--;
        return a;
    }

    public T get(int index) {
        if (this.isEmpty() || index < 0 || index >= size) {
            return null;
        } else {
            int pointer = front;
            for (int i = 0; i <= index; i++) {
                pointer = (pointer + 1) % items.length;
            }
            return items[pointer];
        }
    }
}
