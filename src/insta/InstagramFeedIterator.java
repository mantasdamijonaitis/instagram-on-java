package insta;
import java.util.*;

import org.jinstagram.Instagram;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;

public class InstagramFeedIterator implements Iterator<MediaFeedData> {

	private Iterator<MediaFeedData> mediaIterator;
	private String maxTagId;
	private String tagName;

	private TagMediaFeed tagMediaFeed;

	public InstagramFeedIterator(String tagName) throws InstagramException { /// sprest pagal api
		this.tagName = tagName;
		loadNextMediaPage();
	}

	@Override
	public boolean hasNext() {
		if (mediaIterator.hasNext()) {
			return true;
		} else if (maxTagId != null) {
			try {
				loadNextMediaPage();
			} catch (InstagramException e) {
				return false;
			}
			return hasNext();
		}
		return false;
	}

	private void loadNextMediaPage() throws InstagramException {
		tagMediaFeed = loadMedia(tagName, maxTagId);
		mediaIterator = tagMediaFeed.getData().iterator();
		maxTagId = tagMediaFeed.getPagination().getNextMaxTagId();
	}

	public static TagMediaFeed loadMedia(String tagName, String maxTagId) throws InstagramException {
		Instagram instagram = new Instagram(System.getProperty("clientId"));
		if(System.getProperty("proxy") != null)
			instagram.setRequestProxy(new AuthenticationProxy().proxy);
		return instagram.getRecentMediaTags(tagName, null, maxTagId);
	}

	@Override
	public MediaFeedData next() {
			hasNext();
			return mediaIterator.next();
	}

	@Override
	public void remove() {
	}

}


