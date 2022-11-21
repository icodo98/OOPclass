package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
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
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        AList<Integer> ns_list = new AList<Integer>();
        AList<Integer> op_list = new AList<Integer>();
        AList<Double> t_list = new AList<Double>();

        SLList<Integer> n_list = new SLList<>();

        int N = 128000;
        int max_ops = 10000;

        int exp = 0;
        for (int i = 0; i < N; i += 1) {
            n_list.addLast(i);
            if (n_list.size() == Math.pow(2, exp)*1000) {
                int j;
                Stopwatch sw = new Stopwatch();
                for (j = 0; j < max_ops; j += 1) {
                    n_list.getLast();
                }
                double timeInSeconds = sw.elapsedTime();
                t_list.addLast(timeInSeconds);
                op_list.addLast(j);
                ns_list.addLast(i+1);
                exp += 1;
            }
        }
        printTimingTable(ns_list, t_list, op_list);
    }
}
