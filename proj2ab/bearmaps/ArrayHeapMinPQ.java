package bearmaps;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<Entry> items;
    private HashMap<T, Integer> ind;
    private int size;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        items.add(0, null);
        ind = new HashMap<>();
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(T item) {
        if (contains(item, 1)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean contains(T item, int index) {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        if (index >= items.size()) {
            return false;
        } else if (item == items.get(index).item) {
            return true;
        } else {
            return contains(item, index * 2) || contains(item, index * 2 + 1);
        }
    }

    private int parent(int k) {
        return k / 2;
    }

    private int leftChild(int k) {
        return k * 2;
    }

    private int rightChild(int k) {
        return k * 2 + 1;
    }

    private boolean hasLeft(int k) {
        return leftChild(k) < items.size();
    }

    private boolean hasRight(int k) {
        return rightChild(k) < items.size();
    }

    private double getPriority(int k) {
        return items.get(k).val;
    }

    private T getItem(int k) {
        return items.get(k).item;
    }

    private Entry getEntry(int k) {
        return items.get(k);
    }

    private void swap(int one, int two) {

        Entry first = getEntry(one);
        Entry sec = getEntry(two);
        items.set(one, sec);
        items.set(two, first);
        ind.replace(first.item, two);
        ind.replace(sec.item, two);
    }


    private void makeCorrect(int k) {
        correctHead(k);
        if (parent(k) == 0) {
            return;
        } else {
            if (getPriority(k) < getPriority(parent(k))) {
                swap(k, parent(k));
            } else {
                return;
            }
        }
    }

    private int findMinChild(int index) {
        double min_p = Math.min(getPriority(leftChild(index)), getPriority(rightChild(index)));
        int min;
        if (min_p == getPriority(leftChild(index))) {
            min = leftChild(index);
        } else {
            min = rightChild(index);
        }
        return min;
    }

    private void correctHead(int index) {
        if (hasLeft(index) && hasRight(index)) {

            int min = findMinChild(index);

            if (getPriority(index) < getPriority(leftChild(index)) && getPriority(index) < getPriority(rightChild(index))) {
                return;
            } else {
                swap(index, min);
                correctHead(min);
            }
        } else {
            if (hasLeft(index)) {
                if (getPriority(index) < getPriority(leftChild(index))) {
                    return;
                } else {
                    swap(index, leftChild(index));
                    correctHead(leftChild(index));
                }
            } else {
                return;
            }
        }
    }


    @Override
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return items.get(1).item;
    }

    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        if (size == 1) {
            T temp = items.get(1).item;
            items.remove(1);
            ind.remove(temp);
            size--;
            return temp;
        }
        Entry newRoot = getEntry(size);
        T item = newRoot.item;
        T remove = items.get(1).item;

        items.set(1, newRoot);
        items.remove(size);
        ind.remove(remove);
        ind.replace(item, 1);
        size--;
        correctHead(1);
        return remove;
    }

    @Override
    public void add(T item, double priority) {
        if (size > 0) {
            if (contains(item)) {
                throw new IllegalArgumentException();
            }
        }
        size++;
        items.add(new Entry(item, priority));
        ind.put(item, size);
        makeCorrect(size);
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = ind.get(item);
        items.set(index, new Entry(item, priority));
        makeCorrect(ind.get(item));
    }

    private class Entry {

        private T item;
        private double val;

        Entry(T first, double priority) {
            item = first;
            val = priority;
        }
    }
}