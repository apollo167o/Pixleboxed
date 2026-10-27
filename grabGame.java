// uses Rawg for games
import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.util.*;

import java.nio.charset.*;
import juanparser.*;

public class grabGame {

    final String JSON_PATH = "games.json";
    final String API_KEY = System.getenv("RAWG_API_KEY");
    final String NAME_URL = "https://api.rawg.io/api/games?search=%s&key=%s&page_size=20";
    final String SLUG_URL = "https://api.rawg.io/api/games/%s?key=%s";
    final String game;

    public record Game(String name, String slug) {};

    public grabGame(String game) {
        this.game = game;
    }

    private Map<String, Object> sendReq(String unformatted_url, Object[] args) {

        String url = String.format(unformatted_url, args); // Excpected formatted args

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        try {

            HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );

            JsonRead reader = new JsonRead(response.body(), false);
            return reader.fromJson();

        } catch (IOException | InterruptedException e) {

            System.out.println(String.format("Error %s Occured", e));
            return Map.of();

        }



    }

    public List<Game> fetchPossibleSlugs() {

        Object[] args = {
            URLEncoder.encode(game, StandardCharsets.UTF_8).replace("+", "%20"),
            URLEncoder.encode(API_KEY, StandardCharsets.UTF_8)
        };

        Object rawResults = sendReq(NAME_URL, args).get("results");
        List<Game> possible = new ArrayList<>();

        if (!(rawResults instanceof List<?> results)) {
            return List.of();
        }

        for (Object item : results) {

            if (!(item instanceof Map<?, ?> gameMap)) continue;

            String name = gameMap.get("name").toString();
            String slug = gameMap.get("slug").toString();

            if (name == null || slug == null) continue;

            possible.add(new Game(name, slug));

        }

        return possible;

    }

    private void appendToFile(String slug, Map<String, Object> content) {

        JsonRead reader = new JsonRead(JSON_PATH, true);
        Map<String, Object> old = new HashMap<>(reader.fromJson());

        old.put(slug, content);

        try {
            JsonWrite.writes(new HashMap<>(old), JSON_PATH);
        } catch (IOException e) {
            System.out.println(String.format("Failed to write to %s", JSON_PATH));
        }
    }

    private void createEntry(String slug) {

        Object[] args = {
            URLEncoder.encode(slug, StandardCharsets.UTF_8),
            URLEncoder.encode(API_KEY, StandardCharsets.UTF_8)
        };

        Map<String, Object> data = sendReq(SLUG_URL, args);
        Map<String, Object> newContent = new HashMap<>();
        List<String> genres = new ArrayList<>();

        newContent.put("name", data.get("name"));
        newContent.put("image", data.get("background_image"));
        newContent.put("description", data.get("description_raw"));

        if (!(data.get("genres") instanceof List<?> genreList)) return; 
        
        for (Object item : genreList) {

            if (!(item instanceof Map<?, ?> curGenre)) continue;

            genres.add(curGenre.get("name").toString());


        }

        newContent.put("genres", genres);

        appendToFile(slug, newContent);

    }

    public void addEntry() {

        List<Game> possible = fetchPossibleSlugs();

        // get correct slug (will likely be replaced by GUI later on)
        for (int i = 0; i < possible.size(); i++) {

            System.out.println(String.format("%d | %s", i+1, possible.get(i).name));

        }

        Scanner sc = new Scanner(System.in);
        int correctIndex = Integer.valueOf(sc.nextLine()) - 1;
        sc.close();
        String correctSlug = possible.get(correctIndex).slug;

        createEntry(correctSlug);
    }



}