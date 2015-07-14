package insta;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.print.attribute.standard.Media;

import static org.mockito.Mockito.*;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.*;

/**
 * Created by mantttttas on 2015-07-13.
 */
public class MediaRepositoryTest {

    @Mock ExecutorService executor;

    @Before
    public void setup() throws IOException {

        MockitoAnnotations.initMocks(this);

        //urls = new HashSet<URL>(100);

    }

    @Test
    public void voidCheckIfMediaRepositoryReturnsAllImages() throws IOException {

        URL url1 = new URL("http://i845.photobucket.com/albums/ab11/clk2me/sml-Clk2Me-Logo.jpg");
        URL url2 = new URL("http://i727.photobucket.com/albums/ww278/online4success78/6url_dot_us_header.png");

        Set<URL> urlSet = new HashSet<URL>();
        urlSet.add(url1);
        urlSet.add(url2);

        MediaRepository repository = new MediaRepository(executor);

        assertEquals(2,repository.getImages(urlSet,100,100).size());

    }

}
