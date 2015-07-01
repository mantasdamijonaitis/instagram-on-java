package insta;

import java.awt.*;

/**
 * Created by mantttttas on 2015-07-01.
 */
public class LayoutParameters {

    Point screenDimensions;

    Rectangle mainContainerParameters;

    public LayoutParameters(Point screenDimensions) {

        if (screenDimensions.getX() < 0 || screenDimensions.getY() < 0){

            throw new IllegalArgumentException("Dimensions can not be equal to zero!");

        }  else {

            this.screenDimensions = screenDimensions;

            calculateMainContainerParameters();

        }

    }

    void calculateMainContainerParameters(){

        int x = (int)screenDimensions.getX() / 5;
        int y = (int)screenDimensions.getY() / 8;

        int width = (int)(screenDimensions.getX() / 2 - screenDimensions.getX() / 10);
        int height = (int)(screenDimensions.getY() - (screenDimensions.getY() / 15) * 2);

        if(width > screenDimensions.getX())
            throw new IllegalComponentStateException("");

        mainContainerParameters = new Rectangle(x,y,width,height);

    }

}
