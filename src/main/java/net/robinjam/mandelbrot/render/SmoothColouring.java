package net.robinjam.mandelbrot.render;

import java.awt.Color;
import net.robinjam.mandelbrot.RenderSettings;
import net.robinjam.mandelbrot.compute.Worker;

/**
 * Applies smooth colouring using the output of the normalised iteration count algorithm.
 * 
 * @author James Robinson
 */
public class SmoothColouring implements ColourScheme {
    
    @Override
    public Color calculateColour(Worker.Pixel p) {
        double mu = p.getN() - Math.log(Math.log(p.getZn().modulus()) / Math.log(RenderSettings.getInstance().getMaxIterations())) / Math.log(2);
        
        if (p.getN() < RenderSettings.getInstance().getMaxIterations())
            return Color.getHSBColor((float) mu * 0.01f + 0.5f, 0.6f, 1.0f);
        else
            return Color.BLACK;
    }
    
}
