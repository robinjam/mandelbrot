package net.robinjam.mandelbrot.compute;

import net.robinjam.mandelbrot.Viewport;

/**
 * Defines an interface used by the {@link Job} class to instantiate workers.
 * 
 * @author James Robinson
 */
public interface WorkerFactory {

    /**
     * Creates a new worker.
     * 
     * @param viewport The viewport object used to transform pixel coordinates to complex numbers.
     * @param row The row number for the current row.
     * @return A Worker object that can be used to render the given row.
     */
    public abstract Worker create(Viewport viewport, int row);

}
