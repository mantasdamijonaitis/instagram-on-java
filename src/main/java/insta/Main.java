package insta;

import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class Main {

	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
		final WindowManager manager = new WindowManager();
		manager.showSearchView();
	}
	
}