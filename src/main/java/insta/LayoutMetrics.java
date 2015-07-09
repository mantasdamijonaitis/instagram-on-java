package insta;

import java.awt.*;
import java.awt.geom.Dimension2D;

public class LayoutMetrics {

    public LayoutMetrics() {

    }

    Rectangle getImageMetrics(Dimension2D screenDimensions) {

        return new Rectangle(
                (int) screenDimensions.getWidth() / 50,
                (int) screenDimensions.getWidth() / 50 + (int) screenDimensions.getHeight() / 20,
                ((int) screenDimensions.getWidth() / 2),
                (int) (screenDimensions.getHeight() * 9/10 - screenDimensions.getWidth() / 25));

    }

    Rectangle getCaptionMetrics(Dimension2D screenDimensions) {

        return new Rectangle(
                (int) getImageMetrics(screenDimensions).getX(),
                0,
                (int) screenDimensions.getWidth() / 2,
                (int) screenDimensions.getHeight() / 10);

    }

    Rectangle getUploaderImageMetrics(Dimension2D screenDimensions) {

        return new Rectangle(
                (int) (screenDimensions.getWidth() / 2 + screenDimensions.getHeight() / 6),
                (int) getImageMetrics(screenDimensions).getY() + (int) screenDimensions.getHeight() / 12,
                (int) screenDimensions.getWidth() / 15,
                (int) screenDimensions.getWidth() / 15);

    }

    Rectangle getCommenterImageMetrics(Dimension2D screenDimensions) {

        return new Rectangle(
                (int)(screenDimensions.getWidth() / 2 + screenDimensions.getHeight() / 6),
                (int)screenDimensions.getWidth() / 50 + (int) screenDimensions.getHeight() * 1/3,
                (int)(screenDimensions.getWidth() / 30),
                (int)(screenDimensions.getWidth() / 30));

    }

    Rectangle getCommentMetrics(Dimension2D screenDimensions) {

        return new Rectangle(
                (int)(screenDimensions.getWidth() * 11 / 20 + screenDimensions.getHeight()  / 6),
                (int)screenDimensions.getWidth() / 50 + (int) screenDimensions.getHeight() * 1/3,
                (int) screenDimensions.getWidth() / 2,
                (int) screenDimensions.getHeight() / 10
        );


    }

    Rectangle getUploaderNameMetrics(Dimension2D screenDimensions) {

        return new Rectangle(
                (int) (screenDimensions.getWidth() * 3/5 + screenDimensions.getHeight() / 6),
                (int) (screenDimensions.getHeight() * 2/15 - screenDimensions.getWidth() / 75) ,
                (int) screenDimensions.getWidth() * 2 / 15,
                (int) screenDimensions.getWidth() * 2 / 15);

    }

}
