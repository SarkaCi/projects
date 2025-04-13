package cz.itsarka.homebudgetfromits.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExpenseDTO {
    private Long id;
    private String user;
    private int year;
    private int month;
    private int electricity;
    private int gas;
    private int rental;
    private int car;
    private int petrol;
    private int internet;
    private int lunch;
    private int shoppingInStore;
    private int expectedContributionSum;

    private String balanceStatus; // ✅ Připraveno pro zobrazení v HTML

    private List<ShoppingEntryDTO> shoppingEntries = new ArrayList<>();

    private List<ExpectedContributionEntryDTO> expectedContributions = new ArrayList<>();

    //Výpočet Celkového Součtu Výdajů
    // Sečte všechny výdajové kategorie a vrátí jejich celkovou hodnotu.
    public int getTotalSum() {
        return electricity + gas + rental + car + petrol + internet + lunch + shoppingInStore;
    }

    //Výpočet Celkové Výše Očekávaných Příspěvků
    //Sečte všechny očekávané příspěvky (getAmount() každého příspěvku).
    //Pokud je seznam expectedContributions prázdný nebo null, vrátí 0.
    public int getExpectedContributionSum() {
        if (expectedContributions == null) {
            return 0; // ✅ Pokud seznam neexistuje, vrátíme 0, aby aplikace nespadla
        }
        return expectedContributions.stream().mapToInt(ExpectedContributionEntryDTO::getAmount).sum();
    }

    //Výpočet Bilance (Zůstatku)
    // Vypočítá rozdíl mezi celkovými výdaji (getTotalSum()) a celkovými příspěvky (totalContributions).
    //Pokud jsou příspěvky vyšší než výdaje, bilance bude kladná.
    //Pokud jsou výdaje vyšší než příspěvky, bilance bude záporná.
    public int getBalance() {
        int totalContributions = expectedContributions.stream()
                .mapToInt(ExpectedContributionEntryDTO::getAmount)
                .sum();
        return getTotalSum() - totalContributions;
    }

    //Aktualizace Stavu Bilance
    public void updateBalanceStatus() {
        int balance = getBalance();
        if (balance > 0) {
            this.balanceStatus = "💰 Dostane zpět " + balance + " Kč"; // Změněná ikona
        } else if (balance < 0) {
            this.balanceStatus = "❌ Musí zaplatit " + Math.abs(balance) + " Kč";
        } else {
            this.balanceStatus = "✅ Vyrovnáno";
        }
    }

    //Filtrování Výdajových Kategorií
    public void filterCategories(List<String> categories) {
        if (!categories.contains("electricity")) {
            this.electricity = 0;
        }
        if (!categories.contains("gas")) {
            this.gas = 0;
        }
        if (!categories.contains("rental")) {
            this.rental = 0;
        }
        if (!categories.contains("car")) {
            this.car = 0;
        }
        if (!categories.contains("petrol")) {
            this.petrol = 0;
        }
        if (!categories.contains("internet")) {
            this.internet = 0;
        }
        if (!categories.contains("lunch")) {
            this.lunch = 0;
        }
        if (!categories.contains("shoppingInStore")) {
            this.shoppingInStore = 0;
        }
        if (!categories.contains("totalSum")) {
            // Pokud uživatel nezvolil "Celkem", nastavíme součet na 0
            this.balanceStatus = null;
        }
    }
}

/*
ExpenseDTO je DTO objekt, který se používá pro přenos dat mezi backendem a frontendem.
ExpenseDTO je serializován do JSON a poslán API (@RestController) do frontend aplikace.
   */


