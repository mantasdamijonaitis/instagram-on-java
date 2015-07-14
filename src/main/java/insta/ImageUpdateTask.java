package insta;

import org.jinstagram.Instagram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.TimerTask;

/**
 * Created by mantttttas on 2015-07-07.
 */
public class ImageUpdateTask extends TimerTask {

    final PhotoFrame photoPanel;

    InstagramFeedIterator iterator;

    final String tagName;

    public static final int PAGE_SIZE = 2;

    @Autowired
    public ImageUpdateTask(PhotoFrame photoPanel, String tagName) throws IOException {
        this.photoPanel = photoPanel;
        this.tagName = tagName;
        this.iterator = createIterator();
    }

    private InstagramFeedIterator createIterator() throws IOException {
        Instagram instagram = new Instagram(System.getProperty("clientId"));
        instagram.setRequestProxy(new ApplicationProxyProvider().getApplicationProxy());

        return new InstagramFeedIterator(instagram, tagName, PAGE_SIZE);
    }

    @Override
    public void run(){
           if (!iterator.hasNext()) {
               try {
                   iterator = createIterator();
               } catch (IOException e) {
                   photoPanel.displayError(e.getMessage());
               }
           } else {
               try {
                   photoPanel.updateMedia(iterator.next());
               } catch (IOException e) {
                   photoPanel.displayError(e.getMessage());
               }
           }

        }

}
