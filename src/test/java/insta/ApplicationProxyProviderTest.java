package insta;

import org.junit.Test;

import java.io.IOException;
import java.net.*;

import static org.junit.Assert.*;

/**
 * Created by mantttttas on 2015-07-07.
 */
public class ApplicationProxyProviderTest {

    @Test (expected = IOException.class)
    public void testIfApplicationProxyThrowsException() throws IOException {
        System.setProperty("proxy", "56657");
        new ApplicationProxyProvider().getApplicationProxy();

    }

    @Test
    public void testIfProxyIsReturned() throws IOException {

        System.setProperty("proxy", "http://mantas:damijonaitis@proxy.fakewebpage.com:8080");
        assertEquals(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.fakewebpage.com", 8080)),
                new ApplicationProxyProvider().getApplicationProxy());

    }

    @Test(expected = IOException.class)
    public void testIfProxyPatternWorksRight() throws IOException {

        System.setProperty("proxy", "http://mantasDamijonaitis@proxy.fakewebpage.com:8080");
        assertEquals(IOException.class, new ApplicationProxyProvider().getApplicationProxy());

    }

    @Test
    public void testIfApplicationProxyProviderChecksForSystemVariablesCorrectly() throws IOException {

        System.clearProperty("proxy");
        assertEquals(Proxy.NO_PROXY, new ApplicationProxyProvider().getApplicationProxy());

    }

}
