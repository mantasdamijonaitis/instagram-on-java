package insta;

import org.jinstagram.entity.common.Caption;
import org.jinstagram.entity.common.Comments;
import org.jinstagram.entity.common.Images;
import org.jinstagram.entity.common.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mantttttas on 2015-07-01.
 */
public class WindowManager {

    JFrame mainWindow;

    ArrayList<Images> imagesList;
    ArrayList<Caption> captionsList;
    ArrayList<User> usersList;
    ArrayList<Comments> commentsList;

    InstagramDataReceiver receiver;

    String userId;

    LoginScreen loginScreen;

    boolean useProxy;

    int width, height;

    public WindowManager(boolean useProxy) throws IOException { ///

        this.useProxy = useProxy;

        initializeScreenDimensions();
        initializeMainWindow();
        loadLoginScreen();

    }

    void initializeMainWindow(){

        mainWindow = new JFrame();
        mainWindow.setBounds(0, 0, width, height);
        mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainWindow.setUndecorated(true);
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

        loginScreenButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){



            }

        });

    }

}
