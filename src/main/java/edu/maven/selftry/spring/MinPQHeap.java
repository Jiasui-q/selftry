package edu.maven.selftry.spring;

import java.util.*;

public class MinPQHeap<T> implements MinPQInterface<T> {
    private String owner;
    private ArrayList<PriorityNode> items;
    private int size;
    private HashSet<T> keys;
    private HashMap<T, Integer> index;

    public MinPQHeap() {
        this.items = new ArrayList<>();
        this.size = 0;
        this.keys = new HashSet<>();
        this.index = new HashMap<>();
    }

    public void setOwner(String name){ this.owner = name; }

    public String getOwner(){ return this.owner; }

    public void printOwner(){
        System.out.println(this.owner + "'s min heap.");
    }

    public void setItems(List<PriorityNode> itemList) {
        for (PriorityNode i: itemList) {
            add(i.getItem(), i.getPriority());
        }
    }

    private int parent(int position) {
        return (position - 1) / 2;
    }

    private int leftChild(int position) {
        return 2 * position + 1;
    }

    private int rightChild(int position) {
        return 2 * position + 2;
    }

    private boolean isLeaf(int position) {
        if (position >= (size / 2) && position <= size) {
            return true;
        }
        return false;
    }

    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        PriorityNode tmp = items.get(a);
        index.replace(items.get(a).item, b);
        index.replace(items.get(b).item, a);
        items.set(a, items.get(b));
        items.set(b, tmp);
    }

    /**
     * Adds an item with the given priority value.
     * Assumes that item is never null.
     * Runs in O(log N) time (except when resizing).
     *
     * @throws IllegalArgumentException if item is already present in the PQ
     */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        PriorityNode newItem = new PriorityNode(item, priority);
        keys.add(item);
        items.add(newItem);
        size += 1;
        int pos = size - 1;
        index.put(items.get(pos).item, pos);
        swim(pos);
    }

    private void swim(int pos) {
        if (items.size() > 1) {
            while (pos != 0 && items.get(pos).priority < items.get(parent(pos)).priority) {
                swap(pos, parent(pos));
                pos = parent(pos);
            }
        }
    }

    /**
     * Returns the item with the smallest priority.
     * Runs in O(log N) time.
     *
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T getSmallest() {
        if (items.size() == 0) {
            throw new NoSuchElementException();
        }
        return items.get(0).item;
    }

    /**
     * Removes and returns the item with the smallest priority.
     * Runs in O(log N) time (except when resizing).
     *
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T removeSmallest() {
        if (items.size() == 0) {
            throw new NoSuchElementException();
        }
        PriorityNode popped = items.get(0);
        PriorityNode last = items.get(size - 1);
        items.set(0, last);
        keys.remove(popped.item);
        index.remove(popped.item);
        items.remove(size - 1);
        index.replace(last.item, 0);
        size--;
        sink(0);
        return popped.item;
    }

    private void sink(int pos) {
        if (!isLeaf(pos)) {
            if (items.get(pos).priority > items.get(leftChild(pos)).priority
                    || (rightChild(pos) <= size - 1 && items.get(pos).priority > items.get(rightChild(pos)).priority)) {
                if (rightChild(pos) > size - 1 ||
                        items.get(leftChild(pos)).priority < items.get(rightChild(pos)).priority) {
                    swap(pos, leftChild(pos));
                    sink(leftChild(pos));
                } else {
                    swap(pos, rightChild(pos));
                    sink(rightChild(pos));
                }
            }
        }
    }

    /**
     * Changes the priority of the given item.
     * Runs in O(log N) time.
     *
     * @throws NoSuchElementException if the item is not present in the PQ
     */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        int where = index.get(item);
        items.get(where).setPriority(priority);
        sink(where);
        swim(where);
    }

    /**
     * Returns true if the PQ contains the given item; false otherwise.
     * Runs in O(log N) time.
     */
    @Override
    public boolean contains(T item) {
        return keys.contains(item);
    }

    /**
     * Returns the number of items in the PQ.
     * Runs in O(log N) time.
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public void printArray(){
        String separator = " ";
        items.forEach(e -> System.out.print(e.getItem() + separator));
        System.out.println();
    }

    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        public PriorityNode(){}

        public PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        public T getItem() { return item; }

        public void setItem(T e) {this.item = e;}

        public double getPriority() {
            return priority;
        }

        public void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }

    }
}
