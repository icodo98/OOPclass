package deque;

import java.util.Iterator;

public class LinkedListDeque<T> {
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
        SentinelNode = new DNode<>();
        SentinelNode.next = SentinelNode;
        SentinelNode.prev = SentinelNode;
        size = 0;
    }
    /**
     * Adds an item of type T to the FRONT of the deque. item is never null*/
    public void addFirst(T item){
        DNode<T> temp = new DNode<>();
        temp.data = item;
        temp.prev = SentinelNode;
        temp.next = SentinelNode.next;
        SentinelNode.next.prev = temp;
        SentinelNode.next = temp;
        size++;

    }

    /**
     * Adds an item of type T to the BACK of the deque. item is never null */
    public void addLast(T item){
        DNode<T> temp = new DNode<>();
        temp.data = item;
        temp.next = SentinelNode;
        temp.prev = SentinelNode.prev;
        SentinelNode.prev.next = temp;
        SentinelNode.prev = temp;
        size++;
    }

    /**
     * Returns true if deque is empty.
     */
    public boolean isEmpty(){
        if(size == 0) return true;
        else return false;
    }

    /**
     * Returns the number of items in the deque.
     */
    public int size(){
        return size;
    }
    /**
     * Prints the items in the deque from first to last, seperated by space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque(){
        DNode<T> thisNode = SentinelNode.next;
        while (thisNode != SentinelNode){
            System.out.print(thisNode.data + " ");
            thisNode = thisNode.next;
        }
        System.out.print("\n");
    }
    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public T removeFirst(){
        if(isEmpty()) return null;
        T data = SentinelNode.next.data;
        SentinelNode.next.next.prev = SentinelNode;
        SentinelNode.next = SentinelNode.next.next;
        size--;
        return data;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast(){
        if(isEmpty()) return null;
        T data = SentinelNode.prev.data;
        SentinelNode.prev.prev.next = SentinelNode;
        SentinelNode.prev = SentinelNode.prev.prev;
        size--;
        return data;

    }
    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next.
     * If no such item exists, returns null. Must not alter the deque.
     */
     public T get(int index){
        DNode<T> tempNode = SentinelNode.next;
        if(isEmpty()) return null;
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
    };
    /**
     * Returns an iterator
     */
    public Iterator<T> iterator(){
        return null;
    }
    /**
     * Returns whether the parameter o is equal to the Deque.
     * o is consideted equal if Deque and if it contains the same contents in same order.
     */
    public boolean equals(Object obj) {
        boolean isDeque = obj instanceof LinkedListDeque;
        boolean isSameOrder = false;
        DNode<T> thisNode = SentinelNode.next;
        LinkedListDeque<T> testingObj = new LinkedListDeque<>();
        int i = 0;
        if(isDeque){
            testingObj = (LinkedListDeque<T>) obj;
        }
        while (thisNode != SentinelNode && i > testingObj.size()) {
            thisNode.data.equals(testingObj.get(i));
            i++;
            thisNode = thisNode.next;
        }
        if((i == testingObj.size()) && (testingObj.size() == this.size) ) isSameOrder = true;


        return isSameOrder & isDeque;
    }
}
