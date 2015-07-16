package insta;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Dimension2D;

@Component
public class LayoutMetrics {

    private final Dimension2D screenDimensions;

    public LayoutMetrics(){

        this.screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();

    }

    public LayoutMetrics(Dimension2D screenDimensions) {

        this.screenDimensions = screenDimensions;

    }

    Rectangle getBackButtonMetrics()

    Rectangle getImageMetrics() {

        return new Rectangle(
                (int) screenDimensions.getWidth() / 50,
                (int) screenDimensions.getWidth() / 50 + (int) screenDimensions.getHeight() / 20,
                ((int) screenDimensions.getWidth() / 2),
                (int) (screenDimensions.getHeight() * 9/10 - screenDimensions.getWidth() / 25));

    }

    Rectangle getCaptionMetrics() {

        return new Rectangle(
                (int) screenDimensions.getWidth() / 50,
                0,
                (int) screenDimensions.getWidth() / 2,
                (int) screenDimensions.getHeight() / 10);

    }

    Rectangle getUploaderImageMetrics() {

        return new Rectangle(
                (int) (screenDimensions.getWidth() / 2 + screenDimensions.getHeight() / 6),
                (int) screenDimensions.getWidth() / 50 + (int) screenDimensions.getHeight() / 20 + (int) screenDimensions.getHeight() / 12,
                (int) screenDimensions.getWidth() / 15,
                (int) screenDimensions.getWidth() / 15);

    }

    Rectangle getCommenterImageMetrics() {

        return new Rectangle(
                (int)(screenDimensions.getWidth() / 2 + screenDimensions.getHeight() / 6),
                (int)screenDimensions.getWidth() / 50 + (int) screenDimensions.getHeight() * 1/3,
                (int)(screenDimensions.getWidth() / 30),
                (int)(screenDimensions.getWidth() / 30));

    }

    Rectangle getCommentMetrics() {

        return new Rectangle(
                (int)(screenDimensions.getWidth() * 11 / 20 + screenDimensions.getHeight()  / 6),
                (int)screenDimensions.getWidth() / 50 + (int) screenDimensions.getHeight() * 1/3,
                (int) screenDimensions.getWidth() / 2,
                (int) screenDimensions.getHeight() / 10
        );


    }

    Rectangle getUploaderNameMetrics() {

        return new Rectangle(
                (int) (screenDimensions.getWidth() * 3/5 + screenDimensions.getHeight() / 6),
                (int) (screenDimensions.getHeight() * 2/15 - screenDimensions.getWidth() / 75) ,
                (int) screenDimensions.getWidth() * 2 / 15,
                (int) screenDimensions.getWidth() * 2 / 15);

    }

}
