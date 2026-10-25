// uses Rawg for games
import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.util.*;
import java.nio.charset.*;
import juanparser.*;

public class grabGame {

    String game;

    grabGame(String game) {
        this.game = game;
    }

    private List<?> lookupGame() { // get the results from the RAWG api

        String API_KEY = System.getenv("RAWG_API_KEY"); // set api as environment variable
        String search = URLEncoder.encode(game, StandardCharsets.UTF_8)
            .replace("+", "%20");

        String url = String.format("https://api.rawg.io/api/games?search=%s&key=%s&page_size=10", search, URLEncoder.encode(API_KEY, StandardCharsets.UTF_8));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        try {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                
            JsonRead reader = new JsonRead(response.body(), false);
            Map<String, Object> data = new HashMap<>(reader.fromJson());
            Object results = data.get("results");
            if (results instanceof List<?> list) return list;
            return List.of(); // no results returned or error from api

        } catch (IOException | InterruptedException e) {

            System.out.println(String.format("Error %s Encountered while sending API request", e));
            return List.of();

        }

    }

    public record GameChoice(String name, String slug) {} // class for storing names and slugs

    private List<GameChoice> sift() { // sift through results to get the usable oness
        List<?> results = lookupGame();
        List<GameChoice> choices = new ArrayList<>();

        for (Object item : results) {

            if(!(item instanceof Map<?, ?> game )) continue;

            Object name = game.get("name");
            Object slug = game.get("slug");


            if (name == null || slug == null) continue;

            choices.add(new GameChoice(name.toString(), slug.toString()));
        }

        return choices;

    } 

    public void requestCorrectGame() { // temporary gui will replace this

        List<GameChoice> choices = sift();

        for (int i = 0; i < choices.size(); i++) {

            System.out.println(String.format("%d -> %s", i, choices.get(i).name));

        }



    }


}