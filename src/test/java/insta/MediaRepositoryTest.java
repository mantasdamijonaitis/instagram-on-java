package insta;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by mantttttas on 2015-07-13.
 */
public class MediaRepositoryTest {

    @Mock ExecutorService executor;
    @Mock ApplicationProxyProvider provider;
    @Mock Image image;
    @Mock Future future;

    @Before
    public void setup() throws IOException {

        MockitoAnnotations.initMocks(this);
        when(executor.submit(any(Callable.class))).thenReturn(future);

    }

    @Test
    public void checkIfMediaRepositoryReturnsAllImages() throws IOException {

        System.clearProperty("proxy");

        Set<URL> urlSet = new HashSet<URL>();

        urlSet.add(new URL("http://www.ccc.com"));
        urlSet.add(new URL("http://www.bbb.com"));
        urlSet.add(new URL("http://www.aaa.com"));

        assertEquals(3, new MediaRepository(executor, provider).getImages(urlSet, 100, 100).size());

    }

    @Test
    public void checkIfImagesDoNotDuplicate() throws IOException {

        System.clearProperty("proxy");

        Set<URL> urlSet = new HashSet<URL>();

        urlSet.add(new URL("http://www.ccc.com"));
        urlSet.add(new URL("http://www.ccc.com"));

        assertEquals(1, new MediaRepository(executor,provider).getImages(urlSet,100,100).size());

    }

    @Test(expected = Exception.class)
    public void checkIfMediaRepositoryHandlesWrongUrls() throws IOException {

        Set<URL> urlSet = new HashSet<URL>();

        urlSet.add(new URL("http://www.ccc.com"));
        urlSet.add(new URL("http://www.bbb.com"));
        urlSet.add(new URL("http://www.aaa.com"));

        reset(executor);
        new MediaRepository(executor,provider).getImages(urlSet,100,100);

    }

}
