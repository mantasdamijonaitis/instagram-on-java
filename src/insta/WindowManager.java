package insta;
import org.jinstagram.exceptions.InstagramException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;


/**
 * Created by mantttttas on 2015-07-01.
 */
public class WindowManager {

    private JFrame mainWindow;
    private String userId;
    private InstagramFeedIterator iterator;
    private LoginScreen loginScreen;
    private LayoutMetrics metrics;
    private JPanel photoPanel;
    private JTextField textField;

    public WindowManager() throws IOException {

        initializeMetrics();
        initializeMainWindow();
        loadLoginScreen();
        launchSlideshowRoutine();

    }



    void initializeMetrics(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();

        metrics = new LayoutMetrics(width,height);

    }


    void initializeMainWindow(){

        mainWindow = new JFrame();
        mainWindow.setBounds(0, 0, metrics.screenWidth, metrics.screenHeight);
        mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainWindow.setUndecorated(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    void loadLoginScreen() throws IOException {

        loginScreen = new LoginScreen(metrics.screenWidth,metrics.screenHeight);

        mainWindow.getContentPane().setLayout(new CardLayout(0, 0));
        mainWindow.getContentPane().add(loginScreen.getInitializedLoginPanel(), "name_123");

        mainWindow.setVisible(true);

    }

    void launchSlideshowRoutine(){

        JButton loginScreenButton = loginScreen.getInitializetButton();

        loginScreenButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                textField = loginScreen.getInitializetTextField();


                if (textField.getText().toString().length() > 0) {

                    System.out.println("clicked");

                    try {
                        iterator = new InstagramFeedIterator(textField.getText().toString());
                    } catch (InstagramException e1) {
                        e1.printStackTrace();
                    }
                    java.util.Timer timer = new java.util.Timer();
                    timer.schedule(new TimerTask() {

                        @Override
                        public void run() {

                            try {
                                if (iterator.hasNext()) {
                                    photoPanel = new PhotoFrame(iterator.next()).getCompletePhotoPanel();
                                } else {
                                    iterator = new InstagramFeedIterator("testingnewestapp");
                                    photoPanel = new PhotoFrame(iterator.next()).getCompletePhotoPanel();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            mainWindow.getContentPane().removeAll();
                            mainWindow.getContentPane().add(photoPanel);

                            mainWindow.setVisible(true);

                        }

                    }, 0, 3000);

                }

            }

        });

    }

}
