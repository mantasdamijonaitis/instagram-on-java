package insta;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

import java.awt.geom.Dimension2D;

public class LayoutMetricsTest {

    @Test
    public void testImageX() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(20, m.getImageMetrics(d).getX(),5);
    }

    @Test
    public void testImageY() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(70, m.getImageMetrics(d).getY(),5);
    }

    @Test
    public void testImageBoundsX() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(500, m.getImageMetrics(d).getWidth(),5);
    }

    @Test
    public void testImageBoundsY() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(860, m.getImageMetrics(d).getHeight(),5);
    }

    @Test
    public void testCaptionX() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(20, m.getCaptionMetrics(d).getX(),5);
    }

    @Test
    public void testCaptionY() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(0, m.getCaptionMetrics(d).getY(),5);
    }

    @Test
    public void testCaptionBoundsX() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(500, m.getCaptionMetrics(d).getWidth(),5);
    }

    @Test
    public void testCaptionBoundsY() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(100, m.getCaptionMetrics(d).getHeight(),5);
    }

    @Test
    public void testUploaderImageX() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(666, m.getUploaderImageMetrics(d).getX(),5);
    }

    @Test
    public void testUploaderImageY() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(153, m.getUploaderImageMetrics(d).getY(),5);
    }

    @Test
    public void testUploaderImageBoundsX() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(66, m.getUploaderImageMetrics(d).getWidth(),5);
    }

    @Test
    public void testUploaderImageBoundsY() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(66, m.getUploaderImageMetrics(d).getHeight(),5);
    }

    @Test
    public void testCommenterImageX(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(666, m.getCommenterImageMetrics(d).getX(),5);
    }

    @Test
    public void testCommenterImageY(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(353, m.getCommenterImageMetrics(d).getY(),5);
    }

    @Test
    public void testCommenterImageBoundsX(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(33, m.getCommenterImageMetrics(d).getWidth(),5);
    }

    @Test
    public void testCommenterImageBoundsY(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(33, m.getCommenterImageMetrics(d).getHeight(),5);
    }

    @Test
    public void testCommentX(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(716, m.getCommentMetrics(d).getX(),5);
    }

    @Test
    public void testCommentY(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(353, m.getCommentMetrics(d).getY(),5);
    }

    @Test
    public void testCommentBoundsX(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(500, m.getCommentMetrics(d).getWidth(),5);
    }

    @Test
    public void testCommentBoundsY(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(100, m.getCommentMetrics(d).getHeight(),5);
    }

    @Test
    public void testUploaderNameX(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(766,m.getUploaderNameMetrics(d).getX(),5);
    }

    @Test
    public void testUploaderNameY(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(120,m.getUploaderNameMetrics(d).getY(),5);
    }

    @Test
    public void testUploaderNameBoundsX(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(132,m.getUploaderNameMetrics(d).getWidth(),5);
    }

    @Test
    public void testUploaderNameBoundsY(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics();
        assertEquals(133,m.getUploaderNameMetrics(d).getHeight(),5);
    }

}
