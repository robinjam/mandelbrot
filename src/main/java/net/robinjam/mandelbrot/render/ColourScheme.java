package net.robinjam.mandelbrot.render;

import java.awt.Color;

import net.robinjam.mandelbrot.compute.Worker;

/**
 * Defines an interface that each colour scheme should implement.
 * 
 * @author James Robinson
 */
public interface ColourScheme {

	/**
	 * Calculates the colour for the given pixel using the current colouring algorithm.
	 * 
	 * @param p The pixel data to calculate the colour for.
	 * @return The colour for the current pixel.
	 */
	public Color calculateColour(Worker.Pixel p);

}
