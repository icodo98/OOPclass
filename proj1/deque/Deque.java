package deque;

import java.util.Iterator;

public interface Deque<T> {
    void addFirst(T item);
    void addLast(T item);
    default boolean isEmpty(){
        if(this.size() == 0) return true;
        else return false;
    };
    int size();
    void printDeque();
    T removeFirst();
    T removeLast();
    T get(int index);
    Iterator<T> iterator();
    boolean equals(Object o);

}
