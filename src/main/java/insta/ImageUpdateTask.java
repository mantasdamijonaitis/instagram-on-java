package insta;

import org.jinstagram.Instagram;

import java.io.IOException;
import java.util.TimerTask;

/**
 * Created by mantttttas on 2015-07-07.
 */
public class ImageUpdateTask extends TimerTask {

    PhotoFrame photoPanel;

    InstagramFeedIterator iterator;

    String tagName;

    private final int pageAmount = 2;

    public ImageUpdateTask(PhotoFrame photoPanel, String tagName) throws IOException {
        this.photoPanel = photoPanel;
        this.tagName = tagName;
        this.iterator = createIterator();
    }

    private InstagramFeedIterator createIterator() throws IOException {
        Instagram instagram = new Instagram(System.getProperty("clientId"));
            instagram.setRequestProxy(new ApplicationProxyProvider().getApplicationProxy());
        return new InstagramFeedIterator(instagram, tagName, pageAmount);
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
