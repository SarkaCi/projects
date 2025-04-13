import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

// hlavní třída aplikace pro správu pojištěnců
public class InsuranceApp {
    //private final proměnná pro Scanner, který slouží k získávání vstupu od uživatele
    private final Scanner scanner;
    //private final proměnná pro registry (evidence pojištěnců)
    private final InsuranceRegistry registry;

    // konstruktor třídy, který přijímá parametry
    public InsuranceApp(Scanner scanner) {
        this.scanner = scanner;
        this.registry = new InsuranceRegistry(scanner);
    }

    // metoda, která spustí hlavní běh aplikace
    public void run() {
        // vypíše název aplikace do konzole v ASCII grafice
        System.out.println("╔═══════════════════════════════╗");
        System.out.println("║ ░▒▓  EVIDENCE POJIŠTĚNÍ  ▓▒░  ║");
        System.out.println("╚═══════════════════════════════╝");

        // načtení existujících dat pojištěnců ze souboru
        registry.loadFromFile();

        // nekonečná smyčka pro hlavní menu aplikace
        while (true) {
            // zobrazení možností hlavního menu
            System.out.println("\nVyberte akci:");
            System.out.println("------------------------------------");
            System.out.println("1️⃣ Přidat pojištěného");
            System.out.println("2️⃣ Zobrazit seznam pojištěných");
            System.out.println("3️⃣ Vyhledat pojištěného");
            System.out.println("4️⃣ Odstranit pojištěného");
            System.out.println("5️⃣ Editovat pojištěného");
            System.out.println("6️⃣ Uložit a ukončit");
            System.out.println("7️⃣ Ukončit");
            System.out.println("------------------------------------");

            // načtení volby uživatele, kontrola vstupu (musí být číslo mezi 1 a 7)
            int choice = ValidationUtils.getValidNumber("Zadejte číslo a stiskněte ENTER: ", 1, 7);

            // zpracování volby pomocí switch-case
            switch (choice) {
                case 1 -> addNewPerson(); // přidání nového pojištěnce
                case 2 -> displayAllPeople(); // zobrazení seznamu všech pojištěnců
                case 3 -> searchPerson(); // vyhledání pojištěnce
                case 4 -> removePerson(); // odstranění pojištěnce
                case 5 -> editPerson(); // úprava údajů pojištěnce
                case 6 -> {
                    registry.saveToFile(); // uloží data do souboru
                    System.exit(0);  // ukončí aplikaci
                }
                case 7 -> System.exit(0); // ukončí aplikaci bez uložení
                default -> ConsoleUtils.printBoxedMessage("Neplatná volba."); // ošetření neplatné volby
            }
        }
    }

    // metoda pro přidání nového pojištěnce
    private void addNewPerson() {
        // načtení jména od uživatele a jeho formátování (první písmeno velké, ostatní malá)
        String firstName = ValidationUtils.formatName(ValidationUtils.getValidInput("Zadejte jméno a ENTER: "));
        // načtení příjmení od uživatele a jeho formátování (první písmeno velké, ostatní malá)
        String lastName = ValidationUtils.formatName(ValidationUtils.getValidInput("Zadejte příjmení a ENTER: "));
        // deklarace proměnné pro datum narození, zatím bez hodnoty
        LocalDate birthDate = ValidationUtils.getValidDate("Zadejte datum narození (dd.MM.yyyy): ");
        // deklarace proměnné pro telefonní číslo
        String phoneNumber = ValidationUtils.getValidPhoneNumber("Zadejte telefonní číslo (+420123456789): ");

        // výpočet věku pojištěnce na základě zadaného data narození a aktuálního data
        int age = Period.between(birthDate, LocalDate.now()).getYears();

        // vytvoření nového objektu pojištěnce a jeho přidání do registru
        registry.addPerson(new InsuredPerson(firstName, lastName, birthDate, phoneNumber));
    }

    // metoda pro vyhledání pojištěnce podle zvoleného kritéria
    private void searchPerson() {
        // získá od uživatele kritérium, podle kterého chce hledat (např. jméno, příjmení, telefonní číslo)
        String choice = askForSearchCriteria();
        //volá metodu `searchPerson()` v `InsuranceRegistry`, která provede samotné hledání
        registry.searchPerson(choice);
    }

    // metoda pro odstranění pojištěnce podle zvoleného kritéria
    private void removePerson() {
        // získá od uživatele kritérium, podle kterého chce pojištěnce smazat
        String choice = askForSearchCriteria();
        // volá metodu `removePerson()` v `InsuranceRegistry`, která vymaže odpovídajícího pojištěnce
        registry.removePerson(choice);
    }

    // metoda pro úpravu údajů pojištěnce podle zvoleného kritéria
    private void editPerson() {
        // získá od uživatele kritérium, podle kterého chce pojištěnce upravit
        String choice = askForSearchCriteria();
        // volá metodu `editPerson()` v `InsuranceRegistry`, která provede změny
        registry.editPerson(choice);
    }

    // metoda pro zobrazení seznamu všech pojištěnců
    private void displayAllPeople() {
        // načtení seznamu všech pojištěnců z registru
        List<InsuredPerson> people = registry.getAllPeople();
        // pokud je seznam pojištěnců prázdný, zobrazí se informační zpráva
        if (people.isEmpty()) {
            ConsoleUtils.printBoxedMessage("Seznam pojištěných je prázdný.");
        } else {
            // vypíše hlavičku tabulky s názvy sloupců
            System.out.println("=================================================================================");
            System.out.printf("%-15s %-15s %-12s %-6s %-2s%n", "Jméno", "Příjmení", "Datum nar.", "Věk", "Telefon");
            System.out.println("---------------------------------------------------------------------------------");
            // prochází seznam pojištěnců a vypisuje jejich údaje do tabulky
            for (InsuredPerson person : people) {
                System.out.printf("%-15s %-15s %-12s %-6d %-2s%n",
                        person.getFirstName(), person.getLastName(),
                        // formátování data narození do čitelného formátu "dd.MM.yyyy"
                        person.getBirthDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        person.getAge(), person.getPhoneNumber());
            }
            // vypíše oddělovač tabulky na konec seznamu
            System.out.println("---------------------------------------------------------------------------------");
        }
    }

    // metoda pro zobrazení možností vyhledávání a získání volby uživatele
    private String askForSearchCriteria() {
        System.out.println("------------------------------------");
        System.out.println("Podle jakého kritéria chcete hledat?");
        System.out.println("------------------------------------");
        System.out.println("1️⃣ Jméno");
        System.out.println("2️⃣ Příjmení");
        System.out.println("3️⃣ Rok narození");
        System.out.println("4️⃣ Telefonní číslo");
        System.out.println("------------------------------------");
        System.out.print("Zadejte číslo a stiskněte ENTER: ");
        // načtení volby uživatele jako řetězec a její vrácení
        return scanner.nextLine();
    }
}

