public class LinkedListDeque<T> implements Deque<T> {
    private class TNode {
        private T item;
        private TNode next;
        private TNode previous;

        TNode(T i, TNode n, TNode p) {
            item = i;
            next = n;
            previous = p;
        }
    }

    /* The first item (if it exists) is at sentinel.next. */
    private TNode sentinel;
    private int size;

    public LinkedListDeque() {
        size = 0;
        sentinel = new TNode(null, new TNode(null, null, null), new TNode(null, null, null));

    }

    /*
    Adds an item of type T to the front of the deque.
     */
    @Override
    public void addFirst(T item) {
        if (isEmpty()) {
            sentinel.next = new TNode(item, sentinel, sentinel);
            sentinel.previous = sentinel.next;
        } else {
            sentinel.next = new TNode(item, sentinel.next, sentinel);
            sentinel.next.next.previous = sentinel.next;
        }
        size += 1;
    }

    /*
    Adds an item of type T to the back of the deque.
     */
    @Override
    public void addLast(T item) {
        if (isEmpty()) {
            sentinel.next = new TNode(item, sentinel, sentinel);
            sentinel.previous = sentinel.next;
        } else {
            sentinel.previous = new TNode(item, sentinel, sentinel.previous);
            sentinel.previous.previous.next = sentinel.previous;
        }
        size += 1;

    }

    /*
    Returns true if deque is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /*
    Returns the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /*
    Removes and returns the item at the front of the deque. If no such item exists, returns null.
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T value = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.previous = sentinel;
        size -= 1;
        return value;
    }


    /*
    Removes and returns the item at the end of the deque. If no such item exists, returns null.
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T value = sentinel.previous.item;
        sentinel.previous = sentinel.previous.previous;
        sentinel.previous.next = sentinel;
        size -= 1;
        return value;
    }

    /*
    Prints the items in the deque from first to last, separated by a space.
     */
    @Override
    public void printDeque() {
        if (isEmpty()) {
            System.out.println("null");
            return;
        }
        TNode temp = sentinel;
        while (temp.next.item != null) {
            System.out.print(temp.next.item + " ");
            temp = temp.next;
        }

        System.out.println();
    }

    /*
    Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
    If no such item exists, returns null. Must not alter the deque!
     */
    @Override
    public T get(int index) {
        if (isEmpty() || (size - 1) < index) {
            return null;
        }

        TNode temp = sentinel;
        while (index >= 0) {
            index -= 1;
            temp = temp.next;
        }

        return temp.item;
    }

    /*
    Same as get, but uses recursion.
     */
    public T getRecursive(int index) {
        if (isEmpty() || (size - 1) < index) {
            return null;
        } else {
            return getRecursive(index, sentinel);
        }
    }

    private T getRecursive(int index, TNode deque) {
        if (index == 0) {
            return deque.next.item;
        } else {
            return getRecursive(index - 1, deque.next);
        }
    }
}
