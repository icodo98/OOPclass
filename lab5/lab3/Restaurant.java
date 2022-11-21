public class Restaurant {
    public String name;
    public double rating;
    public boolean dinein;

    public Restaurant(String name){
        this.name = name;
    }

    public Restaurant() { }

    public void increaseRating(double inc){
        rating += inc;
    }
    public static void restCall(){
        System.out.println("Hello Restaurant!");
    }
}
