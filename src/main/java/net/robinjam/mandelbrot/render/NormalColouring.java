package net.robinjam.mandelbrot.render;

import java.awt.Color;
import net.robinjam.mandelbrot.Complex;
import net.robinjam.mandelbrot.RenderSettings;

public class NormalColouring implements ColourScheme {
    
    @Override
    public Color calculateColour(Complex zn, int n) {
        if (n < RenderSettings.getInstance().getMaxIterations())
            return Color.getHSBColor(n * 0.01f + 0.5f, 0.6f, 1.0f);
        else
            return Color.BLACK;
    }
    
}
