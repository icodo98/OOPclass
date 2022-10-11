package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private Comparator<T> compare;
    public MaxArrayDeque(Comparator<T> c){
        super();
        compare = c;
    }

    /**
     * returns the maximum element in the deque as governed by the previous given Comparator.
     * if the MaxArrayDeque is empty, simply return null.
     * @return
     */
    public T max(){
        if(this.isEmpty()) return null;
        T maxVal = (T) this.get(0);
        for (T x : this){
            if(compare.compare(maxVal,x) < 0){
                maxVal = x;
            }
        }
        return maxVal;
    }
    /**
     * returns the maximum element in the deque as governed by the parameter Comparator c.
     * if the MaxArrayDeque is empty, simply return null.
     * @return
     */
    public T max(Comparator<T> c){
        if(this.isEmpty()) return null;
        T maxVal = (T) this.get(0);
        for (T x : this){
            if(c.compare(maxVal,x) < 0){
                maxVal = x;
            }
        }
        return maxVal;
    }
}
