package randomizedtest;

import afu.org.checkerframework.checker.oigj.qual.World;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import timingtest.AList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove(){
        AList CorrectAList = new AList<Integer>();
        BuggyAList WrongAList = new BuggyAList<Integer>();

        for (int k = 4; k <7 ; k++) {
            CorrectAList.addLast(k);
            WrongAList.addLast(k);
        }
        assertEquals(CorrectAList.getLast(),WrongAList.getLast());
        assertEquals(CorrectAList.getLast(),WrongAList.getLast());
        assertEquals(CorrectAList.getLast(),WrongAList.getLast());
    }
    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> CorrectAList = new AListNoResizing<>();
        BuggyAList<Integer> WrongAList = new BuggyAList<>();


        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                CorrectAList.addLast(randVal);
                WrongAList.addLast(randVal);
                //System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int csize = CorrectAList.size();
                int wsize = WrongAList.size();
                assertEquals(csize,wsize);
                //System.out.println("size: " + csize);
            } else if (operationNumber == 2) {
                //getLast
                if(CorrectAList.size() == 0) continue;
                else {
                    int getcVal = CorrectAList.getLast();
                    int getwVal = WrongAList.getLast();
                    assertEquals(getcVal,getwVal);
                    //System.out.println("getLast(" + getcVal + ")");
                }
            } else if (operationNumber == 3) {
                //removeLast
                if(CorrectAList.size() == 0) continue;
                else {
                    int removedcVal = CorrectAList.removeLast();
                    int removedwVal = WrongAList.removeLast();
                    assertEquals(removedwVal,removedcVal);
                    //System.out.println("removeLast(" + removedcVal + ")");
                }
            }
        }
    }
}
