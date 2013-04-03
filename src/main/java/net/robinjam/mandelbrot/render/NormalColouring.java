package net.robinjam.mandelbrot.render;

import java.awt.Color;

import net.robinjam.mandelbrot.RenderSettings;
import net.robinjam.mandelbrot.compute.Worker;

/**
 * The "normal" colouring algorithm. Does not apply smooth shading or special effects.
 * 
 * @author James Robinson
 */
public class NormalColouring implements ColourScheme {

	@Override
	public Color calculateColour(Worker.Pixel p) {
		if (p.getN() < RenderSettings.getInstance().getMaxIterations())
			return Color.getHSBColor(p.getN() * 0.01f + 0.5f, 0.6f, 1.0f);
		else
			return Color.BLACK;
	}

}
