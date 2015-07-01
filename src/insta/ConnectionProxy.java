package insta;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;

/**
 * Created by mantttttas on 2015-07-01.
 */
public class ConnectionProxy {

    Proxy proxy;

    public ConnectionProxy(){

        initializeProxy();

    }


    void initializeProxy(){

        proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxyvip.foreningssparbanken.se",8080));

        Authenticator authenticator = new Authenticator() {

            public PasswordAuthentication getPasswordAuthentication() {
                return (new PasswordAuthentication("p998olj",
                        "p998olj".toCharArray()));
            }
        };

        Authenticator.setDefault(authenticator);

    }

    Proxy getProxy(){

        return proxy;

    }

}
