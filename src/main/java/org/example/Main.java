package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<Integer, String> skrotWaluty = Map.of(
            1, "USD",
            2, "EUR",
            3, "PLN",
            4, "CHF",
            5, "GBP"
    );
    private static final Scanner sc = new Scanner(System.in);
    private static final LatestDataRequestSender latestDataRequestSender = new LatestDataRequestSender();

    public static void main(String[] args) {
        boolean running = true;
        do {
            System.out.println("Policz ile w tym momencie jest warta twoja waluta!");

            System.out.println("Jaką walutę wymieniasz? \nWybór zatwierdź odpowiednią cyfrą.");
            int daje = wybierzWalute();
            String dajeWalute = skrotWaluty.get(daje);

            System.out.println("Jaką walutę chcesz otrzymać? \nWybór zatwierdź odpowiednią cyfrą.");
            int dostaje = wybierzWalute();
            String dostajeWalute = skrotWaluty.get(dostaje);

            System.out.println("Jaką ilość waluty " + dajeWalute + " chesz wymienić?");
            double ilosc = sc.nextDouble();

            Double cena;
            try {
                cena = getLatestPrice(dajeWalute, dostajeWalute);
            } catch (Exception e) {
                System.err.println("Błąd przy fetchowaniu ceny");
                throw new RuntimeException(e);
            }

            DecimalFormat f = new DecimalFormat("00.00");
            System.out.println(f.format(ilosc)+ dajeWalute + " = " + f.format(ilosc * cena) + dostajeWalute);

            System.out.println("Czy chcesz wymienić inną walutę?");
            System.out.println("1:Tak \t inny Integer:Nie");
            if (sc.nextInt() != 1) {
                running = false;
            }
        } while(running);
        System.out.println("Dziękujemy za skorzystanie z kantoru!");
    }

    private static int wybierzWalute() {
        int input;
        do {
            System.out.println("1:USD \t\t 2:EUR \t\t 3:PLN \t\t 4:CHF \t\t 5:GBP");
            System.out.println("(Dolar) \t (Euro) \t (Złotówka)  (Frank) \t (Funt)");
            input = sc.nextInt();
            if (input < 1 || input > 5) {
                System.out.println("Wprowadź wartość która występuje w programie!");
            }
        } while (input < 1 || input > 5);
        return input;
    }

    private static Double getLatestPrice(String dajeWalute, String dostajeWalute) throws IOException, URISyntaxException, InterruptedException {
        var latestData = latestDataRequestSender.getLatestData(dajeWalute, dostajeWalute);
        return latestData.getRates().get(dostajeWalute);
    }
}