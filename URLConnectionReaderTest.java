import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by devmachine on 3/3/17.
 */
public class URLConnectionReaderTest {

    private URLConnectionReader urlGood;
    private URLConnectionReader urlBad;

    @BeforeClass
    public static void setUpClass(){
        System.out.println("Set up class");
    }

    @Before
    public void setUp(){
        urlGood = new URLConnectionReader();
        urlBad = new URLConnectionReader();

        urlGood.setQuery("Google API");
        urlGood.setPoints(5);


        urlBad.setQuery("q3q45q345333tfgsdf");
        urlBad.setPoints(5000);

    }

    @After
    public void tearDown(){
        urlGood = null;
        urlBad = null;
    }

    @Test
    public void TestSuccessfulSearchReturnsResult() throws IOException {
        urlGood.search();
        assertTrue(urlGood.getHitResults().getNbHits() >= 1);
    }

    @Test
    public void TestSuccessfulSearchReturnsTitle() throws IOException {
        urlGood.search();
        for (HitObject hit : urlGood.getHitResults().getHits()) {
            assertFalse(hit.getTitle() != null && hit.getTitle().isEmpty());
        }
    }

    @Test
    public void TestSuccessfulSearchReturnsLink() throws IOException {

        urlGood.search();
        for (HitObject hit : urlGood.getHitResults().getHits()) {
            assertFalse(hit.getUrl() != null && hit.getUrl().isEmpty());
        }

    }

    @Test
    public void TestSuccessfulSearchReturnsInPointThreshold() throws  IOException {
        urlGood.search();
        for (HitObject hit : urlGood.getHitResults().getHits()) {
            assertTrue(hit.getPoints() >= urlGood.getPoints());
        }
    }

    @Test
    public void TestResultsBetweenCorrectTimePeriod() throws IOException {
        urlGood.search();
        assertTrue((urlGood.getDateNowInSeconds() - urlGood.getDateOneWeekAgoInSeconds()) == 604800);
    }


    @Test
    public void TestBadSearchReturnsNoResults() throws IOException {
        urlBad.search();
        assertTrue(urlBad.getHitResults().getNbHits() == 0);
    }

    @Test
    public void TestBadSearchResultReturnsNoTitle() throws IOException {

        urlBad.search();
        for (HitObject hit : urlBad.getHitResults().getHits()) {
            assertTrue(hit.getTitle() != null && hit.getTitle().isEmpty());
        }
    }

    @Test
    public void TestBadSearchResultReturnsNoLinks() throws IOException {

        urlBad.search();
        for (HitObject hit : urlBad.getHitResults().getHits()) {
            assertTrue(hit.getUrl() != null && hit.getUrl().isEmpty());
        }

    }

}