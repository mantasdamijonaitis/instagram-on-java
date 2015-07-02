package insta;

import java.util.*;
import java.util.List;

import org.jinstagram.Instagram;
import org.jinstagram.entity.common.Pagination;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;

public class InstagramFeedIterator implements Iterator<MediaFeedData> {

	private final Instagram instagram;
	private TagMediaFeed tagMediaFeed = null;
	private Iterator<MediaFeedData> mediaFeed = null;

	String lastId;
	int counter = 0;
	String tagName;

	public InstagramFeedIterator(String tagName) throws InstagramException { /// sprest pagal api
		instagram = new Instagram(System.getProperty("clientId"));

		System.out.println("Tag info " + instagram.getTagInfo(tagName));

		tagMediaFeed = instagram.getRecentMediaTags(tagName);
		mediaFeed = tagMediaFeed.getData().iterator();

		this.tagName = tagName;

		printTagNamesToLearn();
		//recall();

	}

	public void recall() throws InstagramException {

		String firstMinId = "";
		String secondMinId = "";
		String thirdMinId = "";

		List<String> mediaIds = new LinkedList<String>();

		List<MediaFeedData> media = tagMediaFeed.getData();
		addToSet(mediaIds, media);

		System.out.println(media.size());
		System.out.println(media.get(0).getId());
		Pagination pagination = tagMediaFeed.getPagination();
		System.out.println(pagination);

		TagMediaFeed feed = null;
		while ((feed = loadMoreMedia(pagination)).getData().size() > 0) {
			pagination = feed.getPagination();
			System.out.println(pagination);
			addToSet(mediaIds, feed.getData());
			System.out.println(mediaIds.size());

			if (pagination.getNextMaxTagId() == null) {
				break;
			}
		}


	}

	private TagMediaFeed loadMoreMedia(Pagination pagination) throws InstagramException {
		return instagram.getRecentMediaTags(tagName, null, pagination.getNextMaxId(), 5);
	}

	private void addToSet(List<String> mediaIds, List<MediaFeedData> media) {
		for (MediaFeedData data: media) {
			mediaIds.add(data.getId());
		}
	}

	void printTagNamesToLearn(){

		List<MediaFeedData> media = tagMediaFeed.getData();

		for (MediaFeedData mediaData : media) {

			//System.out.println(mediaData.getId());

		}

	}

	@Override
	public boolean hasNext() {
		return mediaFeed.hasNext();
	}

	@Override
	public MediaFeedData next() {
		return mediaFeed.next();
	}

	@Override
	public void remove() {
	}



	/*ArrayList<Images> imagesList;
	ArrayList<Comments>commentsList;
	ArrayList<Likes>likesList;
	ArrayList<Caption> captionsList;
	ArrayList<User> usersList;
	
	String tagName;
	
	Instagram instagram;
	
	List<MediaFeedData> mediaFeeds;

	boolean useProxy = false;
	
	public InstagramFeedIterator(String tagName) throws InstagramException{
		
		this.tagName = tagName;

		System.out.println(tagName);

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
		
	}*/

}
