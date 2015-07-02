package insta;

import org.jinstagram.entity.common.Caption;
import org.jinstagram.entity.common.Comments;
import org.jinstagram.entity.common.Images;
import org.jinstagram.entity.common.User;
import org.jinstagram.exceptions.InstagramException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by mantttttas on 2015-07-01.
 */
public class WindowManager {

    JFrame mainWindow;

    ArrayList<Images> imagesList;
    ArrayList<Caption> captionsList;
    ArrayList<User> usersList;
    ArrayList<Comments> commentsList;

    InstagramFeedIterator receiver;

    String userId;

    LoginScreen loginScreen;

    LayoutMetrics metrics;

    boolean useProxy;

    int width, height;

    PhotoFrame frame;

    int counter;

    public WindowManager(boolean useProxy) throws IOException { ///

        this.useProxy = useProxy;

        initializeScreenDimensions();
        initializeMetrics();
        initializeMainWindow();
        loadLoginScreen();

    }

    void initializeMetrics(){

        metrics = new LayoutMetrics(width,height);

    }


    void initializeMainWindow(){

        mainWindow = new JFrame();
        mainWindow.setBounds(0, 0, width, height);
        mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //mainWindow.setUndecorated(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    void loadLoginScreen() throws IOException {

        loginScreen = new LoginScreen(width,height);

        mainWindow.getContentPane().setLayout(new CardLayout(0, 0));
        mainWindow.getContentPane().add(loginScreen.getInitializedLoginPanel(), "name_123");

        mainWindow.setVisible(true);

    }

    void initializeScreenDimensions(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Double widthDouble = screenSize.getWidth();
        Double heightDouble = screenSize.getHeight();

        height = heightDouble.intValue();
        width = widthDouble.intValue();

    }

    void launchSlideshowRoutine(){

        JButton loginScreenButton = loginScreen.getInitializetButton();

        loginScreenButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                JTextField textField = loginScreen.getInitializetTextField();

                if (textField.getText().toString().length() > 0) {

                    System.out.println("clicked");

                    counter = 0;

                    try {
                        initializeInstagramData(textField.getText().toString());
                    } catch (InstagramException e1) {
                        e1.printStackTrace();
                    }

                    initializeDataArrays();

                    try {
                        frame = new PhotoFrame(useProxy,counter);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    java.util.Timer timer = new java.util.Timer();
                    timer.schedule(new TimerTask() {

                        @Override
                        public void run() {

                            try {

                                frame.mainImage = parseImage(getPictureUrl(imagesList.get(counter)));
                                frame.caption = captionsList.get(counter).getText();
                                frame.uploaderImage = parseImage((usersList.get(counter).getProfilePictureUrl()));
                                frame.uploaderName = usersList.get(counter).getUserName();
                                frame.commentsData = commentsList.get(counter).getComments();
                                frame.useProxy = false;
                                frame.setMainPicture();
                                frame.setComments(counter);

                                mainWindow.getContentPane().setLayout(new CardLayout(0, 0));
                                mainWindow.getContentPane().add(frame.returnCompletePhotoFrame(), "name_28865865238250");
                                mainWindow.setVisible(true);

                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                        }

                    }, 0, 3000);

                }

            }

        });

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

    void initializeInstagramData(String userId) throws InstagramException {

       // receiver= new InstagramFeedIterator(userId);
       // receiver.getData();

    }

    void initializeDataArrays(){

      //  imagesList = receiver.getImages();
      //  captionsList = receiver.getCaptions();
       // usersList = receiver.getUsers();
       // commentsList = receiver.getComments();

    }

    Image parseImage(String url) throws IOException {

        Image image = null;
        URL connectionURL = new URL(url);

        Point p = getMaximumImageSize();

        if(useProxy){

            ConnectionProxy conProxy = new ConnectionProxy();
            Proxy proxy = conProxy.getProxy();

            URLConnection urlConnection = connectionURL.openConnection(proxy);
            InputStream inStream = urlConnection.getInputStream();

            image = ImageIO.read(inStream).getScaledInstance((int) p.x, p.y, Image.SCALE_SMOOTH);

        } else image = ImageIO.read(connectionURL).getScaledInstance((int) p.x, p.y, Image.SCALE_SMOOTH);

        return image;

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

}
