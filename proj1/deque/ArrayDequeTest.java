package deque;

import jh61b.junit.In;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.Optional;

import static org.junit.Assert.*;
public class ArrayDequeTest {
    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        ArrayDeque<String> lld1 = new ArrayDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();

    }
    @Test
    public void IteratorTest(){
        Deque<Integer> lld1 = new ArrayDeque<>();
        Deque<String> lld2 = new ArrayDeque<>();

        lld2.addFirst("First");
        lld2.addFirst("Second");
        lld2.addFirst("Third");
        for (int i = 0; i < 5; i++) {
            lld1.addLast(i);
        }

        Iterator<Integer> test1 = lld1.iterator();
        Iterator<String> test2 = lld2.iterator();

        for (int i = 0; i <20 ; i++) {
            assertEquals(true,test1.hasNext());
            assertEquals(true,test2.hasNext());
        }
        for (int i = 0; i < 5 ; i++) {
            System.out.println("testcase for "+ i);
            System.out.println(test1.hasNext());
            System.out.println(test1.next());
        }
        assertEquals("Third",test2.next());
        assertEquals("Second",test2.next());
        assertEquals("First",test2.next());

        assertFalse(test1.hasNext());
        assertFalse(test2.hasNext());
        assertEquals(null,test1.next());
        assertEquals(null,test2.next());
    }
    @Test
    public void ResizeTest(){
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        for (int i = 0; i < 8 ; i++) {
            lld1.addFirst(i);
        }

        lld1.addFirst(9);
        assertEquals(9,(int) lld1.get(0));



    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {


        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {


        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);

    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {


        ArrayDeque<String>  lld1 = new ArrayDeque<String>();
        ArrayDeque<Double>  lld2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> lld3 = new ArrayDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();

    }

    @Test
    /* check if null is return when removing from an empty ArrayDeque. */
    public void emptyNullReturnTest() {


        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }


    }
    @Test
    public void getSimpleTest(){
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 5 ; i++) {
            lld1.addLast(i);
        }
        assertEquals(0, (int)lld1.get(0));
        assertEquals(1, (int)lld1.get(1));
        assertEquals(2, (int)lld1.get(2));
        assertEquals(3, (int)lld1.get(3));
        assertEquals(4, (int)lld1.get(4));
    }
    @Test
    public void getAddandRemoveTest(){
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 5 ; i++) {
            lld1.addLast(i);
        }
        lld1.removeFirst();
        assertEquals(1, (int)lld1.get(0));
        lld1.removeLast();
        assertEquals(3, (int)lld1.get(2));
        assertEquals(null,lld1.get(4));
    }

    @Test
    public void equalsTest(){
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        ArrayDeque<Integer> lld2 = new ArrayDeque<Integer>();
        for (int i = 0; i < 5 ; i++) {
            lld1.addLast(i);
            lld2.addLast(i);
        }
        assertEquals("Same instance test",true,lld1.equals(lld1));
        assertEquals("Different instance test",true,lld1.equals(lld2));
        lld1.removeFirst();
        assertEquals("Different instance and size test",false,lld1.equals(lld2));
    }

}