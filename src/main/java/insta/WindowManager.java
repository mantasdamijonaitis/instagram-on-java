package insta;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Dimension2D;
import java.io.IOException;
import java.util.Timer;


/**
 * Created by mantttttas on 2015-07-01.
 */

@Component
public class WindowManager {

    private static Logger LOG = Logger.getLogger(WindowManager.class.getName());

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

    public void displaySearchView()  {

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
        LOG.info("Changing view to " + name);
        mainWindow.getContentPane().removeAll();
        mainWindow.getContentPane().add(view, name);
        mainWindow.revalidate();
        mainWindow.repaint();
    }

    public void displaySlideShowView(final String tagName) {
        final Timer timer = new Timer();
        try {
            final ImageUpdateTask task = new ImageUpdateTask(photoFrame,tagName);
            LOG.info("Task created #" + tagName);
            timer.schedule(task, 0, 3000);
            changeView(photoFrame, "slideShow");

            mainWindow.addKeyListener(new KeyListener() {
                public void keyTyped(KeyEvent e) {

                }

                public void keyPressed(KeyEvent e) {

                    if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                        boolean taskCancelStatus = task.cancel();
                        LOG.info("Task canceled #" + tagName + " status " + taskCancelStatus);
                        timer.cancel();
                        displaySearchView();
                    }

                }

                public void keyReleased(KeyEvent e) {

                }
            });

            mainWindow.setFocusable(true);
            mainWindow.requestFocusInWindow();

        } catch (IOException e) {
            photoFrame.displayError(e.getMessage());
        }

    }

}
