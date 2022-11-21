public class LagerDemo {

    public static  boolean larger(int a, int b){
        return a>b;
    }
    public static  boolean larger(double a, double b){
        return a>b;
    }
    public static  boolean larger(String a, String b) {
        return a.compareTo(b) < 0;
    }
    public static void main(String[] args){
        System.out.println(larger(2,4));
        System.out.println(larger(3.2,2.4));
        System.out.println(larger("Apple","year"));
    }
}
