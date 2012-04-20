package net.robinjam.mandelbrot.compute;

import net.robinjam.mandelbrot.Complex;
import net.robinjam.mandelbrot.Viewport;

/**
 * Handles rendering the Burning Ship fractal.
 * 
 * @author James Robinson
 */
public class BurningShipWorker extends Worker {
    
    private BurningShipWorker(Viewport viewport, int row, int max_iterations) {
        super(viewport, row, max_iterations);
    }
    
    @Override
    public Pixel[] call() {
        Pixel[] result = new Pixel[viewport.getWidth()];
        for (int i = 0; i < viewport.getWidth(); i++) {
            Complex z = viewport.getPixel(i, row);
            Complex zn = new Complex();
            int n;
            for (n = 0; zn.modulusSquared() < 4 && n < max_iterations; n++) {
                zn.setRe(Math.abs(zn.getRe()));
                zn.setIm(Math.abs(zn.getIm()));
                zn.square();
                zn.add(z);
            }
            result[i] = new Pixel(n, zn);
        }
        return result;
    }
    
    /**
     * @return A new {@link WorkerFactory} object that can be used to instantiate Burning Ship workers.
     */
    public static WorkerFactory getFactory() {
        return new WorkerFactory() {

            @Override
            public Worker create(Viewport viewport, int row, int max_iterations) {
                return new BurningShipWorker(viewport, row, max_iterations);
            }

        };
    }
    
}
