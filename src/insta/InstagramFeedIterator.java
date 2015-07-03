package insta;

import java.util.*;

import org.jinstagram.Instagram;
import org.jinstagram.entity.common.Pagination;
import org.jinstagram.entity.media.MediaInfoFeed;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;

public class InstagramFeedIterator implements Iterator<MediaFeedData> {

	private final Instagram instagram;
	private Iterator<MediaFeedData> mediaIterator = null;
	private String maxTagId;
	//TODO remove
	List<MediaFeedData> media;

	String lastId;
	String tagName;

	String nextMinId;
	String nextMaxId;

	int counter = 0;

	List<String> mediaIds;

	TagMediaFeed feed1;
	TagMediaFeed feed2;

	Pagination pagination;

	Pagination pagination1;

	public InstagramFeedIterator(String tagName) throws InstagramException { /// sprest pagal api
		this.tagName = tagName;
		loadNextMediaPage(tagName, null);
	}

	public void getMoreData(boolean firstTime) throws InstagramException {

		mediaIds = new LinkedList<String>();

		media = tagMediaFeed.getData();
		addToSet(mediaIds, media);

		//System.out.println(media.size());
		//System.out.println(media.get(0).getId());
		Pagination pagination = tagMediaFeed.getPagination();
		//System.out.println(pagination);

		feed1 = null;
		while ((feed1 = loadMoreMedia(pagination)).getData().size() > 0) {
			pagination = feed1.getPagination();
			mediaIterator = updateIterator(pagination, mediaIds.size()); /// media Ids.size() is an update
			System.out.println("minTagId: "+pagination.getMinTagId()+" nextMaxTagId: "+pagination.getNextMaxTagId());
			addToSet(mediaIds, feed1.getData());
			//System.out.println(mediaIds.size());

			if (pagination.getNextMaxTagId() == null) {
				break;
			}
		}

		if(firstTime) {

			mediaIds = new LinkedList<String>();

			media = tagMediaFeed.getData();
			addToSet(mediaIds, media);

			//System.out.println(media.size());
			//System.out.println(media.get(0).getId());
			pagination1 = tagMediaFeed.getPagination();
			//System.out.println(pagination);
			System.out.println("firstTimeOutputOutside minTagId: "+pagination1.getMinTagId()+" nextMaxTagId: "+pagination1.getNextMaxTagId());

			int counter = 0;
			feed1 = loadMoreMedia(pagination1);
			while ((feed1 = loadMoreMedia(pagination1)).getData().size() > 0 && counter < 1) {
				counter++;
				pagination1 = feed1.getPagination();
				mediaIterator = updateIterator(pagination1,mediaIds.size()); /// media Ids.size() is an update
				System.out.println("firstTimeOutputInside minTagId: "+pagination1.getMinTagId()+" nextMaxTagId: "+pagination1.getNextMaxTagId());
				addToSet(mediaIds, feed1.getData());
				//System.out.println(mediaIds.size());

				if (pagination1.getNextMaxTagId() == null) {
					break;
				}
			}

		} else {

			mediaIds = new LinkedList<String>();

			media = tagMediaFeed.getData();
			addToSet(mediaIds, media);

			//System.out.println(media.size());
			//System.out.println(media.get(0).getId());
			//Pagination pagination1 = tagMediaFeed.getPagination();
			//System.out.println(pagination);


			int counter = 0;
			feed2 = loadMoreMedia(pagination1);
			pagination1 = feed2.getPagination();
			System.out.println("secondTimeOutputOutside minTagId: "+pagination1.getMinTagId()+" nextMaxTagId: "+pagination1.getNextMaxTagId()); // passed!!!!!!!!
			while ((feed2 = loadMoreMedia(pagination1)).getData().size() > 0 && counter < 1) {
				counter++;
				pagination1 = feed2.getPagination();
				mediaIterator = updateIterator(pagination1, mediaIds.size()); /// media Ids.size() is an update
				addToSet(mediaIds, feed2.getData());
				System.out.println("secondTimeOutputInside minTagId: " + pagination1.getMinTagId() + " nextMaxTagId: " + pagination1.getNextMaxTagId()); /// one too far

				//System.out.println(mediaIds.size());

				if (pagination1.getNextMaxTagId() == null) {
					break;
				}

			}
		}

	}

	public void printThatShit() throws InstagramException {
		boolean isFirstTime = true;
		getMoreData(isFirstTime);
		int counter = 0;
		while(mediaIterator.hasNext()){

			System.out.println("kartas: "+isFirstTime+"coutner : "+ counter+" link "+instagram.getMediaInfo(mediaIds.get(counter)).getData().getId());
			counter++;
			mediaIterator.next();

			if(!mediaIterator.hasNext()) {
				//System.out.println("this happens");
				counter = 0;
				isFirstTime = false;
				getMoreData(isFirstTime);
			}

		}

	}




	public void recall() throws InstagramException {

		mediaIds = new LinkedList<String>();

		media = tagMediaFeed.getData();
		addToSet(mediaIds, media);

		//System.out.println(media.size());
		//System.out.println(media.get(0).getId());
		Pagination pagination = tagMediaFeed.getPagination();
		//System.out.println(pagination);

		feed1 = null;
		while ((feed1 = loadMoreMedia(pagination)).getData().size() > 0) {
			pagination = feed1.getPagination();
			mediaIterator = updateIterator(pagination,mediaIds.size()); /// media Ids.size() is an update
			//System.out.println(pagination);
			addToSet(mediaIds, feed1.getData());
			//System.out.println(mediaIds.size());

			if (pagination.getNextMaxTagId() == null) {
				break;
			}
		}


		Timer timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run(){

				int counter = 0;
				while(mediaIterator.hasNext()){

					try {
						System.out.println("counter: "+counter+" link:"+instagram.getMediaInfo(mediaIds.get(counter)).getData().getImages().getStandardResolution().getImageUrl());
					} catch (InstagramException e) {
						e.printStackTrace();
					}
					counter++;
					mediaIterator.next();

				}

			}

		},2000);


				/*for(int i=0;i<mediaIds.size();i++) { /// this is how I can get information !

					try {
						MediaInfoFeed data = instagram.getMediaInfo(mediaIds.get(i));
						System.out.println("size: "+mediaIds.size()+" link: "+data.getData().getImages().getStandardResolution().getImageUrl());
					} catch (InstagramException e) {
						e.printStackTrace();
					}

				} /// the end of information showing */



	}

	private TagMediaFeed loadMoreMedia(Pagination pagination) throws InstagramException {
		return instagram.getRecentMediaTags(tagName, null, pagination.getNextMaxId(), 5); /// kiek nurodai tiek vienu kartu pasiima foto
	}

	private void addToSet(List<String> mediaIds, List<MediaFeedData> media) {
		for (MediaFeedData data: media) {
			mediaIds.add(data.getId());
		}
	}

	private Iterator<MediaFeedData> updateIterator(Pagination pagination, int size) throws InstagramException {
		return instagram.getRecentMediaTags(tagName, null, pagination.getNextMaxId(),size).getData().iterator(); /// kiek nurodai, tiek vienu kartu pasiima iteratorius
	}

	void printTagNamesToLearn(){

		List<MediaFeedData> media = tagMediaFeed.getData();

		for (MediaFeedData mediaData : media) {

			//System.out.println(mediaData.getId());

		}

	}

	@Override
	public boolean hasNext() {
		if (mediaIterator.hasNext()) {
			return true;
		} else if (maxTagId != null) {
			try {
				TagMediaFeed tagMediaFeed = loadNextMediaPage(tagName, maxTagId);
				mediaIterator = tagMediaFeed.getData().iterator();
				maxTagId = tagMediaFeed.getPagination().getNextMaxTagId();
			} catch (InstagramException e) {
				return false;
			}
			return hasNext();
		}
		return false;
	}

	public static TagMediaFeed loadNextMediaPage(String tagName, String maxTagId) throws InstagramException {
		Instagram instagram = new Instagram(System.getProperty("clientId"));
		return instagram.getRecentMediaTags(tagName, null, maxTagId);
	}

	@Override
	public MediaFeedData next() {
		return mediaIterator.next();
	}

	@Override
	public void remove() {
	}

	void workWithPicture(){



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
