package insta;

import org.jinstagram.exceptions.InstagramException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.io.IOException;
import java.util.Timer;


/**
 * Created by mantttttas on 2015-07-01.
 */
public class WindowManager {


    public WindowManager() throws IOException {

        Dimension2D screenDimensions = screenDimensionsToObject();
        LoginScreen loginScreen = new LoginScreen(screenDimensions);
        System.out.println(loginScreen.getInitializedLoginPanel().toString());
        JFrame mainWindow = initializeMainWindow(screenDimensions, loginScreen);
        launchSlideshowRoutine(screenDimensions, mainWindow, loginScreen);

    }

    Dimension2D screenDimensionsToObject() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    JFrame initializeMainWindow(Dimension2D screenDimensions, LoginScreen loginScreen) {

        JFrame mainWindow = new JFrame();
        mainWindow.setBounds(0, 0, (int) screenDimensions.getWidth(), (int) screenDimensions.getHeight());
        mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainWindow.setUndecorated(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.getContentPane().setLayout(new CardLayout(0, 0));
        mainWindow.getContentPane().add(loginScreen.getInitializedLoginPanel(), "name_123");

        mainWindow.setVisible(true);

        return mainWindow;

    }

    void launchSlideshowRoutine(final Dimension2D screenDimensions, final JFrame mainWindow, final LoginScreen loginScreen) {

        final JButton loginScreenButton = loginScreen.getInitializetButton();

        loginScreenButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                JTextField textField = loginScreen.getInitializetTextField();
                JPanel photoPanel = loginScreen.getInitializedLoginPanel();

                if (textField.getText().toString().length() > 0) {

                    launchTimerTask(textField, photoPanel, mainWindow, loginScreen, screenDimensions);

                }

            }

        });

    }

    private void launchTimerTask(JTextField textField, JPanel photoPanel, JFrame mainWindow, LoginScreen loginScreen, Dimension2D screenDimensions) {
        try {
            ImageUpdate task = new ImageUpdate(mainWindow, photoPanel, textField.getText().toString());
            new Timer().schedule(task, 0, 3000);
        } catch (InstagramException e1) {
            loginScreen.showFieldWithErrorMessage(screenDimensions, e1.toString());
            mainWindow.setVisible(true);
        }
    }

}
