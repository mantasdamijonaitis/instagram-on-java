package insta;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.jinstagram.Instagram;
import org.jinstagram.entity.common.Caption;
import org.jinstagram.entity.common.Comments;
import org.jinstagram.entity.common.Images;
import org.jinstagram.entity.common.Likes;
import org.jinstagram.entity.common.User;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;

public class InstagramDataReceiver {
	
	ArrayList<Images> imagesList;
	ArrayList<Comments>commentsList;
	ArrayList<Likes>likesList;
	ArrayList<Caption> captionsList;
	ArrayList<User> usersList;
	
	String tagName;
	
	Instagram instagram;
	
	List<MediaFeedData> mediaFeeds;

	boolean useProxy = false;
	
	public InstagramDataReceiver(String tagName) throws InstagramException{
		
		this.tagName = tagName;
		
		getData();
		
	}
	
	void getData() throws InstagramException{
		
		instagram = new Instagram("3dc6dd309cb54342b21d9ae74bd0902f");

		if(useProxy) {

			ConnectionProxy connectionProxy = new ConnectionProxy();

			Proxy proxy = connectionProxy.getProxy();

			instagram.setRequestProxy(proxy);

		}

		TagMediaFeed mediaFeed = instagram.getRecentMediaTags
				(tagName,100);

		List<MediaFeedData> mediaFeeds = mediaFeed.getData();
		
		imagesList = new ArrayList<Images>();
		commentsList = new ArrayList<Comments>();
		likesList = new ArrayList<Likes>();
		captionsList = new ArrayList<Caption>();
		usersList = new ArrayList<User>();
		
		for (MediaFeedData mediaData : mediaFeeds) {
			
			Images images = mediaData.getImages();
			imagesList.add(images);
			
		    Comments comments = mediaData.getComments();
		    commentsList.add(comments);
			
		    Likes likes = mediaData.getLikes();
		    likesList.add(likes);
		    
		    Caption caption = mediaData.getCaption();
		    captionsList.add(caption);
		    
		    User user = mediaData.getUser();
		    usersList.add(user);
		
		}
		
	}
	
	ArrayList<Images> getImages(){
		
		return this.imagesList;
		
	}
	
	ArrayList<Comments> getComments(){
		
		return this.commentsList;
		
	}
	
	ArrayList<Likes> getLikes(){
		
		return this.likesList;
		
	}
	
	ArrayList<Caption>getCaptions(){
		
		return this.captionsList;
		
	}
	
	ArrayList<User>getUsers(){
		
		return this.usersList; 
		
	}

}
