package insta;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by mantttttas on 2015-07-16.
 */
public class VideoPlayer {

    private final String mediaUrl;
    private final JPanel panel;
    private Rectangle dimensions;

    public VideoPlayer(String mediaUrl, Rectangle screenDimensions){

        this.panel = new JPanel();
        this.mediaUrl = mediaUrl;
        this.dimensions = screenDimensions;
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                initSwingLater();
            }

        });

    }

    private void initFxLater(JFXPanel panel){
        Group root = new Group();
        Scene scene = new Scene(root,640,640);

        Media media = new Media(mediaUrl);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        MediaView mediaView = new MediaView(mediaPlayer);
        ((Group)scene.getRoot()).getChildren().add(mediaView);

        panel.setScene(scene);
    }

    private void initSwingLater(){
        panel.setBounds(dimensions.x,dimensions.y,640, 640);
        panel.setVisible(true);
        panel.setBackground(Color.BLACK);

        final JFXPanel playerPanel = new JFXPanel();
        playerPanel.setBackground(Color.BLACK);
        panel.add(playerPanel);

        Platform.runLater(new Runnable() {

            public void run() {
                initFxLater(playerPanel);
            }
        });

    }

    public JPanel getVideoPanel(){
        return this.panel;
    }

}
