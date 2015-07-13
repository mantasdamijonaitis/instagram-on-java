package insta;

import org.jinstagram.entity.comments.CommentData;
import org.jinstagram.entity.common.Caption;
import org.jinstagram.entity.common.Comments;
import org.jinstagram.entity.common.User;
import org.jinstagram.entity.users.feed.MediaFeedData;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;

/**
 * Created by mantttttas on 2015-07-01.
 */
public class PhotoFrame extends JPanel {

    public PhotoFrame() {
        setLayout(new CardLayout(0, 0));
        final Dimension2D screenDimensions = getScreenDimension();
        setBounds(0, 0, (int) screenDimensions.getWidth(), (int) screenDimensions.getHeight());
        setBackground(Color.BLACK);
        setVisible(true);
    }

    public void updateMedia(MediaFeedData media) throws IOException {
        removeAll();
        final Dimension2D screenDimensions = getScreenDimension();
        final LayoutMetrics metrics = new LayoutMetrics(screenDimensions);
        final JLabel image = getMainPicture(media.getImages().getStandardResolution().getImageUrl(), metrics);
        final JLabel caption = getCaption(media.getCaption(), metrics);
        final JLabel uploaderName = getUploaderName(media.getUser(), metrics);
        final JLabel uploderProfilePicture = getUploaderProfilePicture(media.getUser().getProfilePictureUrl(), metrics);
        final List<JLabel> comment = getComments(media.getComments(), metrics, screenDimensions);
        final List<JLabel> commenterImage = getCommenterImages(media.getComments(), metrics, screenDimensions);

        final JLayeredPane layeredPhotoPanel = createPanel(image,caption,uploaderName,uploderProfilePicture,commenterImage,comment);
        add(layeredPhotoPanel, "layeredPhotoPanel");

        revalidate();
        repaint();

    }

    private final JLayeredPane createPanel(JLabel image,JLabel caption,JLabel uploaderName, JLabel uploaderProfilePicture, List <JLabel> commenterImage, List<JLabel> comment) {

        JLayeredPane layeredPhotoPanel = new JLayeredPane();
        layeredPhotoPanel.setBackground(Color.BLACK);

        layeredPhotoPanel.setLayer(image, 1);
        layeredPhotoPanel.add(image);

        layeredPhotoPanel.setLayer(caption, 1);
        layeredPhotoPanel.add(caption);

        layeredPhotoPanel.setLayer(uploaderProfilePicture, 1);
        layeredPhotoPanel.add(uploaderProfilePicture);

        layeredPhotoPanel.setLayer(uploaderName, 1);
        layeredPhotoPanel.add(uploaderName);

        for(int i=0;i<commenterImage.size();i++){

            layeredPhotoPanel.setLayer(commenterImage.get(i), 1);
            layeredPhotoPanel.add(commenterImage.get(i));

            layeredPhotoPanel.setLayer(comment.get(i), 1);
            layeredPhotoPanel.add(comment.get(i));

        }

        return layeredPhotoPanel;

    }
    
    private Dimension2D getScreenDimension(){
        return Toolkit.getDefaultToolkit().getScreenSize();
    }


   private JLabel getUploaderProfilePicture(String uploaderUrl, LayoutMetrics metrics) throws IOException {

        JLabel uploaderImage = new JLabel();

        uploaderImage.setBounds((metrics.getUploaderImageMetrics()));

        uploaderImage.setIcon(getImageIcon(
                uploaderUrl,
                metrics.getUploaderImageMetrics().width,
                (int) metrics.getUploaderImageMetrics().height
        ));

        return uploaderImage;

    }

    private JLabel getCaption(Caption captionInfo, LayoutMetrics metrics){

        JLabel caption = new JLabel();

        caption.setForeground(Color.ORANGE);
        caption.setFont(new Font("Tahoma", Font.PLAIN, 50));

        caption.setText(captionInfo.getText());
        caption.setBounds(metrics.getCaptionMetrics());

        return caption;

    }

    private JLabel getUploaderName(User user, LayoutMetrics metrics){

        JLabel uploaderName = new JLabel();
        uploaderName.setHorizontalAlignment(SwingConstants.CENTER);
        uploaderName.setFont(new Font("Tahoma", Font.PLAIN, 30));
        uploaderName.setForeground(Color.ORANGE);


        uploaderName.setText(user.getUserName());
        uploaderName.setBounds(metrics.getUploaderNameMetrics());

        return uploaderName;
    }


    private JLabel getMainPicture(String imagePath, LayoutMetrics metrics) throws IOException {

        JLabel pictureLabel = new JLabel();

        pictureLabel.setBounds(metrics.getImageMetrics());

                pictureLabel.setIcon(getImageIcon(
                        imagePath,
                        metrics.getImageMetrics().width,
                        metrics.getImageMetrics().height
                ));

        return pictureLabel;

    }

    private List<JLabel> getComments(Comments commentsData, LayoutMetrics metrics, Dimension2D screenDimensions){

        List<JLabel>comment = new LinkedList<JLabel>();

        int startPositionY = (int) metrics.getCommenterImageMetrics().getY();

        for(int i = 0;i<commentsData.getComments().size();i++) {

            comment.add(new JLabel(commentsData.getComments().get(i).getText()));

            comment.get(i).setForeground(Color.ORANGE);
            comment.get(i).setFont(new Font("Tahoma", Font.PLAIN, 15));
            comment.get(i).setBounds(
                    metrics.getCommentMetrics().x,
                    startPositionY - (int) screenDimensions.getHeight() / 30,
                    metrics.getCommentMetrics().width,
                    metrics.getCommentMetrics().height);

            startPositionY += screenDimensions.getHeight() / 15;

        }

        return comment;

    }

    private List<JLabel> getCommenterImages(Comments commentsData, LayoutMetrics metrics, Dimension2D screenDimensions) throws IOException {

        Rectangle bounds = metrics.getCommenterImageMetrics();

        Set<URL> list = new HashSet<URL>();

        for(CommentData comment : commentsData.getComments()){
            list.add(new URL(comment.getCommentFrom().getProfilePicture()));
        }

        Map<URL,Image> images = MediaRepository.getImages(list, bounds.x , bounds.y);

        List <JLabel> commenterImages = new LinkedList<JLabel>();
        int startPositionY = (int)metrics.getCommenterImageMetrics().getY();

        for(CommentData comment : commentsData.getComments()){

            JLabel label = new JLabel();
            label.setIcon(getImageIcon(comment.getCommentFrom().getProfilePicture(), 100, 100));

            label.setBounds(metrics.getCommenterImageMetrics().x,
                    startPositionY, metrics.getCommenterImageMetrics().width,
                    metrics.getCommenterImageMetrics().height);

            commenterImages.add(label);
            startPositionY+=screenDimensions.getHeight() / 15;

        }

        return commenterImages;

    }

    private ImageIcon getImageIcon(String url, int width, int height) throws IOException{

        final Proxy proxy = new ApplicationProxyProvider().getApplicationProxy();
        URLConnection urlConnection = new URL(url).openConnection(proxy);
        urlConnection.setReadTimeout(2000);
        final InputStream inStream = urlConnection.getInputStream();

        return new ImageIcon(ImageIO.read(inStream).getScaledInstance(width, height, Image.SCALE_SMOOTH));

    }

    public void displayError(String message) {

    }

    private Map<URL,ImageIcon> getMap(List<URL> list) throws IOException {

        Map<URL,ImageIcon> map = new HashMap<URL,ImageIcon>();

        for(int i=0;i<list.size();i++){

            map.put(list.get(i),getImageIcon(list.get(i).getPath(), 100, 100));

        }

        return map;

    }

}
