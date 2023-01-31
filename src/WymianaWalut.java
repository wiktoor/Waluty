import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.System.in;

public class WymianaWalut {

    public static void main(String[] args) throws IOException {

        Boolean running = true;

        do {
            HashMap<Integer, String> skrotWaluty = new HashMap<Integer, String>();

            //Dodajemy skróty walutowe np. "USD"
            skrotWaluty.put(1, "USD");
            skrotWaluty.put(2, "EUR");
            skrotWaluty.put(3, "PLN");
            skrotWaluty.put(4, "CHF");
            skrotWaluty.put(5, "GDP");

            Integer daje, dostaje;
            String dajeWalute, dostajeWalute;
            double ilosc;

            Scanner sc = new Scanner(System.in);

            System.out.println("Policz ile w tym momencie jest warta twoja waluta!");

            System.out.println("Jaką walutę wymieniasz? \nWybór zatwierdź odpowiednią cyfrą.");
            System.out.println("1:USD \t\t 2:EUR \t\t 3:PLN \t\t 4:CHF \t\t 5:GDP");
            System.out.println("(Dolar) \t (Euro) \t (Złotówka)  (Frank) \t (Funt)");
            daje = sc.nextInt();
            while (daje < 1 || daje > 5) {
                System.out.println("Wprowadź wartość która występuje w programie!");
                daje = sc.nextInt();
            }
            dajeWalute = skrotWaluty.get(daje);

            System.out.println("Jaką walutę chcesz otrzymać? \nWybór zatwierdź odpowiednią cyfrą.");
            System.out.println("1:USD \t\t 2:EUR \t\t 3:PLN \t\t 4:CHF \t\t 5:GDP");
            System.out.println("(Dolar) \t (Euro) \t (Złotówka)  (Frank) \t (Funt)");
            dostaje = sc.nextInt();
            while (dostaje < 1 || dostaje > 5) {
                System.out.println("Wprowadź wartość która występuje w programie!");
                daje = sc.nextInt();
            }
            dostajeWalute = skrotWaluty.get(daje);

            System.out.println("Jaką ilość waluty " + dajeWalute + " chesz wymienić?");
            ilosc = sc.nextFloat();

            sendHttpGETRequest(dajeWalute, dostajeWalute, ilosc);

            System.out.println("Czy chcesz wymienić inną walutę?");
            System.out.println("1:Tak \t inny Integer:Nie");
            if (sc.nextInt() != 1) {
                running = false;
            }
        }while(running);
        System.out.println("Dziękujemy za skorzystanie z kantoru!");
    }

    private static void sendHttpGETRequest(String dajeWalute, String dostajeWalute, double ilosc) throws IOException {

        DecimalFormat f = new DecimalFormat("00.00");

        String GET_URL = "https://api.exchangeratesapi.io/latest?base=" + dostajeWalute + "&symbols=" + dajeWalute;
        URL url = new URL(GET_URL);
        HttpURLConnection HttpURLConnection = (HttpURLConnection) url.openConnection();
        HttpURLConnection.setRequestMethod("GET");
        int responseCode = HttpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(HttpURLConnection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }in.close();

        JSONObject obj = new JSONObject(response.toString());
        Double exchangeRate = obj.getJSONObject("rates").getDouble(dajeWalute);
        System.out.println(obj.getJSONObject("rates"));
        System.out.println(exchangeRate);
        System.out.println();
        System.out.println(f.format(ilosc)+ dajeWalute + " = " + f.format(ilosc/exchangeRate) + dostajeWalute);


    }
        else{
            System.out.println("Nie udało się wypełnić zadania");
        }
    }

}