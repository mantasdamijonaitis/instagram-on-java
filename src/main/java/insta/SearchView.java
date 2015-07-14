package insta;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Dimension2D;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@Component
public class SearchView {

	public final JButton launchButton;
	public final JTextField instagramTagField;
	public final JLayeredPane layeredLoginPane;
	public final JPanel loginPanel;

	public SearchView() {
		final Dimension2D dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.instagramTagField = initializeInputField(dimension);
		this.launchButton = initializeButton(dimension);
		this.loginPanel = initializeFrame(dimension);
		this.layeredLoginPane = addFieldsToLayeredPane(dimension, loginPanel, instagramTagField, launchButton);

		
	}
	
	JPanel initializeFrame(Dimension2D dimension){

		JPanel loginPanel = new JPanel();
		loginPanel.setSize((int) dimension.getWidth(), (int) dimension.getHeight());
		loginPanel.setLayout(new CardLayout(0, 0));
    	loginPanel.setVisible(true);

		return loginPanel;
		
	}
	
	JLayeredPane addFieldsToLayeredPane(Dimension2D dimension, JPanel loginPanel, JTextField instagramTagField, JButton launchButton)  {

		JLayeredPane layeredLoginPane;
		layeredLoginPane = new JLayeredPane();
		loginPanel.add(layeredLoginPane, "name_123"); /// add this later!

		JLabel backgroundLabel = new JLabel("New label");
		layeredLoginPane.setLayer(backgroundLabel, 0);
		backgroundLabel.setBounds(0, 0, (int) dimension.getWidth(), (int) dimension.getHeight());

		backgroundLabel.setOpaque(true);
		backgroundLabel.setBackground(Color.BLACK);

		layeredLoginPane.add(backgroundLabel);

		JLabel welcomeTextLabel = new JLabel("Welcome using PicSharing Tool! To begin, enter instagram tag below:");
		layeredLoginPane.setLayer(welcomeTextLabel, 1);
		welcomeTextLabel.setBounds((int) dimension.getWidth() / 8, (int) dimension.getHeight() / 8, (int) dimension.getWidth(), (int) dimension.getHeight() / 4);

		welcomeTextLabel.setText("Welcome using PicSharing Tool! To begin, enter instagram tag below:");
		welcomeTextLabel.setForeground(Color.ORANGE);
		welcomeTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 50));

		layeredLoginPane.add(welcomeTextLabel);

		layeredLoginPane.setLayer(instagramTagField, 1);//
		layeredLoginPane.add(instagramTagField);

		layeredLoginPane.setLayer(launchButton, 1);//
		layeredLoginPane.add(launchButton);

		return layeredLoginPane;

	}

	
	JTextField initializeInputField(Dimension2D dimension){

		JTextField instagramTagField = new JTextField();
		instagramTagField.setBounds((int) dimension.getWidth() / 2 - (int) dimension.getWidth() / 10, (int) dimension.getHeight() / 3, (int) dimension.getWidth() / 5, (int) dimension.getHeight() / 20);
        instagramTagField.setOpaque(false);
		instagramTagField.setFont(new Font("Tahoma", Font.PLAIN, 30));
		instagramTagField.setCaretColor(Color.ORANGE);
		instagramTagField.setForeground(Color.ORANGE);
        instagramTagField.setColumns(10);
        instagramTagField.setText("vilniuscc");

		return instagramTagField;
		
	}
	
	JButton initializeButton(Dimension2D dimension){
		
		JButton launchButton = new JButton("Start!");
		launchButton.setBounds((int) (dimension.getWidth() * 2/5), (int) (dimension.getHeight() * 5/12), (int) (dimension.getWidth() / 5), (int) (dimension.getHeight() / 20));
		launchButton.setBackground(Color.ORANGE);

		return launchButton;
		
	}
	
	JButton getInitializetButton(){
		
		return launchButton;
		
	}

	JPanel getInitializedLoginPanel(){

		return  loginPanel;

	}
	
	JTextField getInitializetTextField(){
		
		return instagramTagField;
		
	}

	void showFieldWithErrorMessage(Dimension2D dimension,String message){

		JLabel errorMessageField = new JLabel();
		errorMessageField.setBounds((int)(dimension.getWidth() * 2/5), (int)(dimension.getHeight() * 9/20), (int)(dimension.getWidth()), (int)(dimension.getHeight() / 20));
		errorMessageField.setForeground(Color.ORANGE);
		errorMessageField.setVisible(true);
		errorMessageField.setText(message);
		layeredLoginPane.setLayer(errorMessageField, 1);
		layeredLoginPane.add(errorMessageField);
		System.out.println("info from logscr class: "+errorMessageField.toString());

	}
	
}
