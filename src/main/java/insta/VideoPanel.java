package insta;

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
@Component
public class VideoPanel extends JPanel {

    private static Logger LOG = Logger.getLogger(VideoPanel.class.getName());

    private final String mediaUrl;
    private Rectangle dimensions;
    public boolean isPlaying;

    public VideoPanel(String mediaUrl, Rectangle screenDimensions){

        this.mediaUrl = mediaUrl;
        this.dimensions = screenDimensions;

        final VideoPanel mutex = this;

        setBounds(dimensions.x, dimensions.y, 640, 640);
        setVisible(true);
        setBackground(Color.BLACK);

        final JFXPanel playerPanel = new JFXPanel();
        playerPanel.setBackground(Color.BLACK);
        add(playerPanel);

        initFxLater(playerPanel);

    }

    private void initFxLater(final JFXPanel panel){
        final Media media = new Media(mediaUrl);
        final MediaPlayer mediaPlayer = new MediaPlayer(media);

        Thread videoLoadThread = new Thread(new Runnable() {
            public void run() {
                Group root = new Group();
                final Scene scene = new Scene(root, 640, 640);

                MediaView mediaView = new MediaView(mediaPlayer);
                ((Group) scene.getRoot()).getChildren().add(mediaView);

                panel.setScene(scene);

                mediaPlayer.play();

                isPlaying = true;

            }

        });

        mediaPlayer.setOnReady(videoLoadThread);

        if(!videoLoadThread.isAlive()){
            isPlaying = false;
        }
    }

}
