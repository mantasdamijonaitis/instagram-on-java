package insta;
import java.util.*;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.jinstagram.Instagram;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;


public class InstagramFeedIterator implements Iterator<MediaFeedData> {

	private final Instagram instagram;
	private Iterator<MediaFeedData> mediaIterator;
	private String maxTagId;
	private final String tagName;
	private int pageSize;


	public InstagramFeedIterator(Instagram instagram, String tagName, int pageSize) throws InstagramException {
		this.instagram = instagram;
		this.tagName = tagName;
		this.pageSize = pageSize;
		loadNextMediaPage();
	}

	private void loadNextMediaPage() throws InstagramException {
		final TagMediaFeed tagMediaFeed = loadMedia(tagName, maxTagId, pageSize);
		mediaIterator = tagMediaFeed.getData().iterator();
		maxTagId = tagMediaFeed.getPagination().getNextMaxTagId();
	}

	public TagMediaFeed loadMedia(String tagName, String maxTagId,int amount) throws InstagramException {
		return instagram.getRecentMediaTags(tagName, null, maxTagId, amount);
	}

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

	public MediaFeedData next() {
			hasNext();
			return mediaIterator.next();
	}

	public void remove() {
	}

}


