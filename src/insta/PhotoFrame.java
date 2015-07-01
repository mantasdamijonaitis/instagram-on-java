package insta;

import sun.plugin.dom.css.Rect;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mantttttas on 2015-07-01.
 */
public class PhotoFrame {

    JPanel photoPanel;
    JPanel mainContainer;

    JLayeredPane layeredPhotoPanel;

    JLabel backgroundLabel;
    JLabel photoLabel;
    JLabel captionLabel;
    JLabel uploaderImageLabel;
    JLabel likesLabel;
    JLabel uploadDateLabel;
    JLabel uploaderNameLabel;


    LayoutParameters layoutParameters;

    int width,height;

    public PhotoFrame(){

        initializeLayoutParameters();
        initializePhotoPanel();
        initializeBackground();

    }

    void initializeLayoutParameters(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        width = (int)screenSize.getWidth();
        height = (int)screenSize.getHeight();

        layoutParameters = new LayoutParameters(new Point(width,height));

    }

    void initializeLayeredPane(){



        layeredPhotoPanel = new JLayeredPane();
        photoPanel.add(layeredPhotoPanel,"layeredPhotoPanel");

    }

    void initializePhotoPanel(){

        photoPanel = new JPanel();
        photoPanel.setLayout(new CardLayout(0, 0));
        photoPanel.setBounds(0,0,width,height);
        photoPanel.setVisible(true);

    }

   void initializeBackground(){

       backgroundLabel = new JLabel();
       layeredPhotoPanel.setLayer(backgroundLabel, 0);
       backgroundLabel.setBounds(0, 0, width, height);

       backgroundLabel.setOpaque(true);
       backgroundLabel.setBackground(Color.BLACK);

       layeredPhotoPanel.add(backgroundLabel);

   }

    void initializeMainContainer(){

        mainContainer = new JPanel();
        layeredPhotoPanel.setLayer(mainContainer,1);
        //mainContainer.setBounds();


    }

}
