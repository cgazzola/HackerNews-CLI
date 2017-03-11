import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.Timer;

/**
 * Created by devmachine on 3/3/17.
 */
public class SearchExecuter {

    public static void main(String[] args) throws MalformedURLException {

        URLConnectionReader hackerNews = new URLConnectionReader();

        Scanner sc = new Scanner(System.in);
        System.out.println("Type in a topic you want to search for (Example - 'Facebook Graph API'): ");
        hackerNews.setQuery(sc.nextLine());
        System.out.println("How many points should the article have at least? ");
        hackerNews.setPoints(sc.nextInt());
        System.out.println("How often would you like the search to be performed? (in minutes): ");
        int minutes = sc.nextInt();
        long inMilliseconds = minutes * 60000;
        Timer timer = new Timer();
        timer.schedule(hackerNews, 0, inMilliseconds);


    }
}
