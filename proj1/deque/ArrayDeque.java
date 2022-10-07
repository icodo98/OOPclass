package deque;

import java.util.Iterator;

public class ArrayDeque<T> {
    private int size;
    private int capacity;
    private T[] items;
    private int NextFirst;
    private int NextLast;


    /**
     * Creates an empty array deque.
     */
    public ArrayDeque(){
        capacity = 8;
        items = (T[]) new Object[capacity];
        size = 0;
        NextFirst = 0;
        NextLast = 1;
    }
    /**
     * Updates NextFirst and NextLast.
     * @param mode 0 : NextFirst, 1 : NextLast
     *
     */
    private int UpdateIndex(int mode, int Next){
        if(mode == 0){
            //Updates NextFirst
            if(Next == 0) Next = this.items.length - 1;
            else Next--;
        }
        else if(mode == 1){
            //Updates NextLast
            if(Next == this.items.length - 1) Next = 0;
            else Next++;
        }
        return Next;
    }

    /**
     * Resizes the array with fixed factor 1.5. <br>
     * New items array will contain a value starts with index 0.
     */
    private void resize(int capacity){
        T[] temp = (T[]) new Object[capacity];
        int First = UpdateIndex(1,NextFirst);
        System.arraycopy(items,First,temp,0,items.length - First);
        System.arraycopy(items,0,temp,items.length - First,NextLast);
        NextLast = size;
        NextFirst = temp.length - 1;
        items = temp;
    }
    /**
     * Adds an item of type T to the front of the deque. You can assume that item is never null.
     */
    public void addFirst(T item){
        if(size == items.length){
            resize((int) (capacity * 1.5));
        }
        size++;
        items[NextFirst] = item;
        NextFirst =  UpdateIndex(0,NextFirst);
    }

    /**
     * Adds an item of type T to the back of the deque. You can assume that item is never null.
     * @param item
     */
    public void addLast(T item){
        if(size == items.length){
            resize((int) (capacity * 1.5));
        }
        size++;
        items[NextLast] = item;
        NextLast = UpdateIndex(1,NextLast);
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        if(size == 0) return true;
        else return false;
    }

    /**
     * Returns the number of items in the deque.
     */
    public int size() {
        return size;

    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        int currentIndex = UpdateIndex(1,NextFirst);
        while (currentIndex < NextLast){
            System.out.print(items[currentIndex++] + " ");
        }
        System.out.print("\n");
    }
    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     * @return
     */
    public T removeFirst() {
        size--;
        if(size < (int) (capacity * 0.25)){
            resize((int) (capacity * 0.5));
        }
        int currentFirst = UpdateIndex(1,NextFirst);
        NextFirst = currentFirst;
        return items[currentFirst];
    }
    /**
     *  Removes and returns the item at the back of the deque. If no such item exists, returns null.
     * @return
     */
    public T removeLast() {
        size--;
        if(size < (int) (capacity * 0.25)){
            resize((int) (capacity * 0.5));
        }
        int currentLast = UpdateIndex(0,NextLast);
        NextLast = currentLast;
        return items[currentLast];
    }
    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index) {
        if(index > size - 1) return null;
        index = index + UpdateIndex(1,NextFirst);
        if(index >= items.length) index = index - items.length;
        return items[index];
    }

    /**
     *  The Deque objects we’ll make are iterable (i.e. Iterable<T>) so we must provide this method to return an iterator.
     * @return
     */
    public Iterator<T> iterator() {
        return null;
    }

    /**
     * Returns whether the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque
     * and if it contains the same contents (as goverened by the generic T’s equals method) in the same order.
     * @param o
     * @return
     */
    public boolean equals(Object o) {
        if(o instanceof ArrayDeque) return false;
        boolean isSame = true;
        ArrayDeque<T> testingObj = new ArrayDeque<>();
        if(this.size != testingObj.size()) return false;
        for (int i = 0; i < testingObj.size; i++) {
            if (!this.get(i).equals(testingObj.get(i))) isSame = false;
        }
        return isSame;
    }
}
