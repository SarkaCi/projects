import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ValidationUtils {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Ověří, že vstup není prázdný. Pokud je, nutí uživatele zadat hodnotu znovu.
     *
     * @param prompt Zpráva pro uživatele.
     * @return Validní textový vstup.
     */
    public static String getValidInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                ConsoleUtils.printBoxedMessage("❌ Tento údaj je povinný. Zkuste to znovu.");
            }
        } while (input.isEmpty());
        return input;
    }

    /**
     * Ověří, že uživatel zadal číslo v daném rozsahu. Pokud ne, opakuje dotaz.
     *
     * @param prompt Zpráva pro uživatele.
     * @param min Minimální povolená hodnota.
     * @param max Maximální povolená hodnota.
     * @return Platné číslo v zadaném rozsahu.
     */
    public static int getValidNumber(String prompt, int min, int max) {
        int number;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                number = Integer.parseInt(input);
                if (number >= min && number <= max) {
                    return number;
                }
                ConsoleUtils.printBoxedMessage("❌ Neplatná volba. Zadejte číslo mezi " + min + " a " + max + ".");
            } catch (NumberFormatException e) {
                ConsoleUtils.printBoxedMessage("❌ Neplatný vstup! Zadejte číslo.");
            }
        }
    }

    /**
     * Ověří a naformátuje jméno tak, aby začínalo velkým písmenem.
     *
     * @param name Jméno k formátování.
     * @return Formátované jméno (první písmeno velké, ostatní malá).
     */
    public static String formatName(String name) {
        if (name.isEmpty()) return name;
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    /**
     * Ověří, že uživatel zadal platné datum ve formátu dd.MM.yyyy.
     *
     * @param prompt Výzva pro uživatele.
     * @return Platné datum jako LocalDate.
     */
    public static LocalDate getValidDate(String prompt) {
        LocalDate birthDate = null;
        while (birthDate == null) {
            System.out.print(prompt);
            String birthDateStr = scanner.nextLine().trim();
            try {
                birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (DateTimeParseException e) {
                ConsoleUtils.printBoxedMessage("❌ Neplatný formát data. Zkuste to znovu.");
            }
        }
        return birthDate;
    }

    /**
     * Ověří, že uživatel zadal platné telefonní číslo.
     *
     * @param prompt Výzva pro uživatele.
     * @return Platné telefonní číslo.
     */
    public static String getValidPhoneNumber(String prompt) {
        String phoneNumber;
        while (true) {
            System.out.print(prompt);
            phoneNumber = scanner.nextLine().trim();
            if (phoneNumber.matches("\\+?[0-9]{9,15}")) {
                return phoneNumber;
            }
            ConsoleUtils.printBoxedMessage("❌ Neplatné telefonní číslo! Zkuste to znovu.");
        }
    }
}

