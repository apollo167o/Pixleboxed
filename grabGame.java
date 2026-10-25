// uses Rawg for webscaping
import java.net.*;
import java.net.http.*;
import java.util.*;
import java.nio.charset.*;

public class grabGame {

    public static void main(String[] args) throws Exception {
        String API_KEY = System.getenv("RAWG_API_KEY"); // set api as environment variable
        String search = URLEncoder.encode("zelda", StandardCharsets.UTF_8)
            .replace("+", "%20");

        String url = String.format("https://api.rawg.io/api/games?search=%s&key=%s", search, URLEncoder.encode(API_KEY, StandardCharsets.UTF_8));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println(response.body());

    }

}