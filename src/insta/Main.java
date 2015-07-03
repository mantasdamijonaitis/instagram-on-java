package insta;

import java.awt.*;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Iterator;


public class Main {

	public static void main(String[] args) throws IOException  {

		//AuthenticationProxy roxy = new AuthenticationProxy();


		InstagramFeedIterator iterator = new InstagramFeedIterator("vilniuscc");
		iterator.printThatShit();
		/*iterator.getMoreData(true);
		iterator.getMoreData(false);
		iterator.getMoreData(false);*/
		//iterator.recall();
		//iterator.workWithPicture();
		//System.out.println(iterator.counter);

		
		//setProxyProperties();
		            
		//AppWindow window = new AppWindow();
		//window.loadLoginScreen();

		//WindowManager manager = new WindowManager(false);
		//manager.launchSlideshowRoutine();

	}
	
	static void setProxyProperties(){
		
		System.setProperty("proxySet","true");
		System.setProperty("http.proxyHost","proxyvip.foreningssparbanken.se");
		System.setProperty("http.proxyPort","8080");
		Authenticator.setDefault(new Authenticator(){
			
			protected PasswordAuthentication getPasswordAuthentication(){
				
				return new PasswordAuthentication("p998olj","p998olj".toCharArray());
				
			}
			
		});
		
	}
	
}