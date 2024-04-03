package Register;

import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

import HumanFriends.Animal;
import HumanFriends.Command;
import HumanFriends.Counter;
import HumanFriends.PackAnimal;
import HumanFriends.Pet;

public class Program {
    public static void main(String[] args) {
        pf("Реестр домашних животных. Версия %s", version);
        Register register = Register.getSample();
        String command;
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                command = readString("Введите команду или 'h' для получения справки:", in);
                if (command.equals("h")) {
                    pl("Список доступных команд:");
                    ph("h", "справка");
                    ph("a", "добавить питомца в реестр");
                    ph("l", "показать список питомцев");
                    ph("m", "показать счетчик созданных животных");
                    ph("s", "выбрать питомца");
                    ph("e", "выход");
                    pl("");
                }
                if (command.equals("e")) {
                    pl("До свидания!");
                    break;
                }
                if (command.equals("a")) {
                    addAnimal(register, in);
                }
                if (command.equals("l")) {
                    showAnimals(register, in);
                }
                if (command.equals("m")) {
                    showCounter();
                }
                if (command.equals("s")) {
                    selectAnimal(register, in);
                }
            }
        } catch (Exception e) {
            pf("Исключение: %s", e.getMessage());
            pf("Работа программы будет завершена.");
        }
    }

    private static void showCounter() {
        pl("Счетчик созданных животных:");
        pf("%d", Counter.getAnimalsCount());
    }

    private static void addAnimal(Register register, Scanner in) {
        pl("Добавить питомца:");
        String name = readString("Укажите имя:", in);
        String kind = readString("Укажите биологический вид:", in);
        String birthDate = readString("Укажите дату рождения в формате ГГГГ.ММ.ДД", in);
        try {
            boolean isPack;
            if (register.getKnownPacks().contains(kind) && !register.getKnownPets().contains(kind)) {
                isPack = true;
            }
            else if (!register.getKnownPacks().contains(kind) && register.getKnownPets().contains(kind)) {
                isPack = false;
            }
            else if (register.getKnownPacks().contains(kind) && register.getKnownPets().contains(kind)) {
                pf("В нашем реестре есть как домашние, так и вьючные животные, вид которых %s", kind);
                while (true) {
                    String command = readString(
                            "Это вьючное животное? Введите 'y', если оно вьючное, или 'n', если оно домашнее:", in);
                    if (command.equals("y")) {
                        isPack = true;
                        break;
                    }
                    if (command.equals("n")) {
                        isPack = false;
                        break;
                    }
                }
            }
            else {
                pf("В реестре нет животных с типом %s", kind);
                while (true) {
                    String command = readString(
                            "Это вьючное животное? Введите 'y', если оно вьючное, или 'n', если оно домашнее:", in);
                    if (command.equals("y")) {
                        isPack = true;
                        break;
                    }
                    if (command.equals("n")) {
                        isPack = false;
                        break;
                    }
                }
            }

            if (isPack) {
                PackAnimal pack = AnimalParser.parsePack(name, kind, birthDate);
                register.addPack(pack);
            }
            else{
                Pet pet = AnimalParser.parsePet(name, kind, birthDate);
                register.addPet(pet);
            }
        }
        catch (Exception e) {
            pl("Ошибка добавления питомца:");
            pl(e.getMessage());
        }
    }
    
    private static void showAnimals(Register register, Scanner in) {
        pl("Показать список питомцев:");
        while (true) {
            String command = readString("Укажите тип сортировки или введите 'h' для справки", in);
            if (command.equals("h")) {
                pl("Список параметров сортировки:");
                ph("h", "справка");
                ph("i", "сортировка по ID (по умолчанию)");
                ph("n", "сортировка по имени");
                ph("k", "сортировка по виду");
                ph("t", "сортировка по подтипу (домашние или вьючные)");
                ph("b", "сортировка по дате рождения");
                ph("e", "отмена операции");
            }
            if (command.isBlank() || command.equals("i")) {
                pl("Список питомцев с сортировкой по ID:");
                pc(Printer.getAnimalsTable(register.getAnimals(), SortType.ID));
                break;
            }
            if (command.equals("n")) {
                pl("Список питомцев с сортировкой по имени:");
                pc(Printer.getAnimalsTable(register.getAnimals(), SortType.NAME));
                break;
            }
            if (command.equals("k")) {
                pl("Список питомцев с сортировкой по виду:");
                pc(Printer.getAnimalsTable(register.getAnimals(), SortType.KIND));
                break;
            }
            if (command.equals("t")) {
                pl("Список питомцев с сортировкой по подтипу:");
                pc(Printer.getAnimalsTable(register.getAnimals(), SortType.SUBTYPE));
                break;
            }
            if (command.equals("b")) {
                pl("Список питомцев с сортировкой по дате рождения:");
                pc(Printer.getAnimalsTable(register.getAnimals(), SortType.BIRTHDAY));
                break;
            }
            if (command.equals("e")) {
                pl("Отмена показа списка питомцев.");
                break;
            }
        }
    }

    private static void selectAnimal(Register register, Scanner in) {
        pl("Выбрать питомца:");
        while (true) {
            String command = readString("Введите ID питомца для выбора или 'e' для отмены:", in);
            try {
                int id = Integer.parseInt(command);
                HashMap<Integer, Animal> byID = new HashMap<>();
                register.getAnimals().forEach(x -> byID.put(x.getID(), x));
                if (byID.containsKey(id)) {
                    handlePet(byID.get(id), in);
                    break;
                } else {
                    throw new Exception(String.format("Питомца с ID=%d не существует.", id));
                }
            } catch (NumberFormatException e) {
                pl("Ошибка ввода: необходимо ввести целое число.");
            } catch (Exception e) {
                pf("Ошибка ввода: %s", e.getMessage());
            }
            if (command.equals("e")) {
                pl("Отмена показа выбора питомца.");
                break;
            }
        }
    }
    
    public static void handlePet(Animal animal, Scanner in) {
        while (true) {
            pf("Питомец с ID=%d: Имя %s, Вид %s, Подтип %s, Дата рождения %s",
                    animal.getID(), animal.getName(),
                    animal.getKind(), animal.getSubType(),
                    animal.getBirthDate());
            String command = readString("Введите команду или 'h' для получения справки:", in);
            if (command.equals("h")) {
                pl("Список параметров сортировки:");
                ph("h", "справка");
                ph("l", "показать список команд животного");
                ph("t", "обучить новой команде");
                ph("e", "выход в главное меню");
            }
            if (command.equals("l")) {
                pl("Список команд животного:");
                for (Command petCommand : animal.getCommands()) {
                    pl(petCommand.getName());
                }
            }
            if (command.equals("t")) {
                teachAnimal(animal, in);
            }
            if (command.equals("e")) {
                pl("Выход в главное меню.");
                break;
            }
        }
    }

    public static void teachAnimal(Animal animal, Scanner in) {
        while (true) {
            String commandName = readString("Введите команду для обучения:", in);
            if (commandName == null) {
                pl("Имя команды не может быть пустым.");
            }
            else {
                animal.addCommand(new Command(commandName));
                pf("Команда %s успешно добавлена.", commandName);
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

    private static void pc(Collection<String> collection) {
        for (String text : collection) {
            pl(text);
        }
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

    public static String version = "1.1";
}
