package insta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
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

    public Map<URL, Image> getImages(Set<URL> urls, final int width, final int height) throws IOException {

        final Map<URL, Future<Image>> futures = new HashMap<URL, Future<Image>>();/// cia map, ne future listas

        for (final URL imageUrl : urls) {

            Future<Image> future = executor.submit(new Callable<Image>() {

                public Image call() throws Exception {
                    return getImage(imageUrl, width, height);
                }
            });

            futures.put(imageUrl, future); /// supila taip, kad futures tampa lygus null, tai man reikia paduot fake future list

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

    public Image getImage(URL url, int width, int height) throws IOException {
        URLConnection urlConnection = url.openConnection(applicationProxy.getApplicationProxy());
        urlConnection.setReadTimeout(2000);
        final InputStream inStream = urlConnection.getInputStream();

        return ImageIO.read(inStream).getScaledInstance(width, height, Image.SCALE_SMOOTH);

    }

}

