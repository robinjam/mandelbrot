package net.robinjam.mandelbrot.compute;

import net.robinjam.mandelbrot.Complex;

/**
 * Handles rendering a Julia set.
 * 
 * @author James Robinson
 */
public class JuliaWorker extends Worker {

    private Complex c;

    private JuliaWorker(Complex[] row, int max_iterations, Complex c) {
        super(row, max_iterations);

        this.c = c;
    }

    @Override
    public Pixel[] call() {
        Pixel[] result = new Pixel[row.length];
        for (int i = 0; i < row.length; i++) {
            Complex z = row[i];
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
            public Worker create(Complex[] row, int max_iterations) {
                return new JuliaWorker(row, max_iterations, c);
            }

        };
    }

}
