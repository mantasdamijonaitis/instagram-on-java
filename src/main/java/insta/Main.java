package insta;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.awt.*;
import java.io.IOException;
import java.security.Key;
import java.util.concurrent.ExecutionException;

public class Main {

	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		final WindowManager manager = context.getBean(WindowManager.class);
		manager.displaySearchView();
		context.registerShutdownHook();
	}

}