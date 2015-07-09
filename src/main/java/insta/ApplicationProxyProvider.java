package insta;

import com.sun.deploy.net.proxy.ProxyType;

import java.io.IOException;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationProxyProvider {

    final private Pattern USERINFO = Pattern.compile("^([A-Za-z0-9]+):([A-Za-z0-9]+)$");

    Proxy getApplicationProxy() throws IOException {
        final URL url;
        String proxyFromSystemProperties = System.getProperty("proxy");
        if (proxyFromSystemProperties != null) {

                url = new URL(proxyFromSystemProperties);

            Proxy proxy = new Proxy(Proxy.Type.valueOf(url.getProtocol().toUpperCase()), new InetSocketAddress(url.getHost(), url.getPort()));

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
                throw new IOException("There was no : between username and password");
            }

            return proxy;
        } else{
            return Proxy.NO_PROXY;
        }

    }

}




