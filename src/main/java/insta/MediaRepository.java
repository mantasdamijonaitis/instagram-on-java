package insta;

import java.awt.*;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Created by mantttttas on 2015-07-15.
 */
public interface MediaRepository {

     Map<URL, Image> getImages(Set<URL> urls, final Point p) throws Exception;

     Image getImage(URL url, final Point p) throws Exception;

}
