package timingtest;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;
import org.checkerframework.checker.units.qual.A;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        AList Ns = new AList<Integer>();
        AList times = new AList<Double>();
        AList opCounts = new AList<Integer>();
        AList test = new AList();
        int opCount = 0;
        Double StartTime = 0.0;
        Double EndTime = 0.0;


        Stopwatch sw = new Stopwatch();
        for (int i = 1; i < 256; i *= 2) {
            Ns.addLast(i*1000);
        }
        for (int j = 0 ; j < Ns.size(); j++ ){
            test = new AList<Integer>();
            opCount = 0;
            StartTime = sw.elapsedTime();
            for (int k = 0; k < (int)Ns.get(j); k++) {
                test.addLast(k);
                opCount++;
            }
            EndTime =sw.elapsedTime();
            times.addLast(EndTime - StartTime);
            opCounts.addLast(opCount);
        }
        printTimingTable(Ns,times,opCounts);
    }
}
