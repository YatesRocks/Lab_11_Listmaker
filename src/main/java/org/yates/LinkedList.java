package org.yates;

import java.util.*;

public class LinkedList<T> implements Iterable<T> {
    /* The element of header will never be accessed
     * The "real" head will be header.next, the tail is header.prev */
    private Node<T> header = new Node<T>(null, null, null);
    private int size = 0;

    public LinkedList() {
        header.prev = header.next = header;
    }

    public LinkedList(Collection<? extends T> c) {
        this();
        addAll(c);
    }

    public LinkedList(T[] c) {
        this();
        addAll(size, c);
    }

    // -- + -- +-> Private Methods <-+ -- + --

    /* Inserts a new node with element "e" before
     * the given Node "n" (adds e before n) */
    private Node<T> addBefore(T e, Node<T> n) {
        Node<T> nN = new Node<T>(n.prev, e, n);
        n.prev.next = nN;
        n.prev = nN;
        size++;
        return nN;
    }

    /* Given an index within the bounds of the arr,
     * returns a pointer to the Node at that index */
    private Node<T> elementAt(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException(
                    "Size: " + size + " Index: " + index
            );

        Node<T> cursor = header;
        if (index > size >> 1) {
            for (int i=size; i>index; i--)
                cursor = cursor.prev;
        } else {
            for (int i=0; i<=index; i++)
                cursor = cursor.next;
        }

        return cursor;
    }

    // -- + -- +-> Add Methods <-+ -- + --

    public void addFirst(T e) {
        addBefore(e, header.next);
    }

    public void add(int index, T e) {
        addBefore(e, elementAt(index));
    }

    public void add(T e) {
        addBefore(e, header);
    }

    public boolean addAll(int index, T[] aArr) {
        int len = aArr.length;
        if (len == 0) return false;
        Node<T> successor = elementAt(index).next;
        Node<T> predecessor = successor.prev;
        for (int i=0; i<len; i++) {
            Node<T> n = new Node<T>(predecessor, aArr[i], successor);
            n.next.prev = n;
            n.prev.next = n;
            predecessor = n;
        }
        successor.prev = predecessor;
        size += len;
        return true;
    }

    public boolean addAll(int index, Collection<? extends T> a) {
        T[] aArr = (T[]) a.toArray();
        return addAll(index, aArr);
    }

    public boolean addAll(Collection<? extends T> a) {
        return addAll(size, a);
    }

    // -- + -- +-> Remove Method <-+ -- + --

    /* In order for the GC to free memory,
     * all existing pointers to elem at index
     * must be removed. */
    public T remove(int index) {
        Node<T> temp = elementAt(index);
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
        temp.prev = temp.next = null;
        size--;
        return temp.elem;
    }

    public void clear() {
        int n = size;
        for (int i = 0; i < n; i++)
            remove(0);
    }

    // -- + -- +-> Get Methods <-+ -- + --

    public T get(int index) {
        return elementAt(index).elem;
    }

    public T get() {
        return header.prev.elem;
    }

    public T getFirst() {
        return header.next.elem;
    }

    // -- + -- +-> Set Methods <-+ -- + --

    public T set(int index, T e) {
        return addBefore(e, elementAt(index)).elem;
    }

    // -- + -- +-> Find Methods <-+ -- + --

    public int indexOf(Object o) {
        int i = 0;
        if (o == null) {
            for (Node n = header.next; n != header; n = n.next) {
                if (n.elem == null)
                    return i;
                i++;
            }
        } else {
            for (Node n = header.next; n != header; n = n.next) {
                if (o.equals(n.elem))
                    return i;
                i++;
            }
        }
        return -1;
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    // -- + -- +-> Misc <-+ -- + --

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public String toString() {
        String buf = new String();
        if (size == 0) return "[]";
        buf += "[";
        for (Node n=header.next; n != header; n = n.next)
            buf += " " + n.elem + ",";
        buf += "\b ]";
        return buf;
    }

    public Object[] toArray() {
        Object[] arr = new Object[size];
        Node<T> cursor = header.next;
        for (int i = 0; i < size; i++) {
            arr[i] = cursor.elem;
            cursor = cursor.next;
        }
        return arr;
    }

    // -- + -- +-> Node <-+ -- + -- 

    /* Static allows instantiation of Node
     * without instantiation of LinkedList.
     * Node is a nested private class in order
     * to lower redundant function calls because
     * they have overhead that adds up w/frequent
     * accesses. */
    private static class Node<T> {
        Node<T> prev;
        T elem;
        Node<T> next;

        Node(Node<T> prev, T elem, Node<T> next) {
            this.prev = prev;
            this.elem = elem;
            this.next = next;
        }
    }

    // -- + -- +-> Iterator Interface <-+ -- + --

    // NOTE: Broken <-------- TODO: Fix

    public Iterator<T> iterator() {
        return new Iter<T>();
    }

    private class Iter<E> implements Iterator<E> {
        // Safety: E is equivalent to T as it's only called from
        // the context of T
        private Node<E> cursor = (Node<E>) header;

        public boolean hasNext() {
            return cursor != header;
        }

        public E next() {
            Node<E> res = cursor;
            cursor = cursor.next;
            return res.elem;
        }
    }
}