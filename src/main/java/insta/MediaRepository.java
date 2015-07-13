package insta;

import net.sf.ehcache.CacheManager;
import org.apache.commons.lang3.math.NumberUtils;
import org.jinstagram.Instagram;
import org.jinstagram.entity.comments.CommentData;
import org.jinstagram.entity.common.Comments;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Media;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by mantttttas on 2015-07-13.
 */

public class MediaRepository {

    private static final ExecutorService executor = Executors.newFixedThreadPool(
            Integer.valueOf(System.getProperty("thread.pool.size", "2")));

    public static Map<URL, Image> getImages(Set<URL> urls, final int width, final int height) throws IOException {

        final Map<URL, Future<Image>> futures = new HashMap<URL, Future<Image>>();

        for (final URL imageUrl : urls) {

            Future<Image> future = executor.submit(new Callable<Image>() {

                public Image call() throws Exception {
                    return getImage(imageUrl, width, height);
                }
            });

            futures.put(imageUrl, future);

        }

        return new HashMap<URL, Image>(){{

            for(URL url : futures.keySet()){
                try {
                    put(url, futures.get(url).get());
                } catch (Exception e) {
                    put(url, getImage(url, width, height));
                }
            }

        }};


    }

    private static Image getImage(URL url, int width, int height) throws IOException {

        final Proxy proxy = new ApplicationProxyProvider().getApplicationProxy();
        URLConnection urlConnection = url.openConnection(proxy);
        urlConnection.setReadTimeout(2000);
        final InputStream inStream = urlConnection.getInputStream();

        return ImageIO.read(inStream).getScaledInstance(width, height, Image.SCALE_SMOOTH);

    }

}
