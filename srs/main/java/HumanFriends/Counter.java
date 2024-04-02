package HumanFriends;

public class Counter {
    
    private static int animalsCount;
    
    public static void increaseAnimalsCount() {
        animalsCount++;
    }

    public static int getAnimalsCount() {
        return animalsCount;
    }
}
