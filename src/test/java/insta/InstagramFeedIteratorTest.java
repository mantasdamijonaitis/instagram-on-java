package insta;

import org.jinstagram.Instagram;
import org.jinstagram.entity.common.Pagination;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;


/**
 * Created by mantttttas on 2015-07-02.
 */
public class InstagramFeedIteratorTest {

    @Mock Instagram instagram;
    @Mock TagMediaFeed mediaFeed1;
    @Mock TagMediaFeed mediaFeed2;
    @Mock Pagination pagination1;
    @Mock Pagination pagination2;

    @Before
    public void before() throws InstagramException {
        MockitoAnnotations.initMocks(this);

        System.setProperty("clientId", "3dc6dd309cb54342b21d9ae74bd0902f");

        when(instagram.getRecentMediaTags("vilniuscc", null, null, 5)).thenReturn(mediaFeed1);
        when(instagram.getRecentMediaTags("vilniuscc", null, "NEXT_TAG_ID", 5)).thenReturn(mediaFeed2);

        MediaFeedData data = new MediaFeedData();
        List<MediaFeedData> dataList = Arrays.asList(data, data, data, data, data);

        when(mediaFeed1.getData()).thenReturn(dataList);
        when(mediaFeed1.getPagination()).thenReturn(pagination1);
        when(pagination1.getNextMaxTagId()).thenReturn("NEXT_TAG_ID");

        when(mediaFeed2.getData()).thenReturn(dataList);
        when(mediaFeed2.getPagination()).thenReturn(pagination2);
        when(pagination2.getNextMaxTagId()).thenReturn(null);
    }

    @Test
    public void testRetrieves10() throws InstagramException {
        InstagramFeedIterator iterator = new InstagramFeedIterator(instagram, "vilniuscc", 5);
        int counter = 0;
        while (iterator.hasNext()) {
            iterator.next();
            counter++;
        }

        assertEquals(10, counter);
        verify(instagram, times(2)).getRecentMediaTags(eq("vilniuscc"), any(String.class), any(String.class), eq(5L));

    }

}
