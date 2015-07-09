package insta;

import org.jinstagram.Instagram;
import org.jinstagram.exceptions.InstagramException;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.TimerTask;

/**
 * Created by mantttttas on 2015-07-07.
 */
public class ImageUpdateTask extends TimerTask {

    PhotoFrame photoPanel;

    InstagramFeedIterator iterator;

    String tagName;

    public ImageUpdateTask(PhotoFrame photoPanel, String tagName) throws IOException {
        this.photoPanel = photoPanel;
        this.tagName = tagName;
        this.iterator = createIterator();
    }

    private InstagramFeedIterator createIterator() throws IOException {
        Instagram instagram = new Instagram(System.getProperty("clientId"));
        if(System.getProperty("proxy") != null)
            instagram.setRequestProxy(new ApplicationProxyProvider().getApplicationProxy());
        return new InstagramFeedIterator(instagram, tagName, 10);
    }

    @Override
    public void run(){
           if (!iterator.hasNext()) {
               try {
                   iterator = createIterator();
               } catch (IOException e) {
                   photoPanel.dislpayError(e.getMessage());
               }
           } else {
               try {
                   photoPanel.updateMedia(iterator.next());
               } catch (IOException e) {
                   photoPanel.dislpayError(e.getMessage());
               }
           }

        }

}
