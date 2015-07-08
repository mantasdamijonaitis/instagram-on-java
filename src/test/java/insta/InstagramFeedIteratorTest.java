package insta;

import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;



/**
 * Created by mantttttas on 2015-07-02.
 */
public class InstagramFeedIteratorTest {

    @Before
    public void before() {
        System.setProperty("clientId", "3dc6dd309cb54342b21d9ae74bd0902f");

    }

    @Test
    public void testConnection() throws InstagramException {
        InstagramFeedIterator iterator = new InstagramFeedIterator("vilniuscc");
        assertNotNull(iterator);
    }

    @Test
    public void testNext() throws InstagramException {
        InstagramFeedIterator iterator = new InstagramFeedIterator("vilniuscc");
        MediaFeedData mediaData = iterator.next();
        assertNotNull(mediaData);
    }

    @Test
    public void testIsComplete() throws InstagramException {
        InstagramFeedIterator iterator = new InstagramFeedIterator("vilniuscc");
        int limit = 1000;
        int count = 0;


        while(iterator.hasNext() && count < limit) {
            iterator.next();
            count++;
        }
        assertTrue("Count exceeds limit", count < limit);
    }



    @Test
    public void testRetrievesMoreThan90() throws InstagramException {

        InstagramFeedIterator iterator = new InstagramFeedIterator("vilniuscc");
        int counter = 0;

        while(iterator.hasNext()){
            counter++;
            iterator.next();
        }

        System.out.println(counter);
        assertTrue("Result is over 90", counter > 90);

    }

    @Test
    public void testRetrievesAlmostInfinity() throws InstagramException {

        InstagramFeedIterator iterator = new InstagramFeedIterator("selfie");
        int counter = 0;

        while(iterator.hasNext() && counter <= 1000){
            counter++;
            iterator.next();
        }

        assertTrue("result is over 300", counter > 300);
    }

}
