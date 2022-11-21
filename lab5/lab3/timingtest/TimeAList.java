package timingtest;

import edu.princeton.cs.algs4.Stopwatch;

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
        AList<Integer> dummy = new AList<Integer>();
        AList<Integer> ns_list = new AList<Integer>();
        AList<Double> t_list = new AList<Double>();
        // AList<Integer> op_list = new AList<Integer>();

        int N = 128000;

        Stopwatch sw = new Stopwatch();
        int exp = 0;
        for (int i = 0; i < N; i += 1) {
            dummy.addLast(i);
            if (dummy.size() == Math.pow(2, exp)*1000) {
                double timeInSeconds = sw.elapsedTime();
                t_list.addLast(timeInSeconds);
                ns_list.addLast(i+1);
                exp += 1;
            }
        }
        printTimingTable(ns_list, t_list, ns_list);
    }
}
