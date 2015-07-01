package insta;



import org.junit.Assert;
import org.junit.Test;

public class LayoutMetricsTest {

	@Test
	public void testCalculateImageMetrics(){
		
		LayoutMetrics metrics = new LayoutMetrics(1000,1000);
		Assert.assertEquals(20.0, metrics.imageBounds.getX(),0.1);
		
	}
	
	@Test
	public void testCalculateImageMetricsBigScreen(){
		
		LayoutMetrics metrics = new LayoutMetrics(20000,20000);
		Assert.assertEquals(400.0, metrics.imageBounds.getX(),0.1);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeScreenResolution(){
		
		LayoutMetrics metrics = new LayoutMetrics(-1000,0);
		
	}
	@Test
	public void testCalculateCommenterImageMetrics(){
		
		LayoutMetrics metrics = new LayoutMetrics(1000,1000);
		Assert.assertEquals(20.0, metrics.imageBounds.getX(),0.1);
		
	}
	
	@Test
	public void testCalculateCommenterImageBigScreen(){
		
		LayoutMetrics metrics = new LayoutMetrics(20000,20000);
		Assert.assertEquals(400.0, metrics.imageBounds.getX(),0.1);
		
	}
	
}
