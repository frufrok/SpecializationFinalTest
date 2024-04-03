package Register;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

import HumanFriends.Animal;
import HumanFriends.Command;

public class Printer {
    public static ArrayList<String> getAnimalsTable(Collection<Animal> animals, SortType sortType) {
        ArrayList<Animal> sortedAnimals;
        if (sortType.equals(SortType.KIND)) {
            sortedAnimals = new ArrayList<>(
                    animals.stream()
                        .sorted(Comparator.comparing(Animal::getID))
                        .sorted(Comparator.comparing(Animal::getKind)).toList());
        } else if (sortType.equals(SortType.BIRTHDAY)) {
            sortedAnimals = new ArrayList<>(
                    animals.stream()
                        .sorted(Comparator.comparing(Animal::getID))
                        .sorted(Comparator.comparingInt(
                            animal -> animal.getBirthDate().getYear() * 365 + animal.getBirthDate().getDayOfYear()))
                            .toList());
        } else if (sortType.equals(SortType.NAME)) {
            sortedAnimals = new ArrayList<>(
                    animals.stream()
                            .sorted(Comparator.comparing(Animal::getID))
                            .sorted(Comparator.comparing(Animal::getName)).toList());
        } else if (sortType.equals(SortType.SUBTYPE)) {
            sortedAnimals = new ArrayList<>(
                    animals.stream()
                            .sorted(Comparator.comparing(Animal::getID))
                            .sorted(Comparator.comparing(Animal::getSubType)).toList());
        } else {
            sortedAnimals = new ArrayList<>(
                    animals.stream()
                            .sorted(Comparator.comparing(Animal::getID))
                            .sorted(Comparator.comparing(Animal::getID)).toList());
        }
        ArrayList<AnimalColumn> template = getAnimalTemplate();
        ArrayList<String> result = new ArrayList<>();
        
        StringBuilder headers = new StringBuilder("|");
        StringBuilder delimiters = new StringBuilder("|");
        for (AnimalColumn col : template) {
            headers.append(maxLengthString(col.header, col.width));
            headers.append("|");
            delimiters.append("-".repeat(col.width));
            delimiters.append("|");
        }
        result.add(headers.toString());
        result.add(delimiters.toString());

        for (Animal animal : sortedAnimals) {
            StringBuilder animalString = new StringBuilder("|");
            for (AnimalColumn col : template) {
                animalString.append(maxLengthString(col.keyExtractor.apply(animal), col.width));
                animalString.append("|");
            }
            result.add(animalString.toString());
        }
        return result;
    }

    private static ArrayList<AnimalColumn> getAnimalTemplate() {
        ArrayList<AnimalColumn> result = new ArrayList<>();
        result.add(new AnimalColumn("ID", 4, x -> String.format("%d", x.getID())));
        result.add(new AnimalColumn("Name", 15, Animal::getName));
        result.add(new AnimalColumn("Kind", 15, Animal::getKind));
        result.add(new AnimalColumn("Subtype", 15, Animal::getSubType));
        result.add(new AnimalColumn("Birthday", 10, pet -> pet.getBirthDate().toString()));
        result.add(new AnimalColumn("Commands", 50,
                animal -> animal.getCommands().stream().map(Command::getName).collect(Collectors.joining(", "))));
        return result;
    }
    
    private static class AnimalColumn {
        private String header;
        private int width;
        private Function<Animal, String> keyExtractor;

        private AnimalColumn(String header, int width, Function<Animal, String> keyExtractor) {
            this.header = header;
            this.width = width;
            this.keyExtractor = keyExtractor;
        }
    }

    private static String maxLengthString(String text, int maxLength) {
        return text.length() > maxLength ? text.substring(0, maxLength) : text + " ".repeat(maxLength - text.length());
    }
}