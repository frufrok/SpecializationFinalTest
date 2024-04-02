package PetRegister;

import HumanFriends.Pet;
import java.util.HashSet;



public class Register {
    private HashSet<Pet> register = new HashSet<>();

    public void addPet(Pet pet) {
        this.register.add(pet);
    }

    
    
}
