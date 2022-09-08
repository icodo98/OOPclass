/** Class that prints the Collatz sequence starting from a given number.
 *  @author JUYOUNG_LEE
 */
public class Collatz {

    /** Buggy implementation of nextNumber! */
    public static int nextNumber(int n) {
        if (n  == 256) {
            return 1;
        } else if (n == 1) {
            return 1;
        } else if ((n % 2) != 0) {
            //if n is odd, next number is 3n +1
            return 3 * n + 1;
        } else {
            //if n is even, next number is n/2
            return n / 2;
        }
    }

    public static void main(String[] args) {
        int n = 64;
        System.out.print(n + " ");
        while (n != 1) {
            n = nextNumber(n);
            System.out.print(n + " ");
        }
        System.out.println();
    }
}

