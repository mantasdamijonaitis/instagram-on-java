package insta;

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

    final JFrame mainWindow;

    public WindowManager() throws IOException {
        Dimension2D screenDimensions = getScreenDimensions();
        mainWindow = new JFrame();
        mainWindow.setBounds(0, 0, (int) screenDimensions.getWidth(), (int) screenDimensions.getHeight());
        //mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //mainWindow.setUndecorated(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.getContentPane().setLayout(new CardLayout(0, 0));

        mainWindow.setVisible(true);

    }

    Dimension2D getScreenDimensions() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public void showSearchView()  {

        Dimension2D screenDimensions = getScreenDimensions();
        final LoginScreen loginScreen = new LoginScreen(screenDimensions);

        loginScreen.getInitializetButton().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                JTextField textField = loginScreen.getInitializetTextField();

                if (textField.getText().toString().length() > 0) {
                    String tagName = textField.getText().toString();
                    displaySlideShowView(tagName);

                }

            }

        });

        changeView(loginScreen.getInitializedLoginPanel(), "searchView");

    }

    private void changeView(JPanel view, String name) {
        mainWindow.getContentPane().removeAll();
        mainWindow.getContentPane().add(view, name);
        mainWindow.revalidate();
        mainWindow.repaint();
    }

    public void displaySlideShowView(String tagName) {
        PhotoFrame slideShowView = new PhotoFrame();
        Timer timer = new Timer();
        ImageUpdateTask task = null;
        try {
            task = new ImageUpdateTask(slideShowView, tagName);
        } catch (IOException e) {
            slideShowView.dislpayError(e.getMessage());
        }
        timer.schedule(task, 0, 3000);
        changeView(slideShowView, "slideShow");
    }

    /*private void launchTimerTask(JTextField textField, JPanel photoPanel, JFrame mainWindow, LoginScreen loginScreen, Dimension2D screenDimensions) {
        try {
            ImageUpdateTask task = new ImageUpdateTask(mainWindow, photoPanel, textField.getText().toString());
            new Timer().schedule(task, 0, 3000);
        } catch (InstagramException e1) {
            loginScreen.showFieldWithErrorMessage(screenDimensions, e1.toString());
            mainWindow.setVisible(true);
        } catch (MalformedURLException e2) {
            loginScreen.showFieldWithErrorMessage(screenDimensions,e2.toString());
            mainWindow.setVisible(true);
        } catch (IOException e3) {
            loginScreen.showFieldWithErrorMessage(screenDimensions,e3.toString());
            mainWindow.setVisible(true);
        }
    }*/

}
