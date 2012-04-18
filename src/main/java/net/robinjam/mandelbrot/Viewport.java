package net.robinjam.mandelbrot;

/**
 * Stores the current viewport settings for the Mandelbrot viewer
 * 
 * @author James Robinson
 */
public class Viewport {
    
    // The complex number represented by the pixel at the centre of the viewport
    private Complex center = new Complex();
    
    // The current zoom level
    private double zoom = 1;
    
    /**
     * Calculates the complex number associated with the given pixel, scaled based on the current viewport settings
     * 
     * @param x The x-coordinate of the pixel
     * @param y The y-coordinate of the pixel
     * @param screen_width The width of the screen
     * @param screen_height The height of the screen
     * @return The complex number associated with the given pixel
     */
    public Complex getPixel(int x, int y, int screen_width, int screen_height) {
        // Calculate a scale factor such that the region (-2 - 1.6i) to (2 + 1.6i) is always fully visible when the zoom is 1
        double scale = Math.min(screen_width / 4, screen_height / 3.2);
        
        double re = (x - screen_width / 2) / zoom / scale + center.getRe();
        double im = -(y - screen_height / 2) / zoom / scale + center.getIm();
        return new Complex(re, im);
    }
    
    /**
     * Sets the centre of the viewport to the given point
     */
    public void setCenter(Complex center) {
        this.center = center;
    }
    
    /**
     * Sets the current zoom level to the given value
     * 
     * @param zoom The zoom level, where 1 fully displays the range (-2 - 1.6i) to (2 + 1.6i) and larger numbers represent higher zoom levels (more detail)
     */
    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
    
    /**
     * Multiplies the current zoom level by 2. Note that this method will not cause the Mandelbrot to re-render
     */
    public void zoomIn() {
        zoom *= 2;
    }

    /**
     * Divides the current zoom level by 2. Note that this method will not cause the Mandelbrot to re-render
     */
    public void zoomOut() {
        zoom *= 0.5;
    }
    
    /**
     * @return The complex number represented by the pixel in the centre of the viewport
     */
    public Complex getCenter() {
        return center;
    }
    
    /**
     * @return The current zoom level
     */
    public double getZoom() {
        return zoom;
    }
    
}