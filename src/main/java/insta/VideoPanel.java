package insta;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mantttttas on 2015-07-16.
 */
public class VideoPanel extends JPanel {

    private static Logger LOG = Logger.getLogger(VideoPanel.class.getName());

    private Rectangle dimensions;
    private final String mediaUrl;

    private final Object finish = new Object();

    public VideoPanel(final String mediaUrl, Rectangle screenDimensions){
        this.mediaUrl = mediaUrl;
        this.dimensions = screenDimensions;


        setBounds(dimensions.x, dimensions.y, 640, 640);
        setVisible(true);
        setBackground(Color.BLACK);


        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                initSwingLater();
            }

        });

        /*
        Platform.runLater(new Runnable() {

            public void run() {

                final Media media = new Media(mediaUrl);
                final MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(2);
                mediaPlayer.setAutoPlay(true);
                mediaPlayer.setOnEndOfMedia(new Runnable() {
                    public void run() {

                        synchronized (finish) {
                            LOG.info("Notify on end of video");
                            finish.notifyAll();
                        }
                    }
                });

                final Scene scene = new Scene(new Group(), 640, 640);
                MediaView mediaView = new MediaView(mediaPlayer);
                ((Group) scene.getRoot()).getChildren().add(mediaView);


                final JFXPanel playerPanel = new JFXPanel();
                playerPanel.setBackground(Color.BLACK);
                add(playerPanel);
                playerPanel.setScene(scene);
            }
        });*/
    }



    private void initSwingLater(){
        setBounds(dimensions.x, dimensions.y, 640, 640);
        setVisible(true);
        setBackground(Color.BLACK);

        final JFXPanel playerPanel = new JFXPanel();
        playerPanel.setBackground(Color.BLACK);
        add(playerPanel);

        Platform.runLater(new Runnable() {

            public void run() {
                initFxLater(playerPanel);
            }
        });

    }

    private void initFxLater(JFXPanel panel){
        Group root = new Group();
        Scene scene = new Scene(root,640,640);

        Media media = new Media(mediaUrl);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(2);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {

                LOG.info("Notify on end of video");
                synchronized (finish) {
                    finish.notifyAll();
                }
            }
        });

        MediaView mediaView = new MediaView(mediaPlayer);
        ((Group)scene.getRoot()).getChildren().add(mediaView);

        panel.setScene(scene);
    }

    public void waitForFinish() {
        synchronized(finish) {
            try {
                LOG.info("Waiting for the end the video");
                finish.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
