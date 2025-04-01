import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;


public class Currencycontrol {
    public static void main(String[] args) {
        HashMap<Integer, String> currencyCodes = new HashMap<>();

        System.out.println("=======================");
        System.out.println("Welcome to Currency Converter");
        System.out.println("=======================");

        currencyCodes.put(1, "USD");
        currencyCodes.put(2, "CAD");
        currencyCodes.put(3, "EUR");
        currencyCodes.put(4, "HKD");
        currencyCodes.put(5, "INR");
        currencyCodes.put(6, "IRR");
        currencyCodes.put(7, "AED");
        currencyCodes.put(8, "JPY");
        currencyCodes.put(9, "GBP");
        currencyCodes.put(10, "AUD");
        currencyCodes.put(11, "CHF");
        currencyCodes.put(12, "CNY");
        currencyCodes.put(13, "SGD");
        currencyCodes.put(14, "NZD");
        currencyCodes.put(15, "BRL");
        currencyCodes.put(16, "RUB");
        currencyCodes.put(17, "ZAR");
        currencyCodes.put(18, "SEK");
        currencyCodes.put(19, "NOK");
        currencyCodes.put(20, "KRW");
        currencyCodes.put(21, "MXN");
        currencyCodes.put(22, "THB");
        currencyCodes.put(23, "IDR");
        currencyCodes.put(24, "TRY");
        currencyCodes.put(25, "PLN");
        currencyCodes.put(26, "HUF");
        currencyCodes.put(27, "CZK");
        currencyCodes.put(28, "DKK");
        currencyCodes.put(29, "MYR");
        currencyCodes.put(30, "PHP");
        currencyCodes.put(31, "ILS");
        currencyCodes.put(32, "SAR");
        currencyCodes.put(33, "TWD");
        currencyCodes.put(34, "CLP");
        currencyCodes.put(35, "PKR");
        currencyCodes.put(36, "EGP");
        currencyCodes.put(37, "NGN");
        currencyCodes.put(38, "COP");
        currencyCodes.put(39, "BDT");
        currencyCodes.put(40, "VND");

        Scanner sc = new Scanner(System.in);
        try {
            String fromCode = getCurrencyCode(sc, "Currency Converting FROM?", currencyCodes);
            String toCode = getCurrencyCode(sc, "Currency Converting TO?", currencyCodes);
            double amount = getAmount(sc);

            convertCurrency(fromCode, toCode, amount);
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter valid values.");
        } finally {
            sc.close();
        }

        System.out.println("\nThank you for using Currency Converter!");
    }

    private static String getCurrencyCode(Scanner sc, String message, HashMap<Integer, String> currencyCodes) {
        while (true) {
            try {
                System.out.println(message);
                System.out.println("1 USD  2 CAD  3 EUR  4 HKD  5 INR  6 JPY  7 GBP  8 AUD  9 CHF  10 CNY");
                int choice = sc.nextInt();
                if (currencyCodes.containsKey(choice)) {
                    return currencyCodes.get(choice);
                } else {
                    System.out.println("Invalid selection. Please enter a valid option.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next();
            }
        }
    }

    private static double getAmount(Scanner sc) {
        while (true) {
            try {
                System.out.println("Enter the Amount to Convert:");
                return sc.nextDouble();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid amount.");
                sc.next();
            }
        }
    }

    private static void convertCurrency(String fromCode, String toCode, double amount) {
        DecimalFormat f = new DecimalFormat("0.00");

        try {
            // New API with accurate rates
            String API_URL = "https://api.exchangerate.host/latest?base=" + fromCode;
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject obj = new JSONObject(response.toString());
                double exchangeRate = obj.getJSONObject("rates").getDouble(toCode);
                double convertedAmount = amount * exchangeRate;

                System.out.println();
                System.out.printf("%s %s = %s %s\n", f.format(amount), fromCode, f.format(convertedAmount), toCode);
            } else {
                System.out.println("Failed to retrieve exchange rates.");
            }
        } catch (IOException e) {
            System.out.println("Error connecting to API: " + e.getMessage());
        }
    }
}
