package org.example;

import java.util.Arrays;
import java.util.Comparator;

public class MyArrayList<E> {
    private Object[] elements;
    private int size = 0;

    public MyArrayList() {
        this.elements = new Object[10];
    }

    public MyArrayList(int size) {
        if (size < this.size) {
            throw new IllegalArgumentException("Illegal collection size: " + size);
        }
        this.elements = new Object[size];
        this.size = size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public E get(int index) {
        validateRange(index);
        return (E) this.elements[index];
    }

    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is " + index);
        }
        E oldValue = (E) this.elements[index];
        quickRemove(index);
        return oldValue;
    }

    public boolean remove(Object element) {
        for (int i = 0; i < this.size; i++) {
            if (element.equals(this.elements[i])) {
                quickRemove(i);
                return true;
            }
        }
        return false;
    }

    public void add(E element) {
        if (size == elements.length)
            elements = expand();
        elements[size++] = element;
    }

    public void add(int index, E element) {
        validateRange(index);
        if (size == elements.length)
            elements = expand();
        System.arraycopy(this.elements, index, this.elements, index + 1, size - index);
        elements[index] = element;
        this.size++;
    }

    public void addAll(MyArrayList<? extends E> otherArrayList) {
        for (int i = 0; i < otherArrayList.getSize(); i++) {
            this.add(otherArrayList.get(i));
        }
    }

    private int getSize() {
        return this.size;
    }

    public static <E> void sort(E[] a, Comparator<? super E> comp) {
        mergeSort(a, 0, a.length - 1, comp);
    }


    private static <E> void mergeSort(E[] a, int from, int to, Comparator<? super E> comp) {
        if (from == to)
            return;
        int mid = (from + to) / 2;
        mergeSort(a, from, mid, comp);
        mergeSort(a, mid + 1, to, comp);
        merge(a, from, mid, to, comp);
    }

    private static <E> void merge(E[] a, int from, int mid, int to, Comparator<? super E> comp) {
        int n = to - from + 1;
        Object[] values = new Object[n];

        int fromValue = from;

        int middleValue = mid + 1;

        int index = 0;

        while (fromValue <= mid && middleValue <= to) {
            if (comp.compare(a[fromValue], a[middleValue]) < 0) {
                values[index] = a[fromValue];
                fromValue++;
            } else {
                values[index] = a[middleValue];
                middleValue++;
            }
            index++;
        }

        while (fromValue <= mid) {
            values[index] = a[fromValue];
            fromValue++;
            index++;
        }
        while (middleValue <= to) {
            values[index] = a[middleValue];
            middleValue++;
            index++;
        }

        for (index = 0; index < n; index++)
            a[from + index] = (E) values[index];
    }

    private Object[] expand() {
        int newCapacity = this.elements.length * 2;
        return elements = Arrays.copyOf(this.elements, newCapacity);
    }

    private void quickRemove(int index) {
        int indexToMove = this.size - index - 1;
        if (indexToMove > 0) {
            System.arraycopy(this.elements, index + 1, this.elements, index, indexToMove);
        }
        this.size--;
        this.elements[this.size] = null;
    }

    private void validateRange(int index) {
        if (index >= (this.size + 1) || index < 0) {
            throw new IllegalArgumentException("Illegal index: " + index);
        }
    }
}
