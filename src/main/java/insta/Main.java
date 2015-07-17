package insta;

import javafx.application.Platform;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.ExecutionException;

public class Main {

	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
		Platform.setImplicitExit(false);
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		final WindowManager manager = context.getBean(WindowManager.class);
		manager.displaySearchView();
		context.registerShutdownHook();

	}


}