package insta;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginScreen {
	
		JPanel loginPanel;

		JButton launchButton;

		JTextField instagramTagField;

		private JLayeredPane layeredLoginPane;
		
	public LoginScreen(int width,int height) throws IOException{
       
		initializeFrame(width, height);
		initializeLayout();
		initializeBackground(width,height);
		initializeWelcomeText(width,height);
		initializeInputField(width,height);
		initializeButton(width,height);
		
	}
	
	void initializeFrame(int width, int height){
		
		loginPanel = new JPanel();
    	loginPanel.setSize(width, height);
    	loginPanel.setVisible(true);
		
	}
	
	void initializeLayout(){
		
		loginPanel.setLayout(new CardLayout(0, 0));
		layeredLoginPane = new JLayeredPane();
        loginPanel.add(layeredLoginPane, "name_123");
		
	}
	
	void initializeBackground(int width, int height) throws IOException{
		
		JLabel backgroundLabel = new JLabel("New label");
        layeredLoginPane.setLayer(backgroundLabel, 0);
        backgroundLabel.setBounds(0, 0, width, height);

        backgroundLabel.setOpaque(true);
        backgroundLabel.setBackground(Color.BLACK);
        
        layeredLoginPane.add(backgroundLabel);
		
	}
	
	void initializeWelcomeText(int width, int height){
		
		JLabel welcomeTextLabel = new JLabel("Welcome using PicSharing Tool! To begin, enter instagram tag below:");
        layeredLoginPane.setLayer(welcomeTextLabel, 1);
        welcomeTextLabel.setBounds(width / 8, height / 8, width, height / 4);
        
        welcomeTextLabel.setText("Welcome using PicSharing Tool! To begin, enter instagram tag below:");
        welcomeTextLabel.setForeground(Color.ORANGE);
        welcomeTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 50));
        
        layeredLoginPane.add(welcomeTextLabel);
		
	}
	
	void initializeInputField(int width, int height){
		
		instagramTagField = new JTextField();
        layeredLoginPane.setLayer(instagramTagField, 1);
        instagramTagField.setBounds(width / 2 - width / 10, height / 3, width / 5, height / 20);
        instagramTagField.setOpaque(false);
        instagramTagField.setFont(new Font("Tahoma", Font.PLAIN, 30));
        instagramTagField.setCaretColor(Color.ORANGE);
        instagramTagField.setForeground(Color.ORANGE);
        instagramTagField.setColumns(10);
        instagramTagField.setText("vilniuscc");
        layeredLoginPane.add(instagramTagField);
		
	}
	
	void initializeButton(int width, int height){
		
		launchButton = new JButton("Start!");
        layeredLoginPane.setLayer(launchButton, 1);
        launchButton.setBounds(width / 2 - width / 10, height / 3 + height / 20 + height / 30, width / 5, height / 20);
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

	JLabel showFieldWithErrorMessage(int width, int height,String message){

		JLabel errorMessageField = new JLabel();
		errorMessageField.setBounds(width / 2 - width / 10, height / 3 + instagramTagField.getHeight() + height / 15, width / 5, height / 20);
		errorMessageField.setForeground(Color.ORANGE);
		errorMessageField.setVisible(true);
		errorMessageField.setText(message);
		layeredLoginPane.setLayer(errorMessageField, 1);
		layeredLoginPane.add(errorMessageField);
		System.out.println("info from logscr class: "+errorMessageField.toString());

		return errorMessageField;

	}
	
}
