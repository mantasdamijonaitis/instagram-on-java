package insta;
import org.jinstagram.exceptions.InstagramException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.Timer;


/**
 * Created by mantttttas on 2015-07-01.
 */
public class WindowManager {

    private JFrame mainWindow;
    private LoginScreen loginScreen;

    boolean toContinue;

    public WindowManager() throws IOException {

        LayoutMetrics metrics = new LayoutMetrics();
        Point screenDimensions = screenDimensionsToPoint();
        initializeMainWindow(screenDimensions);
        loadLoginScreen(screenDimensions);
        launchSlideshowRoutine(screenDimensions);

    }

    Point screenDimensionsToPoint(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        return new Point((int) screenSize.getWidth(), (int)screenSize.getHeight());

    }

    void initializeMainWindow(Point screenDimensions){

        mainWindow = new JFrame();
        mainWindow.setBounds(0, 0, (int)screenDimensions.getX(), (int)screenDimensions.getY());
        mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainWindow.setUndecorated(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    void loadLoginScreen(Point screenDimensions) throws IOException {

        loginScreen = new LoginScreen((int)screenDimensions.getX(),(int)screenDimensions.getY());

        mainWindow.getContentPane().setLayout(new CardLayout(0, 0));
        mainWindow.getContentPane().add(loginScreen.getInitializedLoginPanel(), "name_123");

        mainWindow.setVisible(true);

    }

    void launchSlideshowRoutine(final Point screenDimensions){

        toContinue = true;

        final JButton loginScreenButton = loginScreen.getInitializetButton();

        loginScreenButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                JTextField textField = loginScreen.getInitializetTextField();
                JPanel photoPanel = loginScreen.getInitializedLoginPanel();

                if (textField.getText().toString().length() > 0) {

                    Timer timer = new java.util.Timer();
                    RepeatableTask task = null;

                    try {
                        task = new RepeatableTask(mainWindow,photoPanel,textField.getText().toString());
                    } catch (InstagramException e1) {
                        loginScreen.showFieldWithErrorMessage((int) screenDimensions.getX(), (int) screenDimensions.getY(), e1.toString());
                        mainWindow.setVisible(true);
                        toContinue = false;
                    }

                    if(toContinue)
                    timer.schedule(task, 0, 3000);

                }

            }

        });

    }

}
