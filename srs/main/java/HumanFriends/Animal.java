package HumanFriends;

import java.time.LocalDate;
import java.util.HashSet;

public class Animal {

    private int id;

    public int getID() {
        return this.id;
    }

    protected String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected String kind;

    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    protected LocalDate birthDate;

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(LocalDate date) {
        this.birthDate = date;
    }

    protected Person owner;

    public Person getOwner() {
        return this.owner;
    }
    
    public void SetOwner(Person person) {
        this.owner = person;
    }

    protected boolean isSleeping;

    public boolean getIsSleeping() {
        return this.isSleeping;
    }

    public void sleep() {
        this.isSleeping = true;
    }

    public void wakeUp() {
        this.isSleeping = false;
    }

    protected boolean isIll;

    public boolean getIsIll() {
        return this.isIll;
    }

    public void setIll() {
        this.isIll = true;
    }

    public void setWell() {
        this.isIll = false;
    }

    private HashSet<Command> commands = new HashSet<>();

    public HashSet<Command> getCommands() {
        return this.commands;
    }

    public void addCommand(Command command) {
        this.commands.add(command);
    }

    public void forgetCommand(Command command) {
        this.commands.remove(command);
    }

    public Animal(String name, String kind, LocalDate birthDate) {
        this.name = name;
        this.kind = kind;
        this.birthDate = birthDate;
        Counter.increaseAnimalsCount();
        this.id = Counter.getAnimalsCount();
    }

    public void doCommand(Command command) {
        if (this.commands.contains(command)) {
            System.out.println(this.toString() + String.format(" done %s", command));
        }
        else {
            System.out.println(this.toString() + String.format(" can't do %s", command));
        }
    }
    
    @Override
    public String toString() {
        return String.format("Animal %s the %s", this.name, this.kind);
    }
}


