package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.size = 0;
    }

    @Override
    public void add(T value) {

        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(null, value, null); //1
        Node<T> currentNode = getNode(index); //1 → 2

        if (index == 0) {
            head = newNode;
        }

        newNode.previous = currentNode.previous;
        newNode.next = currentNode;
        if (index != 0) {
            currentNode.previous.next = newNode;
        }
        currentNode.previous = newNode;

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T data : list) {
            add(data);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).data;
    }

    @Override
    public T set(T value, int index) {
        T currentData = getNode(index).data;
        getNode(index).data = value;
        return currentData;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNode(index);
        unlink(currentNode);
        size--;
        return currentNode.data;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if ((get(i) == null && object == null)
                    || (get(i) != null && get(i).equals(object))) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);

        boolean isIndexInFirstHalf = index <= size / 2;

        int i = isIndexInFirstHalf ? 0 : size - 1;
        Node<T> currentNode = isIndexInFirstHalf ? head : tail;

        while (i != index) {
            currentNode = isIndexInFirstHalf ? currentNode.next : currentNode.previous;
            i = isIndexInFirstHalf ? i + 1 : i - 1;
        }

        return currentNode;
    }

    private void unlink(Node<T> currentNode) {

        if (currentNode == head) {
            head = currentNode.next;
        } else {
            currentNode.previous.next = currentNode.next;
        }

        if (currentNode == tail) {
            tail = currentNode.previous;
        } else {
            currentNode.next.previous = currentNode.previous;
        }

        currentNode.previous = null;
        currentNode.next = null;
    }

    private class Node<T> {
        private Node<T> previous;
        private T data;
        private Node<T> next;

        public Node(Node<T> previous, T data, Node<T> next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }
    }
}
