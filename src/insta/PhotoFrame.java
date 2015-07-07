package insta;

import com.sun.java.util.jar.pack.Attribute;
import org.jinstagram.entity.common.Caption;
import org.jinstagram.entity.common.Comments;
import org.jinstagram.entity.common.User;
import org.jinstagram.entity.users.feed.MediaFeedData;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by mantttttas on 2015-07-01.
 */
public class PhotoFrame {

    private JLayeredPane layeredPhotoPanel;

    JPanel photoPanel;

    private JLabel pictureLabel;
    private JLabel captionLabel;
    private JLabel uploaderImageLabel;
    private JLabel uploaderNameLabel;
    private JLabel backgroundLabel;

    private MediaFeedData media;

    public PhotoFrame(MediaFeedData media) throws IOException {

        this.media = media;
        LayoutMetrics metrics = initializeMetrics();
        initializePhotoPanel(metrics);
        initializeLayeredPane();
        initializeMediaFields();
        initializeBackground(metrics);
        setMainPicture(media.getImages().getStandardResolution().getImageUrl(),metrics);
        setCaption(media.getCaption(),metrics);
        setUploaderName(media.getUser(),metrics);
        setUploaderProfilePicture(media.getUser().getProfilePictureUrl(),metrics);
        setComments(media.getComments(),metrics);

    }

    public JPanel getCompletePhotoPanel(){

        return photoPanel;

    }

    void setUploaderProfilePicture(String uploaderUrl, LayoutMetrics metrics) throws IOException {

        uploaderImageLabel.setBounds(metrics.uploaderImageBounds);

        URL userUrl = new URL(uploaderUrl);

        if(System.getProperty("proxy")!=null) {
            AuthenticationProxy conProxy = new AuthenticationProxy();
            Proxy proxy = conProxy.getProxy();

            URLConnection urlConnection = userUrl.openConnection(proxy);
            InputStream inStream = urlConnection.getInputStream();

            Image image = ImageIO.read(inStream).getScaledInstance((int) metrics.uploaderImageBounds.getWidth(), (int) metrics.uploaderImageBounds.getHeight(), Image.SCALE_SMOOTH);
            uploaderImageLabel.setIcon(new ImageIcon(image));
        } else uploaderImageLabel.setIcon(new ImageIcon(ImageIO.read(userUrl).getScaledInstance((int) metrics.uploaderImageBounds.getWidth(), (int) metrics.uploaderImageBounds.getHeight(), Image.SCALE_SMOOTH)));



        layeredPhotoPanel.add(uploaderImageLabel);

    }

    void setCaption(Caption caption, LayoutMetrics metrics){

        captionLabel.setText(caption.getText());
        captionLabel.setBounds(metrics.captionBounds);
        layeredPhotoPanel.add(captionLabel);

    }

    void setUploaderName(User user, LayoutMetrics metrics){

        uploaderNameLabel.setText(user.getUserName());
        uploaderNameLabel.setBounds(metrics.uploaderNameBounds);
        layeredPhotoPanel.add(uploaderNameLabel);

    }


    void setMainPicture(String imagePath, LayoutMetrics metrics) throws IOException {

        URL url = new URL(imagePath);
        pictureLabel.setBounds(metrics.imageBounds);

        if(System.getProperty("proxy")!=null) {

            AuthenticationProxy proxy = new AuthenticationProxy();
            Proxy conProxy = proxy.getProxy();
            URLConnection connection = url.openConnection(conProxy);
            InputStream inStream = connection.getInputStream();
            try {
                pictureLabel.setIcon(new ImageIcon(

                        ImageIO.read(inStream).getScaledInstance((int)metrics.imageBounds.getWidth(),(int)metrics.imageBounds.getHeight(), Image.SCALE_SMOOTH)));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        else pictureLabel.setIcon(new ImageIcon(ImageIO.read(url).getScaledInstance((int)metrics.imageBounds.getWidth(), (int)metrics.imageBounds.getHeight(), Image.SCALE_SMOOTH)));

        layeredPhotoPanel.add(pictureLabel);

    }

    LayoutMetrics initializeMetrics(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();

        return new LayoutMetrics(width,height);

    }

    void initializeLayeredPane(){

        layeredPhotoPanel = new JLayeredPane();
        photoPanel.add(layeredPhotoPanel, "layeredPhotoPanel");

    }

    void initializePhotoPanel(LayoutMetrics metrics){

        photoPanel = new JPanel();
        photoPanel.setLayout(new CardLayout(0, 0));
        photoPanel.setBounds(0, 0, metrics.screenWidth, metrics.screenHeight);
        photoPanel.setVisible(true);

    }

   void initializeBackground(LayoutMetrics metrics){



       backgroundLabel = new JLabel();
       layeredPhotoPanel.setLayer(backgroundLabel, 0);
       backgroundLabel.setBounds(0, 0, metrics.screenWidth, metrics.screenHeight);

       backgroundLabel.setOpaque(true);
       backgroundLabel.setBackground(Color.BLACK);

       layeredPhotoPanel.add(backgroundLabel);

   }

    void initializeMediaFields(){

        pictureLabel = new JLabel();
        layeredPhotoPanel.setLayer(pictureLabel, 1);

        captionLabel = new JLabel();
        layeredPhotoPanel.setLayer(captionLabel, 1);

        captionLabel.setForeground(Color.ORANGE);
        captionLabel.setFont(new Font("Tahoma", Font.PLAIN, 50));

        uploaderImageLabel = new JLabel();
        layeredPhotoPanel.setLayer(uploaderImageLabel, 1);

        uploaderNameLabel = new JLabel();
        uploaderNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        uploaderNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        uploaderNameLabel.setForeground(Color.ORANGE);
        layeredPhotoPanel.setLayer(uploaderNameLabel, 1);

    }

    void setComments(Comments comments, LayoutMetrics metrics) throws IOException{

        int commentsCount = comments.getCount();
        int startPositionY = (int)metrics.commenterImageBounds.getY();

        if(commentsCount > 0){

            JLabel commenterImage[] = new JLabel[commentsCount];
            JLabel comment[] = new JLabel[commentsCount];

            for(int i = 0;i<commentsCount;i++){

                commenterImage[i] = new JLabel("");
                comment[i] = new JLabel("");
                layeredPhotoPanel.setLayer(commenterImage[i], 1);

                URL commenterPictureUrl = new URL(comments.getComments().get(i).getCommentFrom().getProfilePicture());

                if(System.getProperty("proxy")!=null) {
                    AuthenticationProxy conProxy = new AuthenticationProxy();
                    Proxy proxy = conProxy.getProxy();

                    URLConnection urlConnection = commenterPictureUrl.openConnection(proxy);
                    InputStream inStream = urlConnection.getInputStream();

                    Image image = ImageIO.read(inStream).getScaledInstance((int) metrics.commenterImageBounds.getWidth(), (int) metrics.commenterImageBounds.getHeight(), Image.SCALE_SMOOTH);

                    commenterImage[i].setIcon(new ImageIcon(image));
                } else commenterImage[i].setIcon(new ImageIcon(ImageIO.read(commenterPictureUrl).getScaledInstance((int) metrics.commenterImageBounds.getWidth(), (int) metrics.commenterImageBounds.getHeight(), Image.SCALE_SMOOTH)));

                commenterImage[i].setBounds((int)metrics.commenterImageBounds.getX(), startPositionY, (int)metrics.commenterImageBounds.getWidth(), (int)metrics.commenterImageBounds.getHeight());
                layeredPhotoPanel.add(commenterImage[i]);

                comment[i].setText(comments.getComments().get(i).getText());
                comment[i].setForeground(Color.ORANGE);
                comment[i].setFont(new Font("Tahoma", Font.PLAIN, 15));
                comment[i].setBounds((int)metrics.commentBounds.getX(), startPositionY - metrics.screenHeight / 30, (int)metrics.commentBounds.getWidth(), (int)metrics.commentBounds.getHeight());
                layeredPhotoPanel.setLayer(comment[i], 1);
                layeredPhotoPanel.add(comment[i]);

                startPositionY+=metrics.screenHeight / 15;

            }

        }

    }

}
