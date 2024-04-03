package PetRegister;

import java.time.LocalDate;

import HumanFriends.Pet;

public class PetParser {
    public static Pet parsePet(String name, String kind, String birthdate) throws Exception {
        if (name == null || name.isBlank()) {
            throw new Exception("Имя не может быть пустой строкой.");
        }
        if (kind == null || kind.isBlank()) {
            throw new Exception("Вид не может быть пустой строкой.");
        }
        String[] dateString = birthdate.split("\\.");
        int[] dateArray = new int[3];
        if (dateString.length != 3) {
            throw new Exception("Дата рождения \"%s\" имеет неправильный формат.");
        }
        for (int i = 0; i < 3; i++) {
            dateArray[i] = Integer.parseInt(dateString[i]); 
        }
        LocalDate date = LocalDate.of(dateArray[0], dateArray[1], dateArray[2]);
        return new Pet(name, kind, date);
    }
}
