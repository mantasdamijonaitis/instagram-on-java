package insta;

import org.jinstagram.exceptions.InstagramException;

import javax.swing.*;
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
        this.iterator = iterator;

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
               e.printStackTrace();
            }
            mainWindow.getContentPane().removeAll();
            mainWindow.getContentPane().add(photoPanel);
            mainWindow.setVisible(true);

        }

    }




    /*boolean isSuccessful = true;

    JFrame mainWindow;
    JPanel photoPanel;

    InstagramFeedIterator iterator;

    String userId;

    public RepeatableTask(InstagramFeedIterator iterator, JFrame mainWindow, JPanel photoPanel, String userId) {
        this.mainWindow = mainWindow;
        this.photoPanel = photoPanel;
        this.userId = userId;
        this.iterator = iterator;
        getRepeatableTask();
    }

   public TimerTask getRepeatableTask() {
       System.out.println("works");

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("works");
                try {
                    if (iterator.hasNext()) {
                        photoPanel = new PhotoFrame(iterator.next()).getCompletePhotoPanel();
                    } else {
                        iterator = new InstagramFeedIterator(userId);
                        photoPanel = new PhotoFrame(iterator.next()).getCompletePhotoPanel();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    isSuccessful = false;
                }
                mainWindow.getContentPane().removeAll();
                mainWindow.getContentPane().add(photoPanel);
                mainWindow.setVisible(true);

            }
        };

        if(isSuccessful)
            return task;
        else return null;

    }

}*/