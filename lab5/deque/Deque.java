package deque;
import java.util.Iterator;

public interface Deque <T>{
    void addFirst(T item);
    void addLast(T item);
    default boolean isEmpty(){
        return this.size() == 0;
    }
    int size();
    String printDeque();
    T removeFirst();
    T removeLast();
    T get(int idx);
    Iterator<T> iterator();
}
