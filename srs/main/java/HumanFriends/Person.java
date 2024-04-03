package HumanFriends;

public class Person {
    private static int maxID = 0;

    private int id;

    public int getID() {
        return this.id;
    }

    private String name;

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Person(String name) {
        maxID++;
        this.name = name;
        this.id = maxID;
    }

    @Override
    public String toString() {
        return String.format("Person %s", this.name);
    }
}
