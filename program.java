public class program {
    
    public static void main(String[] args) throws Exception {
     
        grabGame api = new grabGame("valorant", "games.json");
        api.requestCorrectGame();
        
    }

}