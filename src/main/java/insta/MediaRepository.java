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
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by mantttttas on 2015-07-13.
 */

@Repository
public class MediaRepository {

    final ExecutorService executor;
    final ApplicationProxyProvider applicationProxy;

    @Autowired
    public MediaRepository(@Qualifier(value = "connectionExecutors") ExecutorService executor, ApplicationProxyProvider applicationProxy){
        this.executor = executor;
        this.applicationProxy = applicationProxy;
    }

    public Map<URL, Image> getImages(Set<URL> urls, final Point p) throws Exception {

        final Map<URL, Future<Image>> futures = new HashMap<URL, Future<Image>>();/// cia map, ne future listas

        for (final URL imageUrl : urls) {

            Future<Image> future = executor.submit(new Callable<Image>() {

                public Image call() throws Exception {
                    return getImage(new Key(imageUrl,p));
                }
            });

            futures.put(imageUrl, future);

        }



        return new HashMap<URL, Image>(){{

            for(URL url : futures.keySet()){
                try {
                    put(url, futures.get(url).get());
                } catch (Exception e) {
                    put(url, getImage(new Key(url, p)));
                }
            }



        }};

    }

    private static LoadingCache<Key,Image>commenterImageCache = CacheBuilder.newBuilder().
            weakKeys().weakValues().expireAfterAccess(1,TimeUnit.DAYS).build(

            new CacheLoader<Key, Image>(){
                public Image load(Key key) throws Exception {
                    final Proxy proxy = new ApplicationProxyProvider().getApplicationProxy();
                    final URLConnection urlConnection = key.getUrl().openConnection(proxy);
                    urlConnection.setReadTimeout(2000);
                    final InputStream inStream = urlConnection.getInputStream();
                    return ImageIO.read(inStream).getScaledInstance(key.getDimesions().x, key.getDimesions().y, Image.SCALE_SMOOTH);
                }
            });


    public static Image getImage(Key key) throws ExecutionException {
        return commenterImageCache.get(key);
    }

}

