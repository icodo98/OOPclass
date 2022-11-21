package deque;
import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = (items.length / 2) - 1;
        nextLast = (items.length / 2);
    }

    @Override
    public Iterator<T> iterator() {return new ADequeIterator();}

    private class ADequeIterator implements Iterator<T> {
        private int wizPos;
        public ADequeIterator() {wizPos = 0;}

        public boolean hasNext() {
            return (wizPos < size);
        }

        public int getPos(){
            return wizPos;
        }

        public T next() {
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }
    }

    @ Override
    public String toString(){
        StringBuilder returnSB = new StringBuilder("{");
        int tmpfirst= nextFirst;
        for (int i = 0; i < size-1; i += 1){
            returnSB.append(get(i).toString());
            returnSB.append(", ");
        }
        returnSB.append(get(size-1));
        returnSB.append("}");
        return  returnSB.toString();
    }

    @Override
    public String printDeque() {
        StringBuilder returnSB = new StringBuilder("");
        if(isEmpty()){
            returnSB = new StringBuilder("");
            System.out.println(returnSB);
            return returnSB.toString();
        }
        for (int i = 0; i < size-1; i += 1){
            returnSB.append(get(i).toString());
            returnSB.append(" ");
        }
        returnSB.append(get(size-1));
//        returnSB.append(" ");
        System.out.println(returnSB);
        return returnSB.toString();
    }


    private void resize(int capacity) {
        T[] new_items = (T[]) new Object[capacity];
        int new_idx = size / 2;
        int prev_size = size;
        while (size > 0){
            new_items[new_idx] = popleft();
            new_idx += 1;
        }

        size = prev_size;
        int new_first = size/2;
        items = new_items;
        nextFirst =  new_first-1;
        nextLast = new_idx;
    }

//    private void shiftRight(){
//        int shift = (int) Math.ceil((float) ((items.length - size) / 2.0));
//        T[] a = (T[]) new Object[items.length];
//        System.arraycopy(items, nextFirst+1, a, shift, size);
//        items = a;
//        nextFirst = shift -1;
//        nextLast = shift + size;
//    }
//
//    private void shiftLeft(){
//        int shift = (int) Math.ceil((float) ((items.length - size) / 2.0));
//        T[] a = (T[]) new Object[items.length];
//        System.arraycopy(items, nextFirst+1, a, nextFirst+1-shift, size);
//        items = a;
//        nextFirst = nextFirst-shift;
//        nextLast = nextFirst + 1 + size ;
//    }

    @Override
    public void addFirst(T item) {
        if (size == items.length){
            resize(size * 2);
        }
//        if (nextFirst < 0){
//            shiftRight();
//        }
        if (nextFirst < 0 && nextLast != items.length){
            nextFirst += items.length;
        }
        items[nextFirst] = item;
        size += 1;
        nextFirst -= 1;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
//        if (nextLast >= items.length){
//            shiftLeft();
//        }
        if (nextLast >= items.length && nextFirst >= 0){
            nextLast -= items.length;
        }
        items[nextLast] = item;
        size += 1;
        nextLast += 1;
    }

    public int size() {return size;}

    public void reduce(int capacity){
        T[] new_items = (T[]) new Object[capacity];
        int new_idx = capacity - size;
        int prev_size = size;
        while (size > 0){
            new_items[new_idx] = popleft();
            new_idx += 1;
        }

        size = prev_size;
        int new_first = capacity-size;
        items = new_items;
        nextFirst =  new_first-1;
        nextLast = new_idx;
    }

    public T popleft() {
        if (nextFirst + 1 >= items.length) {
            nextFirst -= items.length;
        }
        T remove = items[nextFirst + 1];
        items[nextFirst + 1] = null;
        nextFirst += 1;
        size -= 1;
        return remove;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()){return null;}
        if (nextFirst + 1 >= items.length){
            nextFirst -= items.length;
        }
        T remove = items[nextFirst+1];
        items[nextFirst+1] = null;
        nextFirst += 1;
        size -= 1;

        if ((size/(float)items.length) < 0.25 && items.length > 8){
            reduce((int) (items.length*0.25));
        }
        return remove;
    }

    public T removeLast() {
        if (isEmpty()){return null;}
        if (nextLast - 1 < 0){
            nextLast += items.length;
        }
        T remove = items[nextLast-1];
        items[nextLast-1] = null;
        nextLast -= 1;
        size -= 1;
        if ((size/(float)items.length) < 0.25 && items.length > 8){
            reduce(size);
        }
        return remove;
    }


    public T getLast() {
        return items[size - 1];
    }
    public T getFirst() {
        return items[0];
    }
    public T get(int i) {
        int idx = i+this.nextFirst+1;
        if (i>size){
            return null;
        }
        if (idx>= items.length){
            idx -= items.length;
        }
        return items[idx];
    }

    public boolean equals(Object o){
        if (o == null) {if (isEmpty()){return true;} return false;}
        if (this == o) {return true;}
        if (this.getClass() != o.getClass()) { return false;}
        if (! (o instanceof Deque)){return false;}
        ArrayDeque<T> other = (ArrayDeque<T>) o;
        if (this.size() != other.size()) { return false;}
        for (int i = 0; i <= size; i += 1){
            if (other.get(i)!=(get(i))){
                return false;
            }
        }
        return true;
    }
}
