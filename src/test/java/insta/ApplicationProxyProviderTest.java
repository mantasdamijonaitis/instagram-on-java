package insta;

import org.junit.Test;

import java.net.*;

import static org.junit.Assert.*;

/**
 * Created by mantttttas on 2015-07-07.
 */
public class ApplicationProxyProviderTest {

    @Test
    public void testIfApplicationProxyThrowsException() {
        System.setProperty("proxy","");
        try {
            new ApplicationProxyProvider().getApplicationProxy();
        } catch (MalformedURLException e) {
            assertNotNull(e.toString());
        }

    }

    @Test
    public void testIfProxyIsReturned() throws MalformedURLException {

        System.setProperty("proxy", "http://mantas:damijonaitis@proxy.fakewebpage.com:8080");
        assertEquals(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.fakewebpage.com", 8080)),
                new ApplicationProxyProvider().getApplicationProxy());

    }

    @Test
    public void testIfProxyPatternWorksRight() throws  MalformedURLException {

        System.setProperty("proxy", "http://mantasDamijonaitis@proxy.fakewebpage.com:8080");
        assertEquals(Proxy.NO_PROXY,new ApplicationProxyProvider().getApplicationProxy());

    }

    @Test
    public void testIfApplicationProxyProviderChecksForSystemVariablesCorrectly() throws MalformedURLException {

        System.clearProperty("proxy");
        assertEquals(Proxy.NO_PROXY, new ApplicationProxyProvider().getApplicationProxy());

    }

}
