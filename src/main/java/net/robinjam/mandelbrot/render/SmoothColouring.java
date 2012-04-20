package net.robinjam.mandelbrot.render;

import java.awt.Color;
import net.robinjam.mandelbrot.Complex;
import net.robinjam.mandelbrot.RenderSettings;

public class SmoothColouring implements ColourScheme {
    
    @Override
    public Color calculateColour(Complex zn, int n) {
        double mu = n - Math.log(Math.log(zn.modulus()) / Math.log(RenderSettings.getInstance().getMaxIterations())) / Math.log(2);
        
        if (n < RenderSettings.getInstance().getMaxIterations())
            return Color.getHSBColor((float) mu * 0.01f + 0.5f, 0.6f, 1.0f);
        else
            return Color.BLACK;
    }
    
}
