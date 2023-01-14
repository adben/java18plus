import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

public class HagueData {
    public static void main(String[] args) throws Exception {
        // Fetch data from API
        String apiUrl = "https://api.example.com/data/thehague";
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        // Parse data and store in variables
        String[] data = content.toString().split(",");
        int population = Integer.parseInt(data[0]);
        int area = Integer.parseInt(data[1]);
        int medianAge = Integer.parseInt(data[2]);
        int medianIncome = Integer.parseInt(data[3]);

        // Plot random statistics
        Random rand = new Random();
        int stat1 = rand.nextInt(population);
        int stat2 = rand.nextInt(area);
        int stat3 = rand.nextInt(medianAge);
        int stat4 = rand.nextInt(medianIncome);
        System.out.println("Random Statistics:");
        System.out.println("Population: " + stat1);
        System.out.println("Area: " + stat2);
        System.out.println("Median Age: " + stat3);
        System.out.println("Median Income: " + stat4);
    }
}
