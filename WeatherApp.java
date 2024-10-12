import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherApp {

    private static final String API_KEY = "f5fdd4f9fb9f27133daae2b27aaca61a"; // OpenWeatherMap API key
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

    public static void main(String[] args) {
        String city = "Seattle";  // City
        try {
            getWeather(city);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getWeather(String city) throws Exception {
        String urlString = BASE_URL + city + "&appid=" + API_KEY + "&units=metric";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON response
            JSONObject weatherData = new JSONObject(response.toString());
            String weatherDescription = weatherData.getJSONArray("weather").getJSONObject(0).getString("description");
            double temp = weatherData.getJSONObject("main").getDouble("temp");

            System.out.println("City: " + city);
            System.out.println("Current Temperature: " + temp + "°C");
            System.out.println("Weather: " + weatherDescription);
        } else {
            System.out.println("GET request failed. Response Code: " + responseCode);
        }
    }
}