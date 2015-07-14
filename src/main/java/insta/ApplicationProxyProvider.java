package insta;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ApplicationProxyProvider {

    public static final Pattern USERINFO = Pattern.compile("^([A-Za-z0-9]+):([A-Za-z0-9]+)$");

    static Proxy getApplicationProxy() throws IOException {

        final String proxyUrl = System.getProperty("proxy");
        if (proxyUrl != null) {

            final URL url = new URL(proxyUrl);

            final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(url.getHost(), url.getPort()));

            final Matcher userInfoMatcher = USERINFO.matcher(url.getUserInfo());
            if (userInfoMatcher.matches()) {

                final String username = userInfoMatcher.group(1);
                final String password = userInfoMatcher.group(2);

                final Authenticator authenticator = new Authenticator() {

                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,
                                password.toCharArray());
                    }
                };
                Authenticator.setDefault(authenticator);
            } else {
                throw new IOException("Proxy url is wrong");
            }

            return proxy;

        }
        return Proxy.NO_PROXY;
    }

}






