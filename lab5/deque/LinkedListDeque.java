package deque;

import java.util.Iterator;

public class LinkedListDeque <T> implements Deque<T>, Iterable<T> {
    private class IntNode {
        public T items;
        public IntNode next;
        public IntNode prev;

        public IntNode(T i, IntNode n){
            items = i;
            next = n;
        }

        public IntNode(Integer i, IntNode n) {
            items = (T) i;
            next = n;
        }
    }

    private IntNode sentinel;
    private int size;

    public LinkedListDeque(){
        Integer i = -1;
        sentinel = new IntNode(i, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public LinkedListDeque(T x){
        Integer i = 63;
        sentinel = new IntNode(i, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
        this.addFirst(x);
    }

    @Override
    public Iterator<T> iterator() {
        return new LLDequeIterator();
    }

    private class LLDequeIterator implements Iterator<T> {
        private int wizPos;
        public LLDequeIterator() {wizPos = 0;}

        public boolean hasNext() {return wizPos < size;}

        public T next() {
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }
    }

    @Override
    public String toString() {
        StringBuilder returnSB = new StringBuilder("{");
        for (int i = 0; i < size - 1; i += 1) {
            returnSB.append(get(i).toString());
            returnSB.append(", ");
        }
        returnSB.append(get(size - 1));
        returnSB.append("}");
        return returnSB.toString();
    }

    public void insertConnection(IntNode new_node, IntNode old_node){
        old_node.prev.next = new_node;
        new_node.prev = old_node.prev;
        old_node.prev = new_node;
        new_node.next = old_node;
    }

    public void insert(int i, T x){
        IntNode l = this.getNode(i);
        size += 1;
        IntNode new_node = new IntNode(x, null);
        insertConnection(new_node, l);
    }

    public void addFirst(T x){
        size += 1;
        IntNode new_node = new IntNode(x, null);
        insertConnection(new_node, sentinel.next);
    }

    public void addLast(T x){
        size += 1;
        IntNode new_node = new IntNode(x, null);
        insertConnection(new_node, sentinel);
    }

    public int size(){return size;}

    @Override
    public String printDeque() {
        StringBuilder returnSB = new StringBuilder("");
        if (isEmpty()){
            returnSB = new StringBuilder("");
            System.out.println(returnSB);
            return returnSB.toString();
        }
        IntNode l = sentinel.next;
        while (l.next != sentinel) {
            returnSB.append(l.items.toString());
            returnSB.append(" ");
            l = l.next;
        }
        returnSB.append(l.items.toString());
//        returnSB.append(" ");
        System.out.println(returnSB);
        return returnSB.toString();
    }


    public IntNode getNode(int i) {
        if (i >= size || i < 0){
            return null;
        }
        IntNode l = sentinel;
        if (i >= 0 ) {
            while (i > 0) {
                l = l.next;
                i -= 1;
            }
            return l.next;
        }
        else{
            while (i < 0){
                l = l.prev;
                i += 1;
            }
            return l;
        }
    }

    public T get(int i) {
//        if (i >= size || i < -(size)){
//            return null;
//        }
//        IntNode l = sentinel;
//        if (i >= 0 ) {
//            while (i > 0) {
//                l = l.next;
//                i -= 1;
//            }
//            return l.next.items;
//        }
//        else{
//            while (i < 0){
//                l = l.prev;
//                i += 1;
//            }
//            return l.items;
//        }
        IntNode l = getNode(i);
        if (l == null){
            return null;
        }
        return l.items;
    }

    public T recursive(int i, IntNode l){
        if (i <  0 || i >= size){
            return null;
        }
        if (i == 0){
            return l.next.items;
        }
        l = l.next;
        return recursive(i-1, l);
    }

    public T recursiveGet(int i){
        return recursive(i, sentinel);
    }

    public T getFirst(){
        return sentinel.next.items;
    }

    public T getLast(){
        return sentinel.prev.items;
    }


    public void deleteConnection(IntNode del_node){
        del_node.prev.next = del_node.next;
        del_node.next.prev = del_node.prev;
    }

    public void remove(int i){
        if (isEmpty()){return;}
        IntNode l = this.getNode(i);
        size -= 1;
        deleteConnection(l);
    }

    public T removeFirst(){
        if (isEmpty()){return null;}
        IntNode del_node = sentinel.next;
        deleteConnection(del_node);
        size -= 1;
        return del_node.items;
    }

    public T removeLast(){
        if (isEmpty()){return null;}
        IntNode del_node = sentinel.prev;
        deleteConnection(del_node);
        size -= 1;
        return del_node.items;
    }

    public T pop(){
        if (isEmpty()){return null;}
        IntNode del_node = getNode(-1);
        deleteConnection(sentinel.prev);
        size -= 1;
        return del_node.items;
    }

    public T popleft(){
        if (isEmpty()){return null;}
        IntNode del_node = getNode(0);
        deleteConnection(sentinel.prev);
        size -= 1;
        return del_node.items;
    }

    public void appendleft(T x){
        size += 1;
        IntNode new_node = new IntNode(x, null);
        insertConnection(new_node, sentinel.next);
    }

    public void append(T x){
        size += 1;
        IntNode new_node = new IntNode(x, null);
        insertConnection(new_node, sentinel);
    }

    public boolean equals(Object o){
        if (! (o instanceof Deque)){return false;}
        if (o == null) {if (isEmpty()){return true;} return false;}
        if (this == o) {return true;}
        if (this.getClass() != o.getClass()) { return false;}
        LinkedListDeque<T> other = (LinkedListDeque<T>) o;
        if (this.size() != other.size()) { return false;}
        for (int i =0; i < size; i += 1){
            if (other.get(i)!=(get(i))){
                return false;
            }
        }
        return true;
    }

}
