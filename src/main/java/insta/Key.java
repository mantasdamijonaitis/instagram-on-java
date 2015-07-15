package insta;

import java.awt.*;
import java.net.URL;

/**
 * Created by mantttttas on 2015-07-15.
 */
public class Key {

    private final URL url;
    private final Point dimensions;

    public Key(URL url, Point dimesions) {
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
        if (o instanceof Key) {
            Key key = (Key) o;
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
