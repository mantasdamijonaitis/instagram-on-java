package insta;

import java.awt.*;

public class LayoutMetrics {
	
	int screenHeight;
	int screenWidth;

	public LayoutMetrics(int screenWidth, int screenHeight){
		
		
		
			this.screenWidth = screenWidth;
			this.screenHeight = screenHeight;
			
			
			if(screenWidth <= 0 && screenHeight <= 0){ 
				
				throw new IllegalArgumentException("Your screen resolution is nonsense xD");
				
			}
		
		calculateImageMetrics();
		calculateCaptionMetrics();
		calculateUploaderImageMetrics();
		calculateCommenterImageMetrics();
		calculateCommentMetrics();
		calculateUploaderNameMetrics();
		
		
	}
	
	Rectangle imageBounds;
	
	Rectangle captionBounds;
	
	Rectangle uploaderImageBounds;
	
	Rectangle uploaderNameBounds;
	
	Rectangle commenterImageBounds;
	
	Rectangle commentBounds;

	void calculateImageMetrics(){

		int imageX = this.screenWidth / 50;
		int imageY = imageX + this.screenHeight / 20;

		int imageBoundsX = (this.screenWidth / 2);
		int imageBoundsY = this.screenHeight - imageY * 2;
		
		imageBounds = new Rectangle(imageX, imageY, imageBoundsX, imageBoundsY);
		
	}
	
	void calculateCaptionMetrics(){

		int captionX = (int)this.imageBounds.getX();
		int captionY = 0;

		int captionBoundsX = this.screenWidth / 2;
		int captionBoundsY = this.screenHeight / 10;

		captionBounds = new Rectangle(captionX, captionY, captionBoundsX, captionBoundsY);
		
	}
	
	void calculateUploaderImageMetrics(){
		
		int uploaderImageX = this.screenWidth / 2 + this.screenWidth / 6;
		int uploaderImageY = (int)imageBounds.getY() + this.screenHeight / 12;
		
		int uploaderImageBoundsX = this.screenWidth / 15;
		int uploaderImageBoundsY = uploaderImageBoundsX;
		
		uploaderImageBounds = new Rectangle(uploaderImageX, uploaderImageY, uploaderImageBoundsX, uploaderImageBoundsY);
		
	}
	
	void calculateCommenterImageMetrics(){
		
		int commenterImageX = (int)uploaderImageBounds.getX();
		int commenterImageY = (int)uploaderImageBounds.getY() + this.screenHeight / 5;
		
		int commenterImageBoundsX = (int)uploaderImageBounds.getWidth() / 2;
		int commenterImageBoundsY = commenterImageBoundsX;
		
		commenterImageBounds = new Rectangle(commenterImageX, commenterImageY, commenterImageBoundsX, commenterImageBoundsY);
		
	}
	
	void calculateCommentMetrics(){
		
		int commentX = (int)commenterImageBounds.getX() + this.screenWidth / 20;
		int commentY = (int)commenterImageBounds.getY();
		
		int commentBoundsX = (int)captionBounds.getWidth();
		int commentBoundsY = (int)captionBounds.getHeight();
		
		commentBounds = new Rectangle(commentX,commentY,commentBoundsX,commentBoundsY);
		
		
	}
	
	void calculateUploaderNameMetrics(){
		
		int uploaderNameX = (int)uploaderImageBounds.getX() + this.screenWidth / 10;
		int uploaderNameY = (int)uploaderImageBounds.getY() - this.screenWidth / 30;
		 
		int uploaderNameBoundsX = (int)uploaderImageBounds.getWidth() * 2;
		int uploaderNameBoundsY = (int)uploaderImageBounds.getHeight() * 2;
		
		uploaderNameBounds = new Rectangle(uploaderNameX, uploaderNameY, uploaderNameBoundsX, uploaderNameBoundsY);
		
	}
	
}
