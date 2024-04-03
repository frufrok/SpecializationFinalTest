package PetRegister;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;
import HumanFriends.Command;
import HumanFriends.Pet;

public class Printer {
    public static ArrayList<String> getPetsTable(Collection<Pet> pets, SortType sortType) {
        ArrayList<Pet> sortedPets;
        if (sortType.equals(SortType.KIND)) {
            sortedPets = new ArrayList<>(
                    pets.stream()
                        .sorted(Comparator.comparing(Pet::getID))
                        .sorted(Comparator.comparing(Pet::getKind)).toList());
        } else if (sortType.equals(SortType.BIRTHDAY)) {
            sortedPets = new ArrayList<>(
                    pets.stream()
                        .sorted(Comparator.comparing(Pet::getID))
                        .sorted(Comparator.comparingInt(
                            pet -> pet.getBirthDate().getYear() * 365 + pet.getBirthDate().getDayOfYear()))
                            .toList());
        } else if (sortType.equals(SortType.NAME)) {
            sortedPets = new ArrayList<>(
                    pets.stream()
                            .sorted(Comparator.comparing(Pet::getID))
                            .sorted(Comparator.comparing(Pet::getName)).toList());
        } else {
            sortedPets = new ArrayList<>(
                    pets.stream()
                            .sorted(Comparator.comparing(Pet::getID))
                            .sorted(Comparator.comparing(Pet::getID)).toList());
        }
        ArrayList<PetColumn> template = getPetTemplate();
        ArrayList<String> result = new ArrayList<>();
        
        StringBuilder headers = new StringBuilder("|");
        StringBuilder delimiters = new StringBuilder("|");
        for (PetColumn col : template) {
            headers.append(maxLengthString(col.header, col.width));
            headers.append("|");
            delimiters.append("-".repeat(col.width));
            delimiters.append("|");
        }
        result.add(headers.toString());
        result.add(delimiters.toString());

        for (Pet pet : sortedPets) {
            StringBuilder petString = new StringBuilder("|");
            for (PetColumn col : template) {
                petString.append(maxLengthString(col.keyExtractor.apply(pet), col.width));
                petString.append("|");
            }
            result.add(petString.toString());
        }
        return result;
    }

    private static ArrayList<PetColumn> getPetTemplate() {
        ArrayList<PetColumn> result = new ArrayList<>();
        result.add(new PetColumn("ID", 4, pet -> String.format("%d", pet.getID())));
        result.add(new PetColumn("Name", 15, Pet::getName));
        result.add(new PetColumn("Kind", 15, Pet::getKind));
        result.add(new PetColumn("Birthday", 10, pet -> pet.getBirthDate().toString()));
        result.add(new PetColumn("Commands", 50,
                pet -> pet.getCommands().stream().map(Command::getName).collect(Collectors.joining(", "))));
        return result;
    }
    
    private static class PetColumn {
        private String header;
        private int width;
        private Function<Pet, String> keyExtractor;

        private PetColumn(String header, int width, Function<Pet, String> keyExtractor) {
            this.header = header;
            this.width = width;
            this.keyExtractor = keyExtractor;
        }
    }

    private static String maxLengthString(String text, int maxLength) {
        return text.length() > maxLength ? text.substring(0, maxLength - 1) : text + " ".repeat(maxLength - text.length());
    }
}