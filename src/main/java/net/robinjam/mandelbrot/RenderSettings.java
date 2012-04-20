package net.robinjam.mandelbrot;

import java.util.Observable;

/**
 * Stores the global render settings, used by every renderer.
 * 
 * @author James Robinson
 */
public class RenderSettings extends Observable {
    
    private static RenderSettings instance = new RenderSettings();
    
    private int max_iterations = 1000;
    
    private RenderSettings() {}
    
    public static RenderSettings getInstance() {
        return instance;
    }
    
    public int getMaxIterations() {
        return max_iterations;
    }
    
    public void setMaxIterations(int max_iterations) {
        this.max_iterations = max_iterations;
        setChanged();
        notifyObservers();
    }
    
}
