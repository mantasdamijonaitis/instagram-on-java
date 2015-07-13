package insta;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

import java.awt.geom.Dimension2D;

public class LayoutMetricsTest {

    @Test
    public void testImageX() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(20, m.getImageMetrics().getX(),5);
    }

    @Test
    public void testImageY() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(70, m.getImageMetrics().getY(),5);
    }

    @Test
    public void testImageBoundsX() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(500, m.getImageMetrics().getWidth(),5);
    }

    @Test
    public void testImageBoundsY() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(860, m.getImageMetrics().getHeight(),5);
    }

    @Test
    public void testCaptionX() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(20, m.getCaptionMetrics().getX(),5);
    }

    @Test
    public void testCaptionY() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(0, m.getCaptionMetrics().getY(),5);
    }

    @Test
    public void testCaptionBoundsX() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(500, m.getCaptionMetrics().getWidth(),5);
    }

    @Test
    public void testCaptionBoundsY() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(100, m.getCaptionMetrics().getHeight(),5);
    }

    @Test
    public void testUploaderImageX() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(666, m.getUploaderImageMetrics().getX(),5);
    }

    @Test
    public void testUploaderImageY() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(153, m.getUploaderImageMetrics().getY(),5);
    }

    @Test
    public void testUploaderImageBoundsX() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(66, m.getUploaderImageMetrics().getWidth(),5);
    }

    @Test
    public void testUploaderImageBoundsY() {
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(66, m.getUploaderImageMetrics().getHeight(),5);
    }

    @Test
    public void testCommenterImageX(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(666, m.getCommenterImageMetrics().getX(),5);
    }

    @Test
    public void testCommenterImageY(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(353, m.getCommenterImageMetrics().getY(),5);
    }

    @Test
    public void testCommenterImageBoundsX(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(33, m.getCommenterImageMetrics().getWidth(),5);
    }

    @Test
    public void testCommenterImageBoundsY(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(33, m.getCommenterImageMetrics().getHeight(),5);
    }

    @Test
    public void testCommentX(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(716, m.getCommentMetrics().getX(),5);
    }

    @Test
    public void testCommentY(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(353, m.getCommentMetrics().getY(),5);
    }

    @Test
    public void testCommentBoundsX(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(500, m.getCommentMetrics().getWidth(),5);
    }

    @Test
    public void testCommentBoundsY(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(100, m.getCommentMetrics().getHeight(),5);
    }

    @Test
    public void testUploaderNameX(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(766,m.getUploaderNameMetrics().getX(),5);
    }

    @Test
    public void testUploaderNameY(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(120,m.getUploaderNameMetrics().getY(),5);
    }

    @Test
    public void testUploaderNameBoundsX(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(132,m.getUploaderNameMetrics().getWidth(),5);
    }

    @Test
    public void testUploaderNameBoundsY(){
        Dimension2D d = new Dimension(1000, 1000);
        LayoutMetrics m = new LayoutMetrics(d);
        assertEquals(133,m.getUploaderNameMetrics().getHeight(),5);
    }

}
