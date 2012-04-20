package net.robinjam.mandelbrot.compute;

import net.robinjam.mandelbrot.Complex;

/**
 * Defines an interface used by the {@link Job} class to instantiate workers.
 * 
 * @author James Robinson
 */
public interface WorkerFactory {

    /**
     * Creates a new worker.
     * 
     * @param row An array of Complex numbers represented by the pixels in the current row.
     * @param max_iterations The maximum number of iterations to perform for each pixel.
     * @return A Worker object that can be used to render the given row.
     */
    public abstract Worker create(Complex[] row, int max_iterations);

}
