package insta;

import java.awt.*;

public class LayoutMetrics {

	public LayoutMetrics(){
		
	}

	Rectangle getImageMetrics(Point screenDimensions){

		int imageX = (int)screenDimensions.getX() / 50;
		int imageY = imageX + (int)screenDimensions.getY() / 20;

		int imageBoundsX = ((int)screenDimensions.getX() / 2);
		int imageBoundsY = (int)screenDimensions.getY() - imageY * 2;
		
		return new Rectangle(imageX, imageY, imageBoundsX, imageBoundsY);
		
	}
	
	Rectangle getCaptionMetrics(Point screenDimensions){

        int captionX = (int)getImageMetrics(screenDimensions).getX();
		int captionY = 0;

		int captionBoundsX = (int)screenDimensions.getX() / 2;
		int captionBoundsY = (int)screenDimensions.getY() / 10;

		return new Rectangle(captionX, captionY, captionBoundsX, captionBoundsY);
		
	}
	
	Rectangle getUploaderImageMetrics(Point screenDimensions){
		
		int uploaderImageX = (int)(screenDimensions.getX() / 2 + screenDimensions.getX() / 6);
		int uploaderImageY = (int)getImageMetrics(screenDimensions).getY() + (int)screenDimensions.getY() / 12;
		
		int uploaderImageBoundsX = (int)screenDimensions.getX() / 15;
		int uploaderImageBoundsY = uploaderImageBoundsX;
		
		return new Rectangle(uploaderImageX, uploaderImageY, uploaderImageBoundsX, uploaderImageBoundsY);
		
	}
	
	Rectangle getCommenterImageMetrics(Point screenDimensions){
		
		int commenterImageX = (int)getUploaderImageMetrics(screenDimensions).getX();
		int commenterImageY = (int)getUploaderImageMetrics(screenDimensions).getY() + (int)screenDimensions.getY() / 5;
		
		int commenterImageBoundsX = (int)getUploaderImageMetrics(screenDimensions).getWidth() / 2;
		int commenterImageBoundsY = commenterImageBoundsX;
		
		return new Rectangle(commenterImageX, commenterImageY, commenterImageBoundsX, commenterImageBoundsY);
		
	}
	
	Rectangle getCommentMetrics(Point screenDimensions){
		
		int commentX = (int)getCommenterImageMetrics(screenDimensions).getX() + (int)screenDimensions.getX() / 20;
		int commentY = (int)getCommenterImageMetrics(screenDimensions).getY();
		
		int commentBoundsX = (int)getCaptionMetrics(screenDimensions).getWidth();
		int commentBoundsY = (int)getCaptionMetrics(screenDimensions).getHeight();
		
		return new Rectangle(commentX,commentY,commentBoundsX,commentBoundsY);
		
		
	}
	
	Rectangle getUploaderNameMetrics(Point screenDimensions){
		
		int uploaderNameX = (int)getUploaderImageMetrics(screenDimensions).getX() + (int)screenDimensions.getX() / 10;
		int uploaderNameY = (int)getUploaderImageMetrics(screenDimensions).getY() - (int)screenDimensions.getX() / 30;
		 
		int uploaderNameBoundsX = (int)getUploaderImageMetrics(screenDimensions).getWidth() * 2;
		int uploaderNameBoundsY = (int)getUploaderImageMetrics(screenDimensions).getHeight() * 2;
		
		return new Rectangle(uploaderNameX, uploaderNameY, uploaderNameBoundsX, uploaderNameBoundsY);
		
	}
	
}
