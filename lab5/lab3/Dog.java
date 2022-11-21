import java.awt.*;

public class Dog {
    public int weight;
    public static String binomen = "Canis";
    public Dog(int startWeight){
        weight = startWeight;
        System.out.println("New Dog! " + weight +" Pound Dog!");
    }

    public void makeNoise(){
        if (weight < 10){
            System.out.println("yip");
        }
        else if (weight < 30){
            System.out.println("bark");
        }
        else{
            System.out.println("woof");
        }
    }
    public static Dog maxDog (Dog dog1, Dog dog2){
        if (dog1.weight > dog2.weight){
            return dog1;
        }
        return dog2;
    }
}
