package net.robinjam.mandelbrot.compute;

import net.robinjam.mandelbrot.Complex;

/**
 * Handles rendering the Burning Ship fractal.
 * 
 * @author James Robinson
 */
public class BurningShipWorker extends Worker {
    
    private BurningShipWorker(Complex[] row, int max_iterations) {
        super(row, max_iterations);
    }
    
    @Override
    public Pixel[] call() {
        Pixel[] result = new Pixel[row.length];
        for (int i = 0; i < row.length; i++) {
            Complex z = row[i];
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
            public Worker create(Complex[] row, int max_iterations) {
                return new BurningShipWorker(row, max_iterations);
            }

        };
    }
    
}
