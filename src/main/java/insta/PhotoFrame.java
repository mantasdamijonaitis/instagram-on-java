package insta;

import org.jinstagram.entity.comments.CommentData;
import org.jinstagram.entity.common.Caption;
import org.jinstagram.entity.common.Comments;
import org.jinstagram.entity.common.User;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by mantttttas on 2015-07-01.
 */
@Component
public class PhotoFrame extends JPanel {

    final LayoutMetrics metrics;
    final MediaRepository repository;
    final Dimension2D screenDimensions;

    @Autowired
    public PhotoFrame(MediaRepository repository, LayoutMetrics metrics) {
        this.metrics = metrics;
        this.repository = repository;
        setLayout(new CardLayout(0, 0));
        this.screenDimensions = getScreenDimension();
        setBounds(0, 0, (int) screenDimensions.getWidth(), (int) screenDimensions.getHeight());
        setBackground(Color.BLACK);
        setVisible(true);
    }

    public void updateMedia(MediaFeedData media) throws Exception {
        removeAll();

        final JLabel caption = getCaption(media.getCaption());
        final JLabel uploaderName = getUploaderName(media.getUser());
        final JLabel uploaderProfilePicture = getUploaderProfilePicture(media.getUser().getProfilePictureUrl());
        final List<JLabel> comment = getComments(media.getComments(), screenDimensions);
        final List<JLabel> commenterImage = getCommenterImages(media.getComments(), screenDimensions);
        final JLayeredPane layeredPhotoPanel = createPanel(caption, uploaderName, uploaderProfilePicture, commenterImage, comment);

        add(layeredPhotoPanel, "layeredPhotoPanel");

        if(media.getVideos() != null) {
            String fixedUrl = media.getVideos().getStandardResolution().getUrl().replace("https","http");
            JPanel videoPanel = getVideo(fixedUrl);
            layeredPhotoPanel.setLayer(videoPanel, 1);
            layeredPhotoPanel.add(videoPanel);
            Thread.sleep(7000);
        } else {
            JLabel mainImage = getMainPicture(media.getImages().getStandardResolution().getImageUrl());
            layeredPhotoPanel.setLayer(mainImage, 1);
            layeredPhotoPanel.add(mainImage);
        }


        revalidate();
        repaint();

    }

    private final JLayeredPane createPanel(JLabel caption, JLabel uploaderName, JLabel uploaderProfilePicture, List<JLabel> commenterImage, List<JLabel> comment) {

        final JLayeredPane layeredPhotoPanel = new JLayeredPane();
        layeredPhotoPanel.setBackground(Color.BLACK);

        layeredPhotoPanel.setLayer(caption, 1);
        layeredPhotoPanel.add(caption);

        layeredPhotoPanel.setLayer(uploaderProfilePicture, 1);
        layeredPhotoPanel.add(uploaderProfilePicture);

        layeredPhotoPanel.setLayer(uploaderName, 1);
        layeredPhotoPanel.add(uploaderName);

        for (int i = 0; i < commenterImage.size(); i++) {

            layeredPhotoPanel.setLayer(commenterImage.get(i), 1);
            layeredPhotoPanel.add(commenterImage.get(i));

            layeredPhotoPanel.setLayer(comment.get(i), 1);
            layeredPhotoPanel.add(comment.get(i));

        }

        return layeredPhotoPanel;

    }

    private Dimension2D getScreenDimension() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    private JPanel getVideo(String videoPath) {

        VideoPlayer video = new VideoPlayer(videoPath,metrics.getImageMetrics());

        return video.getVideoPanel();

    }

    private JLabel getUploaderProfilePicture(String uploaderUrl) throws Exception {

        final JLabel uploaderImage = new JLabel();
        Point pictureDimensions = new Point(metrics.getUploaderImageMetrics().width,
                metrics.getUploaderImageMetrics().height);

        uploaderImage.setBounds((metrics.getUploaderImageMetrics()));

        uploaderImage.setIcon(new ImageIcon(repository.getImage(new URL(uploaderUrl), pictureDimensions)));

        return uploaderImage;

    }

    private JLabel getCaption(Caption captionInfo) {

        final JLabel caption = new JLabel();

        caption.setForeground(Color.ORANGE);
        caption.setFont(new Font("Tahoma", Font.PLAIN, 50));

        caption.setText(captionInfo.getText());
        caption.setBounds(metrics.getCaptionMetrics());

        return caption;

    }

    private JLabel getUploaderName(User user) {

        final JLabel uploaderName = new JLabel();
        uploaderName.setHorizontalAlignment(SwingConstants.CENTER);
        uploaderName.setFont(new Font("Tahoma", Font.PLAIN, 30));
        uploaderName.setForeground(Color.ORANGE);

        uploaderName.setText(user.getUserName());
        uploaderName.setBounds(metrics.getUploaderNameMetrics());

        return uploaderName;
    }


    private JLabel getMainPicture(String imagePath) throws Exception {

        final JLabel pictureLabel = new JLabel();
        pictureLabel.setBounds(metrics.getImageMetrics());
        Point pictureDimensions = new Point(metrics.getImageMetrics().width,metrics.getImageMetrics().height);

        pictureLabel.setIcon(new ImageIcon(repository.getImage(new URL(imagePath),pictureDimensions)));

        return pictureLabel;

    }

    private List<JLabel> getComments(Comments commentsData, Dimension2D screenDimensions) {

        final Rectangle bounds = metrics.getCommentMetrics();

        final List<JLabel> commentList = new LinkedList<JLabel>();

        int startPositionY = (int) metrics.getCommenterImageMetrics().getY();

        for (CommentData comment : commentsData.getComments()) {

            final JLabel label = new JLabel(comment.getText());
            label.setForeground(Color.ORANGE);
            label.setFont(new Font("Tahoma", Font.PLAIN, 15));
            label.setBounds(
                    bounds.x,
                    startPositionY - (int) screenDimensions.getHeight() / 30,
                    bounds.width,
                    bounds.height);

            commentList.add(label);

            startPositionY += screenDimensions.getHeight() / 15;

        }

        return commentList;

    }

    private List<JLabel> getCommenterImages(Comments commentsData, Dimension2D screenDimensions) throws Exception {

        final Rectangle bounds = metrics.getCommenterImageMetrics();

        final Set<URL> list = new HashSet<URL>();

        for (CommentData comment : commentsData.getComments()) {
            list.add(new URL(comment.getCommentFrom().getProfilePicture()));
        }

        final Map<URL, Image> images = repository.getImages(list, new Point(bounds.width, bounds.height));

        final List<JLabel> commenterImages = new LinkedList<JLabel>();
        int startPositionY = (int) metrics.getCommenterImageMetrics().getY();

        for (CommentData comment : commentsData.getComments()) {

            final JLabel label = new JLabel();
            label.setIcon(new ImageIcon(images.get(new URL(comment.getCommentFrom().getProfilePicture()))));

            label.setBounds(bounds.x,
                    startPositionY,
                    bounds.width,
                    bounds.height);

            commenterImages.add(label);
            startPositionY += screenDimensions.getHeight() / 15;

        }

        return commenterImages;

    }

    public void displayError(String message) {

    }
}
