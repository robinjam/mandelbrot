package net.robinjam.mandelbrot.render;

import java.awt.Color;
import net.robinjam.mandelbrot.Complex;

public interface ColourScheme {
    
    public Color calculateColour(Complex zn, int n);
    
}
