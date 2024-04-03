package HumanFriends;

public class Command {
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Command(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Command %s", this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return command.name.equals(this.name);
    }
}
