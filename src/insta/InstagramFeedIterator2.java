package insta;

import org.jinstagram.Instagram;
import org.jinstagram.entity.common.Images;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;

import java.awt.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mantttttas on 2015-07-02.
 */
public class InstagramFeedIterator2 {






    Instagram instagram;

    String clientId;

    List<MediaFeedData> dataList;

    String minTagId;

    public InstagramFeedIterator2(){

        clientId  = getClientId();
        createInstagram(clientId);

        dataList = getFeedData("vilniuscc");

        if(!dataList.equals(null))
            System.out.println(dataList.size()+"");
        else System.out.println("null");

    }




    String getClientId() {

        String userId = System.getProperty("clientId");

        if(!userId.equals(null) && userId.length() > 0)
            return userId;

        else return "3dc6dd309cb54342b21d9ae74bd0902f";

    }

    void createInstagram(String clientId){

        instagram = new Instagram(clientId);

    }

    List<MediaFeedData> getFeedData(String tagName) {

        minTagId = "1";

        TagMediaFeed feed = null;
        try {
            feed = instagram.getRecentMediaTags(tagName, minTagId, null, 100000);
        } catch (InstagramException e) {
            e.printStackTrace();
            return null;
        }

        System.out.println(feed.toString());

        List<MediaFeedData> dataList = feed.getData();
        if (dataList.size() == 0) return null;

        MediaFeed recentMediaNextPage = null;
        try {

            recentMediaNextPage = instagram.getRecentMediaNextPage(feed.getPagination());
            while (recentMediaNextPage.getPagination() != null) {
                dataList.addAll(recentMediaNextPage.getData());
                recentMediaNextPage = instagram.getRecentMediaNextPage(recentMediaNextPage.getPagination());
            }

        } catch (InstagramException e) {
            e.printStackTrace();
            return null;
        }

        minTagId = feed.getPagination().getMinTagId();

        return dataList;

    }



}