package gh2;

import deque.ArrayDeque;
import deque.Deque;

public class Drums implements Instruments {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = 1.0; // energy decay factor
    private int capacity;

    /* Buffer for storing sound data. */
    private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public Drums(double frequency) {
        buffer = new ArrayDeque<>();
        this.capacity =(int) Math.round(SR/frequency);
        for (int i = 0; i < this.capacity ; i++) {
            buffer.addLast((double) 0);
        }

    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        for (int i = 0; i < buffer.size(); i++) {
            buffer.removeFirst();
            buffer.addLast(Math.random()-0.5);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double NewDouble = (buffer.removeFirst()+buffer.get(0))*0.5*DECAY;
        if(Math.random() < 0.5) buffer.addLast(NewDouble);
        else buffer.addLast(-NewDouble);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }

}
