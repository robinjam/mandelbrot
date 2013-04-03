package net.robinjam.mandelbrot.compute;

import net.robinjam.mandelbrot.Complex;
import net.robinjam.mandelbrot.RenderSettings;
import net.robinjam.mandelbrot.Viewport;

/**
 * Handles rendering the Mandelbrot set.
 * 
 * @author James Robinson
 */
public class MandelbrotWorker extends Worker {

	private MandelbrotWorker(Viewport viewport, int row) {
		super(viewport, row);
	}

	@Override
	public Pixel[] call() {
		Pixel[] result = new Pixel[viewport.getWidth()];
		for (int i = 0; i < viewport.getWidth(); i++) {
			Complex z = viewport.getPixel(i, row);
			Complex zn = new Complex();
			int n;
			for (n = 0; zn.modulusSquared() < 4 && n < RenderSettings.getInstance().getMaxIterations(); n++) {
				zn.square();
				zn.add(z);
			}
			result[i] = new Pixel(n, zn);
		}
		return result;
	}

	/**
	 * @return A new {@link WorkerFactory} object that can be used to instantiate Mandelbrot workers.
	 */
	public static WorkerFactory getFactory() {
		return new WorkerFactory() {

			@Override
			public Worker create(Viewport viewport, int row) {
				return new MandelbrotWorker(viewport, row);
			}

		};
	}

}
