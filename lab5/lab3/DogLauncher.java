public class DogLauncher {
    public static void main(String[] args){
        Dog smallDog;
        new Dog(20);
        smallDog = new Dog(20);
        smallDog.makeNoise();
        Dog huge = new Dog(59);
        huge.makeNoise();

        Dog[] dogs = new Dog[2];
        dogs[0] = smallDog;
        dogs[1] = huge;
        dogs[0].makeNoise();

        Dog biggerdog = Dog.maxDog(smallDog, huge);
        System.out.println(biggerdog.weight);
        new Dog(20).makeNoise();

        System.out.println(Dog.binomen);
        System.out.println(huge.binomen);
    }
}
