package net.robinjam.mandelbrot.compute;

import net.robinjam.mandelbrot.Complex;
import net.robinjam.mandelbrot.Viewport;

/**
 * Handles rendering a Julia set.
 * 
 * @author James Robinson
 */
public class JuliaWorker extends Worker {

    private Complex c;

    private JuliaWorker(Viewport viewport, int row, int max_iterations, Complex c) {
        super(viewport, row, max_iterations);

        this.c = c;
    }

    @Override
    public Pixel[] call() {
        Pixel[] result = new Pixel[viewport.getWidth()];
        for (int i = 0; i < viewport.getWidth(); i++) {
            Complex z = viewport.getPixel(i, row);
            Complex zn = z;
            int n;
            for (n = 0; zn.modulusSquared() < 4 && n < max_iterations; n++) {
                zn.square();
                zn.add(c);
            }
            result[i] = new Pixel(n, zn);
        }
        return result;
    }

    /**
     * @param c The Complex number for the Julia set.
     * @return A new {@link WorkerFactory} object that can be used to instantiate Julia workers for the given Complex number.
     */
    public static WorkerFactory getFactory(final Complex c) {
        return new WorkerFactory() {

            @Override
            public Worker create(Viewport viewport, int row, int max_iterations) {
                return new JuliaWorker(viewport, row, max_iterations, c);
            }

        };
    }

}
