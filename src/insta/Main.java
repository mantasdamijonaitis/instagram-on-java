package insta;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Timer;
import java.util.TimerTask;



public class Main {

	public static void main(String[] args) throws IOException  {
		
		setProxyProperties();
		            
		AppWindow window = new AppWindow();
		window.loadLoginScreen():

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