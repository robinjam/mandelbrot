package net.robinjam.mandelbrot;

/**
 * Stores the current viewport settings for the Mandelbrot viewer.
 * 
 * @author James Robinson
 */
public interface Viewport {

	/**
	 * Calculates the complex number associated with the given pixel, scaled based on the current viewport settings.
	 * 
	 * @param x The x-coordinate of the pixel.
	 * @param y The y-coordinate of the pixel.
	 * @return The complex number associated with the given pixel.
	 */
	public Complex getPixel(int x, int y);

	/**
	 * @return The width of the viewport.
	 */
	public int getWidth();

	/**
	 * @return The height of the viewport.
	 */
	public int getHeight();

}