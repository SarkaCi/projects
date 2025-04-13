import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Třída pro správu registru pojištěnců
class InsuranceRegistry {
    // Seznam všech pojištěnců (interní úložiště v paměti)
    private List<InsuredPerson> insuredPeople = new ArrayList<>();
    // Název souboru pro uložení dat
    private final String FILE_NAME = "insured_data.txt";
    // Scanner pro čtení uživatelského vstupu
    private final Scanner scanner;

    // Konstruktor - přijímá Scanner pro práci s uživatelským vstupem
    public InsuranceRegistry(Scanner scanner) {
        this.scanner = scanner;
    }

    // Přidání nového pojištěnce s kontrolou duplicit
    public void addPerson(InsuredPerson person) {
        // Ověří, zda pojištěnec se stejnými údaji již neexistuje
        for (InsuredPerson p : insuredPeople) {
            if (p.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                    p.getLastName().equalsIgnoreCase(person.getLastName()) &&
                    p.getPhoneNumber().equals(person.getPhoneNumber())) {
                ConsoleUtils.printBoxedMessage("⚠️ Pojištěný s těmito údaji již existuje.");
                return; // Pokud existuje, nového pojištěnce nepřidáme
            }
        }
        // Přidání pojištěnce do seznamu
        insuredPeople.add(person);
        ConsoleUtils.printBoxedMessage("✅ Pojištěný úspěšně přidán ");
    }

    // Editace údajů pojištěnce
    public void editPerson(String choice) {
        System.out.print("Zadejte hledanou hodnotu pro editaci a stiskněte ENTER: ");
        String value = scanner.nextLine().trim();

        // Vyhledání pojištěnců podle zadaného kritéria
        List<InsuredPerson> matchingPeople = new ArrayList<>();
        for (InsuredPerson person : insuredPeople) {
            if ((choice.equals("1") && person.getFirstName().equalsIgnoreCase(value)) ||
                    (choice.equals("2") && person.getLastName().equalsIgnoreCase(value)) ||
                    (choice.equals("3") && String.valueOf(person.getBirthDate().getYear()).equals(value)) ||
                    (choice.equals("4") && person.getPhoneNumber().equals(value))) {
                matchingPeople.add(person);
            }
        }
        // Pokud žádný pojištěnec nebyl nalezen, zobrazí zprávu
        if (matchingPeople.isEmpty()) {
            ConsoleUtils.printBoxedMessage("❌ Pojištěný nenalezen.");
            return;
        }
        // Výpis nalezených pojištěnců
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("🔎 Nalezení pojištěnci:");
        System.out.println("------------------------------------");
        for (int i = 0; i < matchingPeople.size(); i++) {
            System.out.println((i + 1) + ". " + matchingPeople.get(i));
        }
        System.out.println("---------------------------------------------------------------------------------");


        System.out.print("Vyberte číslo osoby, kterou chcete upravit (nebo 0 pro zrušení): ");
        int index;
        try {
            index = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            ConsoleUtils.printBoxedMessage("❌ Neplatná volba.");
            return;
        }

        // Ověření, zda uživatel zvolil platnou osobu
        if (index == 0) {
            ConsoleUtils.printBoxedMessage("🚫 Úprava zrušena.");
            return;
        }

        if (index < 1 || index > matchingPeople.size()) {
            ConsoleUtils.printBoxedMessage("❌ Neplatná volba.");
            return;
        }

        InsuredPerson personToEdit = matchingPeople.get(index - 1);

        // Editace jmena
        System.out.print("Zadejte nové jméno (Enter pro ponechání starého): ");
        String newFirstName = scanner.nextLine().trim();
        if (!newFirstName.isEmpty()) {
            personToEdit.setLastName(ValidationUtils.formatName(newFirstName));
        }

        // Editace příjmení
        System.out.print("Zadejte nové příjmení (Enter pro ponechání starého): ");
        String newLastName = scanner.nextLine().trim();
        if (!newLastName.isEmpty()) {
            personToEdit.setLastName(ValidationUtils.formatName(newLastName));
        }

        // Editace telefonního čísla s validací
        while (true) {
            System.out.print("Zadejte nové telefonní číslo (Enter pro ponechání starého): ");
            String newPhone = scanner.nextLine().trim();
            if (newPhone.isEmpty()) {
                break; // Ponechání starého čísla
            }
            if (newPhone.matches("\\+?[0-9]{9,15}")) {
                personToEdit.setPhoneNumber(newPhone);
                break;
            } else {
                ConsoleUtils.printBoxedMessage("❌ Neplatné telefonní číslo! Zkuste to znovu.");
            }
        }

        // Uložení změn
        saveToFile();
        ConsoleUtils.printBoxedMessage("✅ Pojištěnec byl upraven.");
    }

    // Metoda pro vyhledání pojištěnců podle zvoleného kritéria
    private List<InsuredPerson> findPeople(String choice) {
        // Výzva k zadání hledané hodnoty
        System.out.print("Zadejte hledanou hodnotu a stiskněte ENTER: ");
        // Načtení vstupu od uživatele, odstranění mezer a převedení na malá písmena pro lepší porovnání
        String value = scanner.nextLine().trim().toLowerCase();

        // Seznam nalezených pojištěnců
        List<InsuredPerson> matchingPeople = new ArrayList<>();
        // Procházíme všechny pojištěnce a hledáme shodu podle zvoleného kritéria
        for (InsuredPerson person : insuredPeople) {
            if ((choice.equals("1") && person.getFirstName().toLowerCase().contains(value)) ||
                    (choice.equals("2") && person.getLastName().toLowerCase().contains(value)) ||
                    (choice.equals("3") && String.valueOf(person.getBirthDate().getYear()).contains(value)) ||
                    (choice.equals("4") && person.getPhoneNumber().contains(value))) {
                matchingPeople.add(person); // Přidání nalezeného pojištěnce do seznamu
            }
        }
        // Vrácení seznamu odpovídajících pojištěnců
        return matchingPeople;
    }

    // Metoda pro vyhledání pojištěnců a jejich zobrazení
    public void searchPerson(String choice) {
        // Použití metody `findPeople()` pro nalezení odpovídajících pojištěnců
        List<InsuredPerson> matchingPeople = findPeople(choice);

        // Pokud nebyl nalezen žádný pojištěnec, zobrazí se zpráva a metoda se ukončí
        if (matchingPeople.isEmpty()) {
            ConsoleUtils.printBoxedMessage("❌ Pojištěný nenalezen.");
            return;
        }

        // Výpis seznamu nalezených pojištěnců
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("🔎 Nalezení pojištěnci:");
        System.out.println("------------------------------------");
        for (int i = 0; i < matchingPeople.size(); i++) {
            System.out.println((i + 1) + ". " + matchingPeople.get(i)); // Číslovaný seznam
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    // Metoda pro odstranění pojištěnce
    public void removePerson(String choice) {
        // Použití metody `findPeople()` pro nalezení odpovídajících pojištěnců
        List<InsuredPerson> matchingPeople = findPeople(choice);

        // Pokud nebyl nalezen žádný pojištěnec, zobrazí se zpráva a metoda se ukončí
        if (matchingPeople.isEmpty()) {
            ConsoleUtils.printBoxedMessage("❌ Pojištěný nenalezen.");
            return;
        }

        // Výpis seznamu nalezených pojištěnců
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("🔎 Nalezení pojištěnci:");
        System.out.println("------------------------------------");
        for (int i = 0; i < matchingPeople.size(); i++) {
            System.out.println((i + 1) + ". " + matchingPeople.get(i)); // Číslovaný seznam
        }
        System.out.println("---------------------------------------------------------------------------------");

        // Výzva k výběru pojištěnce k odstranění
        System.out.print("Zadejte číslo pojištěnce, kterého chcete odstranit (nebo 0 pro zrušení) a stiskněte ENTER: ");
        int index;
        try {
            // Převod vstupu na číslo
            index = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            ConsoleUtils.printBoxedMessage("❌ Neplatná volba.");
            return;
        }

        // Pokud uživatel zadal 0, odstranění se zruší
        if (index == 0) {
            ConsoleUtils.printBoxedMessage("🚫 Odstranění zrušeno.");
            return;
        }

        // Ověření, zda uživatel zadal platné číslo odpovídající nalezeným pojištěncům
        if (index < 1 || index > matchingPeople.size()) {
            ConsoleUtils.printBoxedMessage("❌ Neplatná volba.");
            return;
        }

        // Získání vybraného pojištěnce k odstranění
        InsuredPerson personToRemove = matchingPeople.get(index - 1);

        // Potvrzení odstranění
        System.out.print("⚠️ Opravdu chcete odstranit: " + personToRemove + "? (ano/ne): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("ano")) {
            insuredPeople.remove(personToRemove); // Odstranění pojištěnce
            saveToFile(); // Uložení změn
            ConsoleUtils.printBoxedMessage("✅ Pojištěný byl úspěšně odstraněn.");
        } else {
            ConsoleUtils.printBoxedMessage("🚫 Odstranění zrušeno.");
        }
    }

    // Uložení seznamu pojištěnců do souboru
    public void saveToFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // Přidání podpory pro LocalDate

        try {
            objectMapper.writeValue(new File(FILE_NAME), insuredPeople);
            ConsoleUtils.printBoxedMessage("✅ Data byla uložena do souboru ve formátu JSON.");
        } catch (IOException e) {
            ConsoleUtils.printBoxedMessage("❌ Chyba při ukládání: " + e.getMessage());
        }
    }

    // Načtení seznamu pojištěnců ze souboru
    public void loadFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // Přidání podpory pro LocalDate

        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                ConsoleUtils.printBoxedMessage("⚠️ Soubor nenalezen. Bude vytvořen nový.");
                return;
            }
            insuredPeople = objectMapper.readValue(file, new TypeReference<List<InsuredPerson>>() {
            });
            ConsoleUtils.printBoxedMessage("✅ Data byla načtena ze souboru ve formátu JSON.");
        } catch (IOException e) {
            ConsoleUtils.printBoxedMessage("❌ Chyba při načítání: " + e.getMessage());
        }
    }

    public List<InsuredPerson> getAllPeople() {
        return insuredPeople;
    }
}
