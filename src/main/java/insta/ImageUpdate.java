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
public class ImageUpdate extends TimerTask {

    JFrame mainWindow;
    JPanel photoPanel;

    InstagramFeedIterator iterator;

    String userId;

    public ImageUpdate(JFrame mainWindow, JPanel photoPanel, String userId) throws IOException {
        this.mainWindow = mainWindow;
        this.photoPanel = photoPanel;
        this.userId = userId;
        this.iterator = createIterator();
    }

    private InstagramFeedIterator createIterator() throws IOException {
        Instagram instagram = new Instagram(System.getProperty("clientId"));
        if(System.getProperty("proxy") != null)
            instagram.setRequestProxy(new ApplicationProxyProvider().getApplicationProxy());
        return new InstagramFeedIterator(instagram, userId, 10);
    }


    @Override
    public void run() {
        try {
               if (iterator.hasNext()) {
                   photoPanel = new PhotoFrame(iterator.next()).getCompletePhotoPanel();
               } else {
                   iterator = createIterator();
                   photoPanel = new PhotoFrame(iterator.next()).getCompletePhotoPanel();
               }
           } catch (IOException e) {
               mainWindow.getContentPane().removeAll();
               JLabel errorLabel = new JLabel(e.toString());
               errorLabel.setVisible(true);
               mainWindow.add(errorLabel);
               mainWindow.setVisible(true);
            }
            mainWindow.getContentPane().removeAll();
            mainWindow.getContentPane().add(photoPanel);
            mainWindow.setVisible(true);

        }

}
