package insta;

import org.apache.commons.lang3.StringUtils;

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
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.getContentPane().setLayout(new CardLayout(0, 0));

        mainWindow.setVisible(true);

    }

    Dimension2D getScreenDimensions() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public void showSearchView()  {

        final SearchView loginScreen = new SearchView();

        loginScreen.getInitializetButton().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                JTextField tagInputField = loginScreen.getInitializetTextField();

                if (StringUtils.isNotEmpty(tagInputField.getText().toString())) {
                    String tagName = tagInputField.getText().toString();
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
            slideShowView.displayError(e.getMessage());
        }
        timer.schedule(task, 0, 3000);
        changeView(slideShowView, "slideShow");
    }

}
