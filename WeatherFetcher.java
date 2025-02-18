import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherFetcher {
    private static final String API_KEY = "your_actual_api_key_here"; // Replace with a valid API key
    private static final String CITY = "Indore";
    private static final String URL_STRING = "https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&appid=" + API_KEY + "&units=metric";

    public static void main(String[] args) {
        try {
            String response = sendGetRequest(URL_STRING);
            parseAndDisplayWeather(response);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String sendGetRequest(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HTTP GET Request Failed with Error Code: " + responseCode);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    private static void parseAndDisplayWeather(String response) {
        JSONObject jsonObj = new JSONObject(response);
        String cityName = jsonObj.getString("name");
        JSONObject main = jsonObj.getJSONObject("main");
        double temperature = main.getDouble("temp");
        int humidity = main.getInt("humidity");
        String weatherDescription = jsonObj.getJSONArray("weather").getJSONObject(0).getString("description");

        System.out.println("===================================");
        System.out.println(" Weather Data for " + cityName + " ");
        System.out.println("===================================");
        System.out.println("Temperature: " + temperature + "°C");
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Description: " + weatherDescription);
        System.out.println("===================================");
    }
}
