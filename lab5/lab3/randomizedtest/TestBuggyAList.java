package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import timingtest.AList;
import timingtest.SLList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE

    @ Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> fixed_list = new AListNoResizing<>();
        BuggyAList bug_list = new BuggyAList();

        fixed_list.addLast(4);
        fixed_list.addLast(5);
        fixed_list.addLast(6);
        bug_list.addLast(4);
        bug_list.addLast(5);
        bug_list.addLast(6);

        assertEquals(fixed_list.size(), bug_list.size());

        assertEquals(fixed_list.removeLast(), bug_list.removeLast());
        assertEquals(fixed_list.removeLast(), bug_list.removeLast());
        assertEquals(fixed_list.removeLast(), bug_list.removeLast());
        System.out.println("Test Passed");

    }

    @ Test
    public void rnadomizedTest(){
        AListNoResizing<Integer> fixed_list = new AListNoResizing<>();
        BuggyAList bug_list = new BuggyAList();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                fixed_list.addLast(randVal);
                bug_list.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
                assertEquals(fixed_list.getLast(), bug_list.getLast());
            }
            else if (operationNumber == 1) {
                // size
                int size = fixed_list.size();
                int b_size = bug_list.size();
                System.out.println("size: " + size);
                assertEquals(size, b_size);
            }
            else if (operationNumber == 2) {
                // getLast
                if (fixed_list.size()>0 && bug_list.size()>0) {
                    int last = fixed_list.getLast();
                    int b_last = (int) bug_list.getLast();
                    System.out.println("Last:" + last + " Buggy Last: " + b_last);
                    assertEquals(b_last,last);
                }
            }
            else if (operationNumber == 3) {
                // removeLast
                if (fixed_list.size()>0 && bug_list.size()>0) {
                    int last =  fixed_list.removeLast();
                    int b_last = (int) bug_list.removeLast();

                    System.out.println("Remove: " + last + " Buggy Remove: " + b_last );
                    assertEquals(b_last,last);
                }

            }

            }
        }
    }

