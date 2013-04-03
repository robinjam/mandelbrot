package net.robinjam.mandelbrot;

import java.util.Observable;

import net.robinjam.mandelbrot.render.ColourScheme;
import net.robinjam.mandelbrot.render.SmoothColouring;

/**
 * Stores the global render settings, used by every renderer.
 * 
 * @author James Robinson
 */
public class RenderSettings extends Observable {

	private static RenderSettings instance = new RenderSettings();

	private int max_iterations = 1000;
	private ColourScheme colourScheme = new SmoothColouring();

	private RenderSettings() {
	}

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

	public ColourScheme getColourScheme() {
		return colourScheme;
	}

	public void setColourScheme(ColourScheme colourScheme) {
		this.colourScheme = colourScheme;
		setChanged();
		notifyObservers();
	}

}
