package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    private int size;
    private DNode<T> SentinelNode;
    private static class DNode<T>{
        T data;
        DNode<T> prev;
        DNode<T> next;

        DNode(){
            this.data = null;
            this.prev = null;
            this.next = null;
        }

    }
    public LinkedListDeque(){
        this.SentinelNode = new DNode<>();
        this.SentinelNode.next = this.SentinelNode;
        this.SentinelNode.prev = this.SentinelNode;
        this.size = 0;
    }
    @Override
    /**
     * Adds an item of type T to the FRONT of the deque. item is never null*/
    public void addFirst(T item){
        DNode<T> temp = new DNode<>();
        temp.data = item;
        temp.prev = this.SentinelNode;
        temp.next = this.SentinelNode.next;
        this.SentinelNode.next.prev = temp;
        this.SentinelNode.next = temp;
        this.size++;

    }
    @Override
    /**
     * Adds an item of type T to the BACK of the deque. item is never null */
    public void addLast(T item){
        DNode<T> temp = new DNode<>();
        temp.data = item;
        temp.next = this.SentinelNode;
        temp.prev = this.SentinelNode.prev;
        this.SentinelNode.prev.next = temp;
        this.SentinelNode.prev = temp;
        this.size++;
    }
    @Override
    /**
     * Returns the number of items in the deque.
     */
    public int size(){
        return this.size;
    }
    @Override
    /**
     * Prints the items in the deque from first to last, seperated by space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque(){
        DNode<T> thisNode = this.SentinelNode.next;
        while (thisNode.next != this.SentinelNode){
            System.out.print(thisNode.data + " ");
            thisNode = thisNode.next;
        }
        System.out.println(thisNode.data);
    }
    @Override
    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public T removeFirst(){
        if(this.size == 0) return null;
        T data = this.SentinelNode.next.data;
        this.SentinelNode.next.next.prev = this.SentinelNode;
        this.SentinelNode.next = this.SentinelNode.next.next;
        this.size--;
        return data;
    }
    @Override
    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast(){
        if(this.size == 0) return null;
        T data = this.SentinelNode.prev.data;
        this.SentinelNode.prev.prev.next = this.SentinelNode;
        this.SentinelNode.prev = this.SentinelNode.prev.prev;
        this.size--;
        return data;

    }
    @Override
    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next.
     * If no such item exists, returns null. Must not alter the deque.
     */
     public T get(int index){
        DNode<T> tempNode = SentinelNode.next;
        if(this.size == 0) return null;
         while (index > 0 && tempNode != SentinelNode){
             tempNode = tempNode.next;
             index--;
         }
         if(index == 0) return tempNode.data;
         else return null;
         
     }
    /**
     * Same as get, but uses recursion.
     * Implement with helper method that finds the target Node with recursion.
     */
    public T getRecursive(int index){
        DNode<T> targetNode = getRecursive(SentinelNode.next,index);
        if(targetNode == null) return null;
        else return targetNode.data;

    }
    private DNode<T> getRecursive(DNode<T> node, int index){
        if(index == 0) return node;
        if(node == this.SentinelNode) return null;
        return getRecursive(node.next, --index);
    }
    @Override
    /**
     * Returns an iterator
     */
    public Iterator<T> iterator(){
        return new LLDequeIterator();
    }

    private class LLDequeIterator implements Iterator<T>{
        private DNode<T> currentNode;
        public LLDequeIterator (){
            currentNode = LinkedListDeque.this.SentinelNode.next;
        };
        public boolean hasNext() {
            if(currentNode == LinkedListDeque.this.SentinelNode) return false;
            else return true;
        }

        public T next(){
            if(!this.hasNext()){
                T currentData = this.currentNode.data;
                this.currentNode = this.currentNode.next;
                return currentData;
            }
            return null;
        }
    }
    @Override
    /**
     * Returns whether the parameter o is equal to the Deque.
     * o is consideted equal if Deque and if it contains the same contents in same order.
     */
    public boolean equals(Object obj) {
        if (obj instanceof LinkedListDeque){
            //check size
            LinkedListDeque<T> testingObj = (LinkedListDeque<T>) obj;
            if(testingObj.size() != this.size) return false;
            Iterator<T> Titerator = testingObj.iterator();
            T testdata;
            for(T x : this){
                if(Titerator.hasNext()){
                    testdata = Titerator.next();
                    if(testdata != x) return false;
                }
            }
            return true;
        }
        return false;
    }

}
