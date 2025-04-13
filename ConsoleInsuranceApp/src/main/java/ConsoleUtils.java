
public class ConsoleUtils {
    // metoda pro zobrazení textové zprávy uvnitř orámovaného boxu
    public static void printBoxedMessage(String text) {
        // odstraní bílé znaky na začátku a konci textu
        text = text.trim();
        // počet mezer kolem zprávy uvnitř boxu
        int padding = 4;
        // celková šířka rámečku (délka textu + mezery na obou stranách)
        int width = text.length() + padding * 2;

        // vytvoření horního rámečku
        System.out.print("╔");
        for (int i = 0; i < width; i++) System.out.print("═");
        System.out.println("╗");

        // prázdný řádek mezi horní hranou a textem
        System.out.print("║");
        for (int i = 0; i < width; i++) System.out.print(" ");
        System.out.println("║");

        // výpis samotného textu se zarovnáním uprostřed rámečku
        for (int i = 0; i < padding; i++) System.out.print(" ");
        System.out.print(text);
        for (int i = 0; i < padding; i++) System.out.print(" ");
        System.out.println();

        // další prázdný řádek mezi textem a dolní hranou
        System.out.print("║");
        for (int i = 0; i < width; i++) System.out.print(" ");
        System.out.println("║");

        // vytvoření dolního rámečku
        System.out.print("╚");
        for (int i = 0; i < width; i++) System.out.print("═");
        System.out.println("╝");
    }
}
