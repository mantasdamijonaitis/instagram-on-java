package insta;

import org.jinstagram.exceptions.InstagramException;

import javax.swing.*;
import java.awt.geom.Dimension2D;
import java.io.IOException;
import java.util.TimerTask;

/**
 * Created by mantttttas on 2015-07-07.
 */
public class RepeatableTask extends TimerTask {

    JFrame mainWindow;
    JPanel photoPanel;

    InstagramFeedIterator iterator;

    String userId;

    public RepeatableTask(JFrame mainWindow, JPanel photoPanel, String userId) throws InstagramException {
        this.mainWindow = mainWindow;
        this.photoPanel = photoPanel;
        this.userId = userId;
        this.iterator = new InstagramFeedIterator(userId);
    }



    @Override
    public void run() {
        try {
               if (iterator.hasNext()) {
                   photoPanel = new PhotoFrame(iterator.next()).getCompletePhotoPanel();
               } else {
                   iterator = new InstagramFeedIterator(userId);
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
