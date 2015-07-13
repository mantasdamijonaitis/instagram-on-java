package insta;

import org.jinstagram.Instagram;
import org.jinstagram.entity.comments.CommentData;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by mantttttas on 2015-07-13.
 */
public class MediaRepositoryTest {

    @Test
    public void voidcheckIfMediaRepositoryReturnsAllImages() throws IOException {
        Instagram instagram = new Instagram(System.getProperty("clientId"));
        InstagramFeedIterator iterator = new InstagramFeedIterator(instagram,"vilniuscc",10);

        int counter = 0;
        while(iterator.hasNext()){
            counter += iterator.next().getComments().getCount();
        }

        int counter2 = 0;
        InstagramFeedIterator iterator2 = new InstagramFeedIterator(instagram,"vilniuscc",10);
        while(iterator2.hasNext()) {
            final Set<URL> list = new HashSet<URL>();
            for (CommentData comment : iterator2.next().getComments().getComments()) {
                list.add(new URL(comment.getCommentFrom().getProfilePicture()));
            }
            counter2+=MediaRepository.getImages(list,100,100).size();
        }

        assertTrue("Amount of images differ in simple and async mode", counter == counter2);

    }

}
