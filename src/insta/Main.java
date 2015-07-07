package insta;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;


public class Main {

 	static JFrame frame;
	static JPanel photoPanel = null;
	static InstagramFeedIterator iterator;

	public static void main(String[] args) throws IOException  {

		WindowManager manager = new WindowManager();

		/*iterator = new InstagramFeedIterator("testingnewestapp");
		frame = new JFrame();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();

		frame.setBounds(0, 0, width, height);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				try {
					if (iterator.hasNext()) {
						photoPanel = new PhotoFrame(iterator.next()).getCompletePhotoPanel();
					} else {
						iterator = new InstagramFeedIterator("testingnewestapp");
						photoPanel = new PhotoFrame(iterator.next()).getCompletePhotoPanel();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				frame.add(photoPanel);
				//frame.getContentPane().setLayout(new CardLayout(0, 0));
				frame.setVisible(true);

			}

		}, 0, 3000);

		frame.add(photoPanel);

		frame.setVisible(true);*/

	}
	
}