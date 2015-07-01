package insta;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

/**
 * Created by mantttttas on 2015-07-01.
 */
public class LayoutParametersTest {

    @Test (expected = IllegalArgumentException.class)
    public void testMainClassConstructorWithZeros(){

        LayoutParameters params = new LayoutParameters(new Point(-100,-100));

    }

    @Test
    public void testMainContainerCoordinatesCalculationsX(){

        LayoutParameters params = new LayoutParameters(new Point(1024,768));
        Assert.assertEquals(204,params.mainContainerParameters.getX(),2);

    }

    @Test
    public void testMainContainerCoordinatesCalculationsY(){

        LayoutParameters params = new LayoutParameters(new Point(1024,768));
        Assert.assertEquals(96,params.mainContainerParameters.getY(),2);

    }

}
