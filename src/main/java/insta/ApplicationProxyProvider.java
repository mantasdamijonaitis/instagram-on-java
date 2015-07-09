package insta;

import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.getProperty;

public class ApplicationProxyProvider {

    Pattern USERINFO = Pattern.compile("^([A-Za-z0-9]+):([A-Za-z0-9]+)$");

    Proxy getApplicationProxy() throws MalformedURLException {
        final URL url;
        String userDefinedProxy = System.getProperty("proxy");
        if (userDefinedProxy != null) {
            try {
                url = new URL(userDefinedProxy);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new MalformedURLException();
            }

            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(url.getHost(), url.getPort()));

            Matcher userInfoMatcher = USERINFO.matcher(url.getUserInfo());
            if (userInfoMatcher.matches()) {

                final String username = userInfoMatcher.group(1);
                final String password = userInfoMatcher.group(2);

                Authenticator authenticator = new Authenticator() {

                    public PasswordAuthentication getPasswordAuthentication() {
                        return (new PasswordAuthentication(username,
                                password.toCharArray()));
                    }
                };
                Authenticator.setDefault(authenticator);
            }

            return proxy;
        } else{
            return Proxy.NO_PROXY;
        }

    }

}




