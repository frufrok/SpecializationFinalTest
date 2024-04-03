package Register;

import java.time.LocalDate;

import HumanFriends.PackAnimal;
import HumanFriends.Pet;

public class AnimalParser {
    public static Pet parsePet(String name, String kind, String birthdate) throws Exception {
        checkStrings(name, kind);
        LocalDate date = parseDate(birthdate);
        return new Pet(name, kind, date);
    }

    public static PackAnimal parsePack(String name, String kind, String birthdate) throws Exception {
        checkStrings(name, kind);
        LocalDate date = parseDate(birthdate);
        return new PackAnimal(name, kind, date, 1);
    }
    
    private static void checkStrings(String name, String kind) throws Exception {
        if (name == null || name.isBlank()) {
            throw new Exception("Имя не может быть пустой строкой.");
        }
        if (kind == null || kind.isBlank()) {
            throw new Exception("Вид не может быть пустой строкой.");
        }
    }

    private static LocalDate parseDate(String date) throws Exception {
        String[] dateString = date.split("\\.");
        int[] dateArray = new int[3];
        if (dateString.length != 3) {
            throw new Exception("Дата рождения \"%s\" имеет неправильный формат.");
        }
        for (int i = 0; i < 3; i++) {
            dateArray[i] = Integer.parseInt(dateString[i]);
        }
        return LocalDate.of(dateArray[0], dateArray[1], dateArray[2]);
    }
}
