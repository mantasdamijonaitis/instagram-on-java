package insta;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class LoginScreen {
	
		JPanel loginPanel;
		
		JLayeredPane layeredLoginPane;
		
		JLabel backgroundLabel;
		
		JTextField instagramTagField;
		
		JButton launchButton;
		
		int height;
		int width;
		
	public LoginScreen(int width,int height) throws IOException{
		
		this.height = height;
		this.width = width;
       
		initializeFrame();
		initializeLayout();
		initializeLayout();
		initializeBackground();
		initializeWelcomeText();
		initializeInputField();
		initializeButton();
		
	}
	
	void initializeFrame(){
		
		loginPanel = new JPanel();
    	loginPanel.setSize(width, height);
    	loginPanel.setVisible(true);
		
	}
	
	void initializeLayout(){
		
		loginPanel.setLayout(new CardLayout(0, 0));
		layeredLoginPane = new JLayeredPane();
        loginPanel.add(layeredLoginPane, "name_123");
		
	}
	
	void initializeBackground() throws IOException{
		
		backgroundLabel = new JLabel("New label");
        layeredLoginPane.setLayer(backgroundLabel, 0);
        backgroundLabel.setBounds(0, 0, width, height);

        backgroundLabel.setOpaque(true);
        backgroundLabel.setBackground(Color.BLACK);
        
        layeredLoginPane.add(backgroundLabel);
		
	}
	
	void initializeWelcomeText(){
		
		JLabel welcomeTextLabel = new JLabel("Welcome using PicSharing Tool! To begin, enter instagram tag below:");
        layeredLoginPane.setLayer(welcomeTextLabel, 1);
        welcomeTextLabel.setBounds(width / 8, height / 8, width, height / 4);
        
        welcomeTextLabel.setText("Welcome using PicSharing Tool! To begin, enter instagram tag below:");
        welcomeTextLabel.setForeground(Color.ORANGE);
        welcomeTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 50));
        
        layeredLoginPane.add(welcomeTextLabel);
		
	}
	
	void initializeInputField(){
		
		instagramTagField = new JTextField();
        layeredLoginPane.setLayer(instagramTagField, 1);
        instagramTagField.setBounds(width / 2 - width / 10, height / 3, width / 5, height / 20);
        instagramTagField.setOpaque(false);
        instagramTagField.setFont(new Font("Tahoma", Font.PLAIN, 30));
        instagramTagField.setCaretColor(Color.ORANGE);
        instagramTagField.setForeground(Color.ORANGE);
        instagramTagField.setColumns(10);
        instagramTagField.setText("Tag goes here...");
        layeredLoginPane.add(instagramTagField);
		
	}
	
	void initializeButton(){
		
		launchButton = new JButton("Start!");
        layeredLoginPane.setLayer(launchButton, 1);
        launchButton.setBounds(width / 2 - width / 10, height / 3 + instagramTagField.getHeight() + height / 30, width / 5, height / 20);
        launchButton.setBackground(Color.ORANGE);
        layeredLoginPane.add(launchButton);
		
	}
	
	JPanel getInitializedLoginPanel(){
		
		return loginPanel;
		
	}
	
	JButton getInitializetButton(){
		
		return launchButton;
		
	}
	
	JTextField getInitializetTextField(){
		
		return instagramTagField;
		
	}
	
}
