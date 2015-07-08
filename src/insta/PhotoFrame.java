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

/**
 * Created by mantttttas on 2015-07-01.
 */
public class PhotoFrame {

    private JLayeredPane layeredPhotoPanel;

    JPanel photoPanel;

    public PhotoFrame(MediaFeedData media) throws IOException {

        LayoutMetrics metrics = new LayoutMetrics();

        Dimension2D screenDimensions = screenDimensionsToObject();
        
        initializePhotoPanel(screenDimensions);
        initializeLayeredPane();
        initializeBackground(screenDimensions);
        setMainPicture(media.getImages().getStandardResolution().getImageUrl(),metrics,screenDimensions);
        setCaption(media.getCaption(),metrics,screenDimensions);
        setUploaderName(media.getUser(),metrics,screenDimensions);
        setUploaderProfilePicture(media.getUser().getProfilePictureUrl(),metrics,screenDimensions);
        setComments(media.getComments(),metrics,screenDimensions);

    }
    
    Dimension2D screenDimensionsToObject(){
        return Toolkit.getDefaultToolkit().getScreenSize();
        
    }

    public JPanel getCompletePhotoPanel(){

        return photoPanel;

    }

    void setUploaderProfilePicture(String uploaderUrl, LayoutMetrics metrics, Dimension2D screenDimensions) throws IOException {

        JLabel uploaderImageLabel = new JLabel();
        layeredPhotoPanel.setLayer(uploaderImageLabel, 1);

        uploaderImageLabel.setBounds((metrics.getUploaderImageMetrics(screenDimensions)));

        URL userUrl = new URL(uploaderUrl);

        if(System.getProperty("proxy")!=null) {
            AuthenticationProxy conProxy = new AuthenticationProxy();
            Proxy proxy = conProxy.getProxy();

            URLConnection urlConnection = userUrl.openConnection(proxy);
            InputStream inStream = urlConnection.getInputStream();

            Image image = ImageIO.read(inStream).getScaledInstance((int) metrics.getUploaderImageMetrics(screenDimensions).getWidth(), (int) metrics.getUploaderImageMetrics(screenDimensions).getHeight(), Image.SCALE_SMOOTH);
            uploaderImageLabel.setIcon(new ImageIcon(image));
        } else uploaderImageLabel.setIcon(new ImageIcon(ImageIO.read(userUrl).getScaledInstance((int) metrics.getUploaderImageMetrics(screenDimensions).getWidth(), (int) metrics.getUploaderImageMetrics(screenDimensions).getHeight(), Image.SCALE_SMOOTH)));



        layeredPhotoPanel.add(uploaderImageLabel);

    }

    void setCaption(Caption caption, LayoutMetrics metrics, Dimension2D screenDimensions){

        JLabel captionLabel = new JLabel();
        layeredPhotoPanel.setLayer(captionLabel, 1);

        captionLabel.setForeground(Color.ORANGE);
        captionLabel.setFont(new Font("Tahoma", Font.PLAIN, 50));

        captionLabel.setText(caption.getText());
        captionLabel.setBounds(metrics.getCaptionMetrics(screenDimensions));
        layeredPhotoPanel.add(captionLabel);

    }

    void setUploaderName(User user, LayoutMetrics metrics, Dimension2D screenDimensions){

        JLabel uploaderNameLabel = new JLabel();
        uploaderNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        uploaderNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        uploaderNameLabel.setForeground(Color.ORANGE);
        layeredPhotoPanel.setLayer(uploaderNameLabel, 1);

        uploaderNameLabel.setText(user.getUserName());
        uploaderNameLabel.setBounds(metrics.getUploaderNameMetrics(screenDimensions));
        layeredPhotoPanel.add(uploaderNameLabel);

    }


    void setMainPicture(String imagePath, LayoutMetrics metrics, Dimension2D screenDimensions) throws IOException {

        JLabel pictureLabel = new JLabel();
        layeredPhotoPanel.setLayer(pictureLabel, 1);

        URL url = new URL(imagePath);
        pictureLabel.setBounds(metrics.getImageMetrics(screenDimensions));

        if(System.getProperty("proxy")!=null) {

            AuthenticationProxy proxy = new AuthenticationProxy();
            Proxy conProxy = proxy.getProxy();
            URLConnection connection = url.openConnection(conProxy);
            InputStream inStream = connection.getInputStream();
            try {
                pictureLabel.setIcon(new ImageIcon(

                        ImageIO.read(inStream).getScaledInstance((int)metrics.getImageMetrics(screenDimensions).getWidth(),(int)metrics.getImageMetrics(screenDimensions).getHeight(), Image.SCALE_SMOOTH)));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        else pictureLabel.setIcon(new ImageIcon(ImageIO.read(url).getScaledInstance((int)metrics.getImageMetrics(screenDimensions).getWidth(), (int)metrics.getImageMetrics(screenDimensions).getHeight(), Image.SCALE_SMOOTH)));

        layeredPhotoPanel.add(pictureLabel);

    }

    void initializeLayeredPane(){

        layeredPhotoPanel = new JLayeredPane();
        photoPanel.add(layeredPhotoPanel, "layeredPhotoPanel");

    }

    void initializePhotoPanel(Dimension2D screenDimensions){

        photoPanel = new JPanel();
        photoPanel.setLayout(new CardLayout(0, 0));
        photoPanel.setBounds(0, 0, (int)screenDimensions.getWidth(), (int)screenDimensions.getHeight());
        photoPanel.setVisible(true);

    }

   void initializeBackground(Dimension2D screenDimensions){

       JLabel backgroundLabel = new JLabel();
       layeredPhotoPanel.setLayer(backgroundLabel, 0);
       backgroundLabel.setBounds(0, 0, (int)screenDimensions.getWidth(), (int)screenDimensions.getHeight());

       backgroundLabel.setOpaque(true);
       backgroundLabel.setBackground(Color.BLACK);

       layeredPhotoPanel.add(backgroundLabel);

   }

    void setComments(Comments comments, LayoutMetrics metrics, Dimension2D screenDimensions) throws IOException{

        int commentsCount = comments.getCount();
        int startPositionY = (int)metrics.getCommenterImageMetrics(screenDimensions).getY();

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

                    Image image = ImageIO.read(inStream).getScaledInstance((int) metrics.getCommenterImageMetrics(screenDimensions).getWidth(), (int) metrics.getCommenterImageMetrics(screenDimensions).getHeight(), Image.SCALE_SMOOTH);

                    commenterImage[i].setIcon(new ImageIcon(image));
                } else commenterImage[i].setIcon(new ImageIcon(ImageIO.read(commenterPictureUrl).getScaledInstance((int) metrics.getCommenterImageMetrics(screenDimensions).getWidth(), (int) metrics.getCommenterImageMetrics(screenDimensions).getHeight(), Image.SCALE_SMOOTH)));

                commenterImage[i].setBounds((int) metrics.getCommenterImageMetrics(screenDimensions).getX(), startPositionY, (int) metrics.getCommenterImageMetrics(screenDimensions).getWidth(), (int) metrics.getCommenterImageMetrics(screenDimensions).getHeight());
                layeredPhotoPanel.add(commenterImage[i]);

                comment[i].setText(comments.getComments().get(i).getText());
                comment[i].setForeground(Color.ORANGE);
                comment[i].setFont(new Font("Tahoma", Font.PLAIN, 15));
                comment[i].setBounds((int) metrics.getCommentMetrics(screenDimensions).getX(), startPositionY - (int) screenDimensions.getHeight() / 30, (int) metrics.getCommentMetrics(screenDimensions).getWidth(), (int) metrics.getCommentMetrics(screenDimensions).getHeight());
                layeredPhotoPanel.setLayer(comment[i], 1);
                layeredPhotoPanel.add(comment[i]);

                startPositionY+=screenDimensions.getHeight() / 15;

            }

        }

    }

}
