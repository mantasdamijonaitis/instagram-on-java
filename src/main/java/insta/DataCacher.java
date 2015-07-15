package insta;

import org.jinstagram.Instagram;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;

import java.util.Iterator;

/**
 * Created by mantttttas on 2015-07-14.
 */
public class DataCacher {

    Iterator<MediaFeedData> iterator;
    private String maxTagId;
    private String tagName;
    final private Instagram instagram;
    public final int PAGE_SIZE = 10;

    public DataCacher(Instagram instagram, String tagName) throws InstagramException {
        this.instagram = instagram;
        this.tagName = tagName;
        while(tagName!=null){
            cacheNextMediaPage();
        }
    }

    private void cacheNextMediaPage() throws InstagramException {
        final TagMediaFeed tagMediaFeed = loadMedia(tagName, maxTagId, PAGE_SIZE);
        iterator = tagMediaFeed.getData().iterator();
        maxTagId = tagMediaFeed.getPagination().getNextMaxTagId();
        //addToCache(tagMediaFeed,maxTagId);
    }

    public TagMediaFeed loadMedia(String tagName, String maxTagId,int amount) throws InstagramException {
        return instagram.getRecentMediaTags(tagName, null, maxTagId, amount);
    }

    /*private void addToCache(TagMediaFeed media, String maxTagId){

        CacheManager cm = CacheManager.getInstance();
        Cache cache = cm.getCache("tagMediaFeedCache");

        if(cache == null){
            cm.addCache("tagMediaFeedCache");
        }

        if(!cache.isValueInCache(media)){
            cache.put(new Element(maxTagId,media));
        }

    }*/

}


