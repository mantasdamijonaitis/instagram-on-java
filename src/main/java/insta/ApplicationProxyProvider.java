package insta;

import com.sun.deploy.net.proxy.ProxyType;

import java.io.IOException;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationProxyProvider {

    static Proxy getApplicationProxy() throws IOException {

        Proxy proxy = Proxy.NO_PROXY;

        final Pattern USERINFO = Pattern.compile("^([A-Za-z0-9]+):([A-Za-z0-9]+)$");

        String proxyUrl = System.getProperty("proxy");

        if (proxyUrl != null) {

               URL url = new URL(proxyUrl);

            proxy = new Proxy(Proxy.Type.valueOf(url.getProtocol().toUpperCase()), new InetSocketAddress(url.getHost(), url.getPort()));

            Matcher userInfoMatcher = USERINFO.matcher(url.getUserInfo());
            if (userInfoMatcher.matches()) {

                final String username = userInfoMatcher.group(1);
                final String password = userInfoMatcher.group(2);

                Authenticator authenticator = new Authenticator() {

                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,
                                password.toCharArray());
                    }
                };
                Authenticator.setDefault(authenticator);
            } else {
                throw new IOException("Proxy url is wrong");
            }

        } return proxy;
        }

}






