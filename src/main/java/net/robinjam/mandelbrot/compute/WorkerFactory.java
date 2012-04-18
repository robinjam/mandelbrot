package net.robinjam.mandelbrot.compute;

import net.robinjam.mandelbrot.Complex;

public interface WorkerFactory {

    public abstract Worker create(Complex[] row, int max_iterations);

}
