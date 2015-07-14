package insta;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

@Component
public class WindowManager {

    final JFrame mainWindow;
    final PhotoFrame photoFrame;
    final SearchView searchView;

    @Autowired
    public WindowManager(PhotoFrame photoFrame,SearchView searchView) {
        this.photoFrame = photoFrame;
        this.searchView = searchView;
        final Dimension2D screenDimensions = getScreenDimensions();
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

        searchView.getInitializetButton().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                String tagName = searchView.getInitializetTextField().getText().toString();
                if (StringUtils.isNotEmpty(tagName)) {
                    displaySlideShowView(tagName);
                }

            }

        });

        changeView(searchView.getInitializedLoginPanel(), "searchView");

    }

    private void changeView(JPanel view, String name) {
        mainWindow.getContentPane().removeAll();
        mainWindow.getContentPane().add(view, name);
        mainWindow.revalidate();
        mainWindow.repaint();
    }

    public void displaySlideShowView(String tagName) {
        Timer timer = new Timer();
        ImageUpdateTask task = null;
        try {
            task = new ImageUpdateTask(photoFrame, tagName);
        } catch (IOException e) {
            photoFrame.displayError(e.getMessage());
        }
        timer.schedule(task, 0, 3000);
        changeView(photoFrame, "slideShow");
    }

}
