package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c){
        super();
        comparator = c;
    }


    public T max(){
        if (isEmpty()){
            return null;
        }
        T max = get(0);
        for(int i=1; i < this.size(); i ++){
            T newi = this.get(i);
            if (0 < comparator.compare(newi, max)) {
                max = newi;
            }
        }
        return max;
    }

    public T max(Comparator<T> c){
        if (isEmpty()){
            return null;
        }
        T max = get(0);
        for(int i=1; i < this.size(); i ++){
            T newi = this.get(i);
            if (0 < c.compare(newi, max)) {
                max = newi;
            }
        }
        return max;
    }

}
