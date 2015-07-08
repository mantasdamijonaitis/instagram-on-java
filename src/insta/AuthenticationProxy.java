package insta;

import java.net.*;

import static java.lang.System.getProperty;

public class AuthenticationProxy {
    URL url = null;
    public AuthenticationProxy(){

    }

    Proxy getProxy() {


        if (System.getProperty("proxy") != null) {


            try {
                url = new URL(getProperty("proxy"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(url.getHost(), url.getPort()));

            Authenticator authenticator = new Authenticator() {

                public PasswordAuthentication getPasswordAuthentication() {
                    return (new PasswordAuthentication(url.getUserInfo().split(":")[0],
                            url.getUserInfo().split(":")[1].toCharArray()));
                }
            };

            Authenticator.setDefault(authenticator);
            return proxy;
        } else return null;

    }

}




