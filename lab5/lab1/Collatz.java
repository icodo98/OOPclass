/** Class that prints the Collatz sequence starting from a given number.
 *  @author YOUR NAME HERE
 */
public class Collatz {

    /** if n is odd nextnumber is 3n+1, n is even nextnumber n/2 */
    public static int nextNumber(int n) {
        if (n % 2 == 1) {
            return 3 * n + 1;
        } else {
            return n / 2;
        }
    }

    public static void main(String[] args) {
        int n = 129;
        System.out.print(n + " ");
        while (n != 1) {
            n = nextNumber(n);
            System.out.print(n + " ");
        }
        System.out.println();
    }
}

