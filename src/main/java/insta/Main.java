package insta;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.jinstagram.Instagram;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.security.Key;
import java.util.concurrent.ExecutionException;

public class Main {

	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		final WindowManager manager = context.getBean(WindowManager.class);
		manager.displaySearchView();
		context.registerShutdownHook();

		CacheLoader<Key, Image> loader = new CacheLoader<Key, Image>() {
			public Image load(Key key) throws Exception {
				return loadImage(key);
			}
		};
		LoadingCache<Key, Image> cache = CacheBuilder.newBuilder().build(loader);

	}

	static Image loadImage(Key key){
		return null;
	}
	
}