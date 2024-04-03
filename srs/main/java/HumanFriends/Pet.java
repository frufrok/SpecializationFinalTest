package HumanFriends;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pet extends Animal {

    private final ArrayList<Toy> toys = new ArrayList<>();

    public ArrayList<Toy> getToys() {
        return this.toys;
    }

    public void addToy(Toy toy) {
        this.toys.add(toy);
    }

    public void takeToy(Toy toy) {
        this.toys.remove(toy);
    }

    public void play(Person person) {
        if (this.isSleeping) {
            System.out.println(this.toString() + "is sleeping and can't to play.");
        } else {
            if (this.isIll) {
                System.out.println(this.toString() + "is ill and can't to play.");
            } else {
                System.out.println(this.toString() + "playing with" + person.toString());
            }
        }
    }
    
    public Pet(String name, String kind, LocalDate birthDate) {
        super(name, kind, birthDate);
        this.subType = "Pet";
    }

    @Override
    public String toString() {
        return String.format("Pet %s the %s", this.name, this.kind);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pet pet = (Pet) o;
        boolean toysAreEqual = this.toys.size() == pet.toys.size();
        if (toysAreEqual) {
            for (int i = 0; i < this.toys.size(); i++) {
                toysAreEqual = toysAreEqual && this.toys.get(i).equals(pet.toys.get(i));
            }
        }
        return pet.name.equals(this.name) && pet.kind.equals(this.kind) && pet.birthDate.equals(this.birthDate) && toysAreEqual;
    }
}
