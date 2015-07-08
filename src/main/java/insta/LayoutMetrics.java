package insta;

import java.awt.*;
import java.awt.geom.Dimension2D;

public class LayoutMetrics {

    public LayoutMetrics() {

    }

    Rectangle getImageMetrics(Dimension2D screenDimensions) {

        int imageX = (int) screenDimensions.getWidth() / 50;
        int imageY = imageX + (int) screenDimensions.getHeight() / 20;

        int imageBoundsX = ((int) screenDimensions.getWidth() / 2);
        int imageBoundsY = (int) screenDimensions.getHeight() - imageY * 2;

        return new Rectangle((int) screenDimensions.getWidth() / 50, imageY, imageBoundsX, imageBoundsY);

    }

    Rectangle getCaptionMetrics(Dimension2D screenDimensions) {

        int captionX = (int) getImageMetrics(screenDimensions).getX();
        int captionY = 0;

        int captionBoundsX = (int) screenDimensions.getWidth() / 2;
        int captionBoundsY = (int) screenDimensions.getHeight() / 10;

        return new Rectangle(captionX, captionY, captionBoundsX, captionBoundsY);

    }

    Rectangle getUploaderImageMetrics(Dimension2D screenDimensions) {

        return new Rectangle((int) (screenDimensions.getWidth() / 2 + screenDimensions.getHeight() / 6),
                (int) getImageMetrics(screenDimensions).getY() + (int) screenDimensions.getHeight() / 12,
                (int) screenDimensions.getWidth() / 15,
                (int) screenDimensions.getWidth() / 15);

    }

    Rectangle getCommenterImageMetrics(Dimension2D screenDimensions) {

        int commenterImageX = (int) getUploaderImageMetrics(screenDimensions).getX();
        int commenterImageY = (int) getUploaderImageMetrics(screenDimensions).getY() + (int) screenDimensions.getHeight() / 5;

        int commenterImageBoundsX = (int) getUploaderImageMetrics(screenDimensions).getWidth() / 2;
        int commenterImageBoundsY = commenterImageBoundsX;

        return new Rectangle(commenterImageX, commenterImageY, commenterImageBoundsX, commenterImageBoundsY);

    }

    Rectangle getCommentMetrics(Dimension2D screenDimensions) {
        return new Rectangle(
                (int) getCommenterImageMetrics(screenDimensions).getX() + (int) screenDimensions.getWidth() / 20,
                (int) getCommenterImageMetrics(screenDimensions).getY(),
                (int) getCaptionMetrics(screenDimensions).getWidth(),
                (int) getCaptionMetrics(screenDimensions).getHeight()
        );


    }

    Rectangle getUploaderNameMetrics(Dimension2D screenDimensions) {

        int uploaderNameX = (int) getUploaderImageMetrics(screenDimensions).getX() + (int) screenDimensions.getWidth() / 10;
        int uploaderNameY = (int) getUploaderImageMetrics(screenDimensions).getY() - (int) screenDimensions.getWidth() / 30;

        int uploaderNameBoundsX = (int) getUploaderImageMetrics(screenDimensions).getWidth() * 2;
        int uploaderNameBoundsY = (int) getUploaderImageMetrics(screenDimensions).getHeight() * 2;

        return new Rectangle(uploaderNameX, uploaderNameY, uploaderNameBoundsX, uploaderNameBoundsY);

    }

}
