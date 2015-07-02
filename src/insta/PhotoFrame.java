package insta;

import org.jinstagram.entity.comments.CommentData;
import org.jinstagram.entity.common.Images;
import sun.plugin.dom.css.Rect;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by mantttttas on 2015-07-01.
 */
public class PhotoFrame {

    JLayeredPane layeredPhotoPanel;

    JPanel photoPanel;

    JLabel pictureLabel;
    JLabel captionLabel;
    JLabel uploaderImageLabel;
    JLabel uploaderNameLabel;
    JLabel commenterProfilePictureLabel;
    JLabel commentLabel;
    JLabel backgroundLabel;

    Image mainImage;
    String caption;
    Image uploaderImage;
    String uploaderName;
    JLabel [] commenterImage;
    JLabel [] comment;

    java.util.List<CommentData> commentsData;

    LayoutMetrics metrics;

    int width,height,counter;

    boolean useProxy;

    public PhotoFrame(boolean useProxy, int counter) throws IOException {

        this.useProxy = useProxy;
        this.counter = counter;

        initializeMetrics();
        initializeLayeredPane();
        initializeBackground();
        initializeMediaFields();



    }

    JPanel returnCompletePhotoFrame(){

        return photoPanel;

    }

    void setMainPicture(){

        pictureLabel = new JLabel();

        Point p = getMaximumImageSize();

        metrics.imageBounds.width = p.x;
        metrics.imageBounds.height = p.y;

        int centeredY = (int)(height / 2 - metrics.imageBounds.getHeight() / 2);

        pictureLabel.setIcon(new ImageIcon(mainImage.getScaledInstance((int) p.x, p.y, Image.SCALE_SMOOTH)));

        pictureLabel.setBounds((int) metrics.imageBounds.getX(), centeredY, p.x, p.y);

        layeredPhotoPanel.add(pictureLabel);

    }

    void initializeMetrics(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int widthDouble = (int)screenSize.getWidth();
        int heightDouble = (int)screenSize.getHeight();

        metrics = new LayoutMetrics(width,height);

    }

    void initializeLayeredPane(){

        layeredPhotoPanel = new JLayeredPane();
        photoPanel.add(layeredPhotoPanel, "layeredPhotoPanel");

    }

    void initializePhotoPanel(){

        photoPanel = new JPanel();
        photoPanel.setLayout(new CardLayout(0, 0));
        photoPanel.setBounds(0, 0, width, height);
        photoPanel.setVisible(true);

    }

   void initializeBackground(){



       backgroundLabel = new JLabel();
       layeredPhotoPanel.setLayer(backgroundLabel, 0);
       backgroundLabel.setBounds(0, 0, width, height);

       backgroundLabel.setOpaque(true);
       backgroundLabel.setBackground(Color.BLACK);

       layeredPhotoPanel.add(backgroundLabel);

   }

    String getPictureUrl(Images inputData) throws IOException{

        String html="";
        String input = inputData.getStandardResolution().toString();

        int index = input.indexOf("imageUrl") + 9;

        while(input.charAt(index) != ','){

            html+=input.charAt(index);
            index++;

        }

        return html;

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

    Point getMaximumImageSize(){

        int endX = (int)(width / 2 - metrics.imageBounds.getX() * 2);
        int endY = (int)(height - metrics.imageBounds.getY() * 2);

        int maxX = 0;
        int maxY = 0;

        while(maxX < endX && maxY < endY){

            maxX++;
            maxY++;

        }

        return new Point(maxX,maxY);

    }

    void setComments(int pos) throws IOException{

        int commentsCount = commentsData.size();
        int startPositionY = (int)metrics.commenterImageBounds.getY();

        if(commentsCount > 0){

            commenterImage = new JLabel[commentsCount];
            comment = new JLabel[commentsCount];

            for(int i = 0;i<commentsCount;i++){

                commenterImage[i] = new JLabel("");
                comment[i] = new JLabel("");
                layeredPhotoPanel.setLayer(commenterImage[i], 1);

                URL commenterPictureUrl = new URL(commentsData.get(i).getCommentFrom().getProfilePicture());

                if(useProxy) {

                    ConnectionProxy conProxy = new ConnectionProxy();
                    Proxy proxy = conProxy.getProxy();

                    URLConnection urlConnection = commenterPictureUrl.openConnection(proxy);
                    InputStream inStream = urlConnection.getInputStream();

                    Image image = ImageIO.read(inStream).getScaledInstance((int) metrics.commenterImageBounds.getWidth(), (int) metrics.commenterImageBounds.getHeight(), Image.SCALE_SMOOTH);

                    commenterImage[i].setIcon(new ImageIcon(image));

                } else commenterImage[i].setIcon(new ImageIcon(ImageIO.read(commenterPictureUrl).getScaledInstance((int)metrics.commenterImageBounds.getWidth(),(int)metrics.commenterImageBounds.getHeight(),Image.SCALE_SMOOTH)));

                commenterImage[i].setBounds((int)metrics.commenterImageBounds.getX(), startPositionY, (int)metrics.commenterImageBounds.getWidth(), (int)metrics.commenterImageBounds.getHeight());
                layeredPhotoPanel.add(commenterImage[i]);

                comment[i].setText(commentsData.get(i).getText());
                comment[i].setForeground(Color.ORANGE);
                comment[i].setFont(new Font("Tahoma", Font.PLAIN, 15));
                comment[i].setBounds((int)metrics.commentBounds.getX(), startPositionY, (int)metrics.commentBounds.getWidth(), (int)metrics.commentBounds.getHeight());
                layeredPhotoPanel.setLayer(comment[i], 1);
                layeredPhotoPanel.add(comment[i]);

                startPositionY+=70;

            }

        }

    }

}
