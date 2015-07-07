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

        final LayoutMetrics metrics = initializeMetrics();
        initializeMainWindow(metrics);
        loadLoginScreen(metrics);
        launchSlideshowRoutine(metrics);

    }



    LayoutMetrics initializeMetrics(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return new LayoutMetrics((int)screenSize.getWidth(),(int)screenSize.getHeight());

    }


    void initializeMainWindow(LayoutMetrics metrics){

        mainWindow = new JFrame();
        mainWindow.setBounds(0, 0, metrics.screenWidth, metrics.screenHeight);
        mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainWindow.setUndecorated(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    void loadLoginScreen(LayoutMetrics metrics) throws IOException {

        loginScreen = new LoginScreen(metrics.screenWidth,metrics.screenHeight);

        mainWindow.getContentPane().setLayout(new CardLayout(0, 0));
        mainWindow.getContentPane().add(loginScreen.getInitializedLoginPanel(), "name_123");

        mainWindow.setVisible(true);

    }

    void launchSlideshowRoutine(final LayoutMetrics metrics){

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
                        loginScreen.showFieldWithErrorMessage((int)metrics.screenWidth,(int)metrics.screenHeight,e1.toString());
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
