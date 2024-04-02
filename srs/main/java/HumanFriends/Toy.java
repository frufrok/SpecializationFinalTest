package HumanFriends;

public class Toy {
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Toy %s", this.name);
    }
}
