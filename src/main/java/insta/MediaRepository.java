package insta;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.Key;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by mantttttas on 2015-07-13.
 */

@Repository
public class MediaRepository {

    final ExecutorService executor;
    final ApplicationProxyProvider applicationProxy;
    LoadingCache<Key, Image> cache;
    Map<URL,Point> map = new HashMap<URL,Point>();

    @Autowired
    public MediaRepository(@Qualifier(value = "connectionExecutors") ExecutorService executor, ApplicationProxyProvider applicationProxy){
        this.executor = executor;
        this.applicationProxy = applicationProxy;
    }

    public Map<URL, Image> getImages(Set<URL> urls, final Point p) throws IOException {

        final Map<URL, Future<Image>> futures = new HashMap<URL, Future<Image>>();/// cia map, ne future listas

        for (final URL imageUrl : urls) {

            Future<Image> future = executor.submit(new Callable<Image>() {

                public Image call() throws Exception {
                    return getImage(imageUrl, p);
                }
            });

            futures.put(imageUrl, future); /// supila taip, kad futures tampa lygus null, tai man reikia paduot fake future list

        }

        return new HashMap<URL, Image>(){{

            for(URL url : futures.keySet()){
                try {
                    put(url, futures.get(url).get());
                } catch (Exception e) {
                    put(url, getImage(url, p));
                }
            }



        }};


    }

    public void getImage(final URL url, final Point p){

        CacheLoader<Key, Image> loader = new CacheLoader<Key, Image>() {
            public Image load(Key key) throws Exception {
                
                return getImage(key.getUrl(), key.getDimesions());
            }
        };

        cache = CacheBuilder.newBuilder().build(loader);

    }

    public Image getImage(URL url, Point p) throws IOException {

        URLConnection urlConnection = url.openConnection(applicationProxy.getApplicationProxy());
        urlConnection.setReadTimeout(2000);
        final InputStream inStream = urlConnection.getInputStream();
        map.put(url,p);
        return ImageIO.read(inStream).getScaledInstance(p.x, p.y, Image.SCALE_SMOOTH);

    }

    public static class Key {

        private final URL url;
        private final Point dimesions;


        public Key(URL url, Point dimesions) {
            this.url = url;
            this.dimesions = dimesions;
        }

        public URL getUrl() {
            return url;
        }

        public Point getDimesions() {
            return dimesions;
        }

        @Override
        public int hashCode() {
            return url.hashCode() + 17 * dimesions.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Key) {
                Key key = (Key) o;
                if (!key.dimesions.equals(dimesions)) {
                    return false;
                }
                if (!key.url.equals(url)) {
                    return false;
                }
                return true;
            }
            return false;
        }
    }
}

