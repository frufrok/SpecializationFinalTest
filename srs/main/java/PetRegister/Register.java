package PetRegister;

import HumanFriends.Command;
import HumanFriends.Pet;

import java.time.LocalDate;
import java.util.HashSet;



public class Register {
    private HashSet<Pet> register = new HashSet<>();

    public void addPet(Pet pet) {
        this.register.add(pet);
    }

    public HashSet<Pet> getPets() {
        return this.register;
    }


    public static Register getSample() {
        Register register = new Register();
        Pet fido = new Pet("Fido",     "Dog",      LocalDate.of(2020, 1,   1));
        Pet whiskers = new Pet("Whiskers", "Cat",      LocalDate.of(2019, 5,   15));
        Pet hammy = new Pet("Hammy",    "Hamster",  LocalDate.of(2021, 3,   10));
        Pet buddy = new Pet("Buddy",    "Dog",      LocalDate.of(2018, 12,  10));
        Pet smudge = new Pet("Smudge",   "Cat",      LocalDate.of(2020, 2,   20));
        Pet peanut = new Pet("Peanut",   "Hamster",  LocalDate.of(2021, 8,   1));
        Pet bella = new Pet("Bella",    "Dog",      LocalDate.of(2019, 11,  11));
        Pet oliver = new Pet("Oliver", "Cat", LocalDate.of(2020, 6, 30));
        
        Command sit = new Command("sit");
        Command stay = new Command("stay");
        Command roll = new Command("roll");
        Command meow = new Command("meow");

        fido.addCommand(sit);
        fido.addCommand(stay);
        whiskers.addCommand(sit);
        hammy.addCommand(roll);
        buddy.addCommand(sit);
        smudge.addCommand(sit);
        smudge.addCommand(meow);
        peanut.addCommand(sit);
        peanut.addCommand(roll);
        bella.addCommand(stay);

        register.addPet(fido);
        register.addPet(whiskers);
        register.addPet(hammy);
        register.addPet(buddy);
        register.addPet(smudge);
        register.addPet(peanut);
        register.addPet(bella);
        register.addPet(oliver);

        return register;
    }
    
}
