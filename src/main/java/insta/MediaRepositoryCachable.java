package insta;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by mantttttas on 2015-07-13.
 */

@Repository
public class MediaRepositoryCachable implements MediaRepository {

    final  ExecutorService executor;
    final ApplicationProxyProvider applicationProxy;
    final Proxy proxy;

    @Autowired
    public MediaRepositoryCachable(@Qualifier(value = "connectionExecutors") ExecutorService executor, ApplicationProxyProvider applicationProxy) throws IOException {
        this.executor = executor;
        this.applicationProxy = applicationProxy;
        this.proxy = new ApplicationProxyProvider().getApplicationProxy();
    }

    public Map<URL, Image> getImages(Set<URL> urls, final Point pictureDimensions) throws Exception {

        final Map<URL, Future<Image>> futures = new HashMap<URL, Future<Image>>();/// cia map, ne future listas

        for (final URL imageUrl : urls) {

            Future<Image> future = executor.submit(new Callable<Image>() {

                public Image call() throws Exception {
                    return getImage(imageUrl,pictureDimensions);
                }
            });

            futures.put(imageUrl, future);

        }

        return new HashMap<URL, Image>(){{

            for(URL url : futures.keySet()){
                try {
                    put(url, futures.get(url).get());
                } catch (Exception e) {
                    put(url, getImage(url,pictureDimensions));
                }
            }



        }};

    }

    public Image getImage(URL url, Point imageDimensions) throws Exception {
        CacheParameter key = new CacheParameter(url,imageDimensions);
        return commenterImageCache.get(key);
    }

    private class CacheParameter {

        private final URL url;
        private final Point dimensions;

        public CacheParameter(URL url, Point dimesions) {
            this.url = url;
            this.dimensions = dimesions;
        }

        public URL getUrl() {
            return url;
        }

        public Point getDimesions() {
            return dimensions;
        }

        @Override
        public int hashCode() {
            return url.hashCode() + 17 * dimensions.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof CacheParameter) {
                CacheParameter key = (CacheParameter) o;
                if (!key.dimensions.equals(dimensions)) {
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

    private LoadingCache<CacheParameter,Image>commenterImageCache = CacheBuilder.newBuilder().
            expireAfterAccess(1,TimeUnit.DAYS).build(

            new CacheLoader<CacheParameter, Image>(){
                public Image load(CacheParameter key) throws Exception {
                    final URLConnection urlConnection = key.getUrl().openConnection(proxy);
                    urlConnection.setReadTimeout(2000);
                    final InputStream inStream = urlConnection.getInputStream();
                    return ImageIO.read(inStream).getScaledInstance(key.getDimesions().x, key.getDimesions().y, Image.SCALE_SMOOTH);
                }
            });



}

