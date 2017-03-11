import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ds.desktop.notify.DesktopNotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.TimerTask;


/**
 * Created by devmachine on 2/27/17.
 */

public class URLConnectionReader extends TimerTask {

    private String query;
    private URL url;
    private int points;
    private long dateNowInSeconds;
    private long dateOneWeekAgoInSeconds;
    private Hits hitResults;


    public void setQuery(String query) {
        this.query = replaceSpaces(query);
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public long getDateNowInSeconds() {
        return dateNowInSeconds;
    }

    public void setDateNowInSeconds(long dateNowInSeconds) {
        this.dateNowInSeconds = dateNowInSeconds;
    }

    public long getDateOneWeekAgoInSeconds() {
        return dateOneWeekAgoInSeconds;
    }

    public void setDateOneWeekAgoInSeconds(long dateOneWeekAgoInSeconds) {
        this.dateOneWeekAgoInSeconds = dateOneWeekAgoInSeconds;
    }

    public Hits getHitResults() { return hitResults; }

    public void setHitResults(Hits hitResults) { this.hitResults = hitResults; }



    private String replaceSpaces(String str) {
        String[] words = str.split(" ");
        StringBuilder sentence = new StringBuilder(words[0]);

        for (int i = 1; i < words.length; ++i) {
            sentence.append("%20");
            sentence.append(words[i]);
        }

        return sentence.toString();
    }


    private String createJsonString() throws IOException {

        URLConnection yc = this.url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String jsonInput = in.readLine();
        return jsonInput;

    }

    private Hits createHits(String json){
        JsonParser parser = new JsonParser();
        JsonObject rootObject = parser.parse(json).getAsJsonObject();
        Gson gson = new Gson();
        Hits hits = gson.fromJson(rootObject, Hits.class);
        return hits;
    }

    private void listResults(Hits hits){

        if(hits.getNbHits() == 0){

            DesktopNotify.showDesktopMessage("No results found", "Sorry no results have been found yet.", DesktopNotify.FAIL);
            System.out.println("Sorry, your query did not return any matches.");
        }

        else{

            DesktopNotify.showDesktopMessage("Links have been found!", "Your query has returned results.", DesktopNotify.SUCCESS);
            for(int i=0; i < hits.getHits().size(); i++){
                System.out.println("Title: " + hits.getHits().get(i).getTitle());
                System.out.println("Created: " + hits.getHits().get(i).getCreated_at());
                System.out.println("Points: " + hits.getHits().get(i).getPoints());
                System.out.println("Link: " + hits.getHits().get(i).getUrl() + "\n");

            }
        }
    }

    private String buildFormattedQuery(){

        this.setDateNowInSeconds(new Date().getTime()/1000);
        this.setDateOneWeekAgoInSeconds(getDateNowInSeconds()-604800);
        String formattedQuery = "http://hn.algolia.com/api/v1/search_by_date?query="+this.query+"&tags=story&numericFilters=created_at_i>" +this.dateOneWeekAgoInSeconds+ ",created_at_i<" +this.dateNowInSeconds +",points>" + this.points;
        return formattedQuery;

    }

    public void search() throws IOException {

        String formattedQuery = buildFormattedQuery();
        this.setUrl(new URL(formattedQuery));
        Date now = new Date(this.getDateNowInSeconds()*1000);
        Date since = new Date(this.getDateOneWeekAgoInSeconds()*1000);

        System.out.println("Searching for links which match your query created between " + since + " and " + now + " with more than " + this.getPoints() + " points.");
        String jsonInput = createJsonString();
        this.setHitResults(createHits(jsonInput));
        listResults(this.getHitResults());

    }

    @Override
    public void run() {
        try {
            search();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

