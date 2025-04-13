
import java.util.Scanner;

//hlavní vstupní bod aplikace
public class Main {
    public static void main(String[] args) {
        //vytváří objekt Scanner, který slouží pro čtení vstupu od uživatele
        Scanner scanner = new Scanner(System.in);
        //vytváří novou instanci InsuranceApp a předává jí Scanner
        InsuranceApp app = new InsuranceApp(scanner);
        //spustí aplikaci voláním app.run()
        app.run();
    }
}

