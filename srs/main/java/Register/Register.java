package Register;

import HumanFriends.Animal;
import HumanFriends.Command;
import HumanFriends.PackAnimal;
import HumanFriends.Pet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;



public class Register {
    private HashSet<Pet> pets = new HashSet<>();
    private HashSet<PackAnimal> packs = new HashSet<>();

    public void addPet(Pet pet) {
        this.pets.add(pet);
    }

    public HashSet<Pet> getPets() {
        return this.pets;
    }

    public void addPack(PackAnimal pack) {
        this.packs.add(pack);
    }

    public HashSet<PackAnimal> getPacks() {
        return this.packs;
    }

    public HashSet<Animal> getAnimals() {
        HashSet<Animal> result = new HashSet<>();
        this.pets.forEach(pet -> result.add(pet));
        this.packs.forEach(pack -> result.add(pack));
        return result;
    }

    public HashSet<String> getKnownPets() {
        HashSet<String> result = new HashSet<>();
        this.pets.forEach(x -> result.add(x.getKind()));
        return result;
    }
    
    public HashSet<String> getKnownPacks() {
        HashSet<String> result = new HashSet<>();
        this.packs.forEach(x -> result.add(x.getKind()));
        return result;
    }

    public static Register getSample() {
        Register register = new Register();

        register.addPet(new Pet("Fido",     "Dog",      LocalDate.of(2020, 1,   1)));
        register.addPet(new Pet("Whiskers", "Cat",      LocalDate.of(2019, 5,   15)));
        register.addPet(new Pet("Hammy",    "Hamster",  LocalDate.of(2021, 3,   10)));
        register.addPet(new Pet("Buddy",    "Dog",      LocalDate.of(2018, 12,  10)));
        register.addPet(new Pet("Smudge",   "Cat",      LocalDate.of(2020, 2,   20)));
        register.addPet(new Pet("Peanut",   "Hamster",  LocalDate.of(2021, 8,   1)));
        register.addPet(new Pet("Bella",    "Dog",      LocalDate.of(2019, 11,  11)));
        register.addPet(new Pet("Oliver",   "Cat",      LocalDate.of(2020, 6,   30)));

        ArrayList<Command> petCommands = new ArrayList<>();
        petCommands.add(new Command("sit"));
        petCommands.add(new Command("stay"));
        petCommands.add(new Command("walk"));
        petCommands.add(new Command("lay"));
        petCommands.add(new Command("run"));

        int petsCount = register.getPets().size();
        int petCommandsCount = petCommands.size();
        for (int i = 0; i < 25; i++) {
            Random rnd = new Random();
            new ArrayList<Pet>(register.getPets())
                    .get(rnd.nextInt(0, petsCount))
                    .addCommand(petCommands.get(rnd.nextInt(0, petCommandsCount)));
        }
        
        register.addPack(new PackAnimal("Thunder", "Horse", LocalDate.of(2015, 7,   21), 1));
        register.addPack(new PackAnimal("Sandy", "Camel",   LocalDate.of(2016, 11,  3), 1));
        register.addPack(new PackAnimal("Eeyore", "Donkey", LocalDate.of(2017, 9,   18), 1));
        register.addPack(new PackAnimal("Storm", "Horse",   LocalDate.of(2014, 5,   5), 1));
        register.addPack(new PackAnimal("Dune", "Camel",    LocalDate.of(2018, 12,  12), 1));
        register.addPack(new PackAnimal("Burro", "Donkey",  LocalDate.of(2019, 1,   23), 1));
        register.addPack(new PackAnimal("Blaze", "Horse",   LocalDate.of(2016, 2,   29), 1));
        register.addPack(new PackAnimal("Sahara", "Camel", LocalDate.of(2015, 8, 14), 1));

        ArrayList<Command> packCommands = new ArrayList<>();
        packCommands.add(new Command("walk"));
        packCommands.add(new Command("run"));
        packCommands.add(new Command("carry load"));
        packCommands.add(new Command("transport"));

        int packsCount = register.getPacks().size();
        int packCommandsCount = packCommands.size();
        for (int i = 0; i < 25; i++) {
            Random rnd = new Random();
            new ArrayList<PackAnimal>(register.getPacks())
                    .get(rnd.nextInt(0, packsCount))
                    .addCommand(packCommands.get(rnd.nextInt(0, packCommandsCount)));
        }

        return register;
    }
    
}
