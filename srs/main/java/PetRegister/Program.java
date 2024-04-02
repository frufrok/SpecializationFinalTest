package PetRegister;

import java.time.LocalDate;
import java.util.Scanner;

import HumanFriends.Pet;

public class Program {
    public static void main(String[] args) {
        pf("Реестр домашних животных. Версия %s", version);
        Register register = new Register();
        register.addPet(new Pet("Fido",     "Dog",      LocalDate.of(2020, 1,   1)));
        register.addPet(new Pet("Whiskers", "Cat",      LocalDate.of(2019, 5,   15)));
        register.addPet(new Pet("Hammy",    "Hamster",  LocalDate.of(2021, 3,   10)));
        register.addPet(new Pet("Buddy",    "Dog",      LocalDate.of(2018, 12,  10)));
        register.addPet(new Pet("Smudge",   "Cat",      LocalDate.of(2020, 2,   20)));
        register.addPet(new Pet("Peanut",   "Hamster",  LocalDate.of(2021, 8,   1)));
        register.addPet(new Pet("Bella",    "Dog",      LocalDate.of(2019, 11,  11)));
        register.addPet(new Pet("Oliver",   "Cat",      LocalDate.of(2020, 6,   30)));
        String command;
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                command = readString("Введите команду или 'h' для получения справки:", in);
                if (command.equals("h")) {
                    pl("Список доступных команд:");
                    ph("h", "справка");
                    ph("a", "добавить питомца в реестр");
                    ph("l", "показать список питомцев");
                    ph("e", "выход");
                    pl("");
                }
                if (command.equals("e")) {
                    pl("До свидания!");
                    break;
                }
                if (command.equals("a")) {
                    addPet(register, in);
                }
                if (command.equals("l")) {
                    showPets(register, in);
                }
            }
        } catch (Exception e) {
            pf("Исключение: %s", e.getMessage());
            pf("Работа программы будет завершена.");
        }
    }

    private static void addPet(Register register, Scanner in) {
        pl("Добавить питомца:");
        String name = readString("Укажите имя:", in);
        String kind = readString("Укажите биологический вид:", in);
        String birthDate = readString("Укажите дату рождения в формате ГГГГ.ММ.ДД", in);
        try {
            Pet pet = PetParser.parsePet(name, kind, birthDate);
            register.addPet(pet);
        }
        catch (Exception e) {
            pl("Ошибка добавления питомца:");
            pl(e.getMessage());
        }
    }
    
    private static void showPets(Register register, Scanner in) {
        pl("Показать список питомцев:");
        while (true) {
            String command = readString("Укажите тип сортировки или введите 'h' для справки", in);
            if (command.equals("h")) {
                pl("Список параметров сортировки:");
                ph("h", "справка");
                ph("n", "сортировка по имени (по умолчанию)");
                ph("k", "сортировка по виду");
                ph("b", "сортировка по дате рождения");
                ph("e", "отмена операции");
            }
            if (command.isBlank() || command.equals("n")) {
                Printer.print(register, SortType.NAME);
                break;
            }
            if (command.equals("k")) {
                Printer.print(register, SortType.KIND);
                break;
            }
            if (command.equals("b")) {
                Printer.print(register, SortType.BIRTHDAY);
                break;
            }
            if (command.equals("e")) {
                break;
            }
        }
    }

    private static void pl(String message) {
        System.out.println(message);
    }

    private static void pf(String message, Object... args) {
        pl(String.format(message, args));
    }
    
    private static void ph(String argument, String function) {
        pf("\t%s - %s", argument, function);
    }

    private static String readString(String message, Scanner in) {
        if (!message.isBlank()) {
            pl(message);
        }
        String result;
        
        try {
            result = in.nextLine();
        }
        catch(Exception ignored) {
            result = "";
        }
        pl("");
        return result;
    }

    public static String version = "1.0";
}
