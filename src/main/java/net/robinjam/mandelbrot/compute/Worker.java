package net.robinjam.mandelbrot.compute;

import java.util.concurrent.Callable;

import net.robinjam.mandelbrot.Complex;
import net.robinjam.mandelbrot.Viewport;

/**
 * Defines the basic functionality that the workers for each type of fractal must implement.
 * 
 * @author James Robinson
 */
public abstract class Worker implements Callable<Worker.Pixel[]> {

	protected Viewport viewport;
	protected int row;

	protected Worker(Viewport viewport, int row) {
		this.viewport = viewport;
		this.row = row;
	}

	/**
	 * Stores the results of computation for a single pixel.
	 */
	public static class Pixel {

		int n;
		Complex zn;

		Pixel(int n, Complex zn) {
			this.n = n;
			this.zn = zn;
		}

		public int getN() {
			return n;
		}

		public Complex getZn() {
			return zn;
		}

	}

}
