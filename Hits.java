import java.util.ArrayList;

/**
 * Created by devmachine on 3/3/17.
 */
public class Hits {

    private ArrayList<HitObject> hits;
    private int nbHits;

    public ArrayList<HitObject> getHits() {
        return hits;
    }

    public void setHits(ArrayList<HitObject> hits) {
        this.hits = hits;
    }

    public int getNbHits() {
        return nbHits;
    }

    public void setNbHits(int nubHits) {
        this.nbHits = nubHits;
    }



}

class HitObject{

    private String created_at;
    private String title;
    private String url;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    private int points;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
