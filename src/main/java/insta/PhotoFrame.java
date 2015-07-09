package insta;

import org.jinstagram.entity.common.Caption;
import org.jinstagram.entity.common.Comments;
import org.jinstagram.entity.common.User;
import org.jinstagram.entity.users.feed.MediaFeedData;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Dimension2D;
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
        Dimension2D screenDimensions = getScreenDimension();

        setLayout(new CardLayout(0, 0));
        setBounds(0, 0, (int) screenDimensions.getWidth(), (int) screenDimensions.getHeight());
        setBackground(Color.BLACK);
        setVisible(true);
    }

    public void updateMedia(MediaFeedData media) throws IOException {
        removeAll();
        LayoutMetrics metrics = new LayoutMetrics();
        Dimension2D screenDimensions = getScreenDimension();

        JLabel image = getMainPicture(media.getImages().getStandardResolution().getImageUrl(), metrics, screenDimensions);
        JLabel caption = getCaption(media.getCaption(), metrics, screenDimensions);
        JLabel uploaderName = getUploaderName(media.getUser(), metrics, screenDimensions);
        JLabel uploderProfilePicture = getUploaderProfilePicture(media.getUser().getProfilePictureUrl(), metrics, screenDimensions);
        List<JLabel> comment = getComments(media.getComments(), metrics, screenDimensions);
        List<JLabel> commenterImage = getCommenterImages(media.getComments(), metrics, screenDimensions);

        JLayeredPane layeredPhotoPanel = createPanel(image,caption,uploaderName,uploderProfilePicture,commenterImage,comment);
        add(layeredPhotoPanel, "layeredPhotoPanel");

        revalidate();
        repaint();

    }

    JLayeredPane createPanel(JLabel image,JLabel caption,JLabel uploaderName, JLabel uploaderProfilePicture, List <JLabel> commenterImage, List<JLabel> comment) {

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
    
    Dimension2D getScreenDimension(){
        return Toolkit.getDefaultToolkit().getScreenSize();
    }


    JLabel getUploaderProfilePicture(String uploaderUrl, LayoutMetrics metrics, Dimension2D screenDimensions) throws IOException {

        JLabel uploaderImage = new JLabel();

        uploaderImage.setBounds((metrics.getUploaderImageMetrics(screenDimensions)));

        uploaderImage.setIcon(getImageIcon(
                uploaderUrl,
                (int) metrics.getUploaderImageMetrics(screenDimensions).getWidth(),
                (int) metrics.getUploaderImageMetrics(screenDimensions).getHeight()
        ));

        return uploaderImage;

    }

    JLabel getCaption(Caption captionInfo, LayoutMetrics metrics, Dimension2D screenDimensions){

        JLabel caption = new JLabel();

        caption.setForeground(Color.ORANGE);
        caption.setFont(new Font("Tahoma", Font.PLAIN, 50));

        caption.setText(captionInfo.getText());
        caption.setBounds(metrics.getCaptionMetrics(screenDimensions));

        return caption;

    }

    JLabel getUploaderName(User user, LayoutMetrics metrics, Dimension2D screenDimensions){

        JLabel uploaderName = new JLabel();
        uploaderName.setHorizontalAlignment(SwingConstants.CENTER);
        uploaderName.setFont(new Font("Tahoma", Font.PLAIN, 30));
        uploaderName.setForeground(Color.ORANGE);


        uploaderName.setText(user.getUserName());
        uploaderName.setBounds(metrics.getUploaderNameMetrics(screenDimensions));

        return uploaderName;
    }


    JLabel getMainPicture(String imagePath, LayoutMetrics metrics, Dimension2D screenDimensions) throws IOException {

        JLabel pictureLabel = new JLabel();

        pictureLabel.setBounds(metrics.getImageMetrics(screenDimensions));

                pictureLabel.setIcon(getImageIcon(
                        imagePath,
                        (int) metrics.getImageMetrics(screenDimensions).getWidth(),
                        (int) metrics.getImageMetrics(screenDimensions).getHeight()
                ));

        return pictureLabel;

    }

    List<JLabel> getComments(Comments commentsData, LayoutMetrics metrics, Dimension2D screenDimensions){

        //JLabel comment[] = new JLabel[commentsData.getComments().size()];
        List<JLabel>comment = new LinkedList<JLabel>();

        int startPositionY = (int) metrics.getCommenterImageMetrics(screenDimensions).getY();

        for(int i = 0;i<commentsData.getComments().size();i++) {

            comment.add(new JLabel(commentsData.getComments().get(i).getText()));

            comment.get(i).setForeground(Color.ORANGE);
            comment.get(i).setFont(new Font("Tahoma", Font.PLAIN, 15));
            comment.get(i).setBounds((int) metrics.getCommentMetrics(screenDimensions).getX(), startPositionY - (int) screenDimensions.getHeight() / 30, (int) metrics.getCommentMetrics(screenDimensions).getWidth(), (int) metrics.getCommentMetrics(screenDimensions).getHeight());

            startPositionY += screenDimensions.getHeight() / 15;

        }

        return comment;

    }

    List<JLabel> getCommenterImages(Comments commentsData, LayoutMetrics metrics, Dimension2D screenDimensions) throws IOException {

        /*List<URL> list = new LinkedList<URL>();

        for(int i=0;i<commentsData.getComments().size();i++){
            list.add(new URL(commentsData.getComments().get(i).getCommentFrom().getProfilePicture()));
        }

        Map<URL,ImageIcon> map = getMap(list);*/

        List <JLabel> commenterImages = new LinkedList<JLabel>();
        int startPositionY = (int)metrics.getCommenterImageMetrics(screenDimensions).getY();

        for(int i=0;i<commentsData.getComments().size();i++){

            commenterImages.add(new JLabel());
            commenterImages.get(i).setIcon(getImageIcon(commentsData.getComments().get(i).getCommentFrom().getProfilePicture(),100,100));

            commenterImages.get(i).setBounds((int) metrics.getCommenterImageMetrics(screenDimensions).getX(),
                    startPositionY, (int) metrics.getCommenterImageMetrics(screenDimensions).getWidth(),
                    (int) metrics.getCommenterImageMetrics(screenDimensions).getHeight());

            startPositionY+=screenDimensions.getHeight() / 15;

        }

        return commenterImages;

    }

    private ImageIcon getImageIcon(String url, int width, int height) throws IOException{

        ApplicationProxyProvider conProxy = new ApplicationProxyProvider();
        Proxy proxy = conProxy.getApplicationProxy();

        URLConnection urlConnection = new URL(url).openConnection(proxy);
        urlConnection.setReadTimeout(2000);
        InputStream inStream = urlConnection.getInputStream();

        ImageIcon imageIcon = new ImageIcon(ImageIO.read(inStream).getScaledInstance(width, height, Image.SCALE_SMOOTH));

        return imageIcon;

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
