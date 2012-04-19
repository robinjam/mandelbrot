package net.robinjam.mandelbrot.compute;

import net.robinjam.mandelbrot.Complex;

public class JuliaWorker extends Worker {

    private Complex c;

    public JuliaWorker(Complex[] row, int max_iterations, Complex c) {
        super(row, max_iterations);

        this.c = c;
    }

    @Override
    public MandelbrotWorker.Pixel[] call() {
        MandelbrotWorker.Pixel[] result = new MandelbrotWorker.Pixel[row.length];
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

    public static WorkerFactory getFactory(final Complex c) {
        return new WorkerFactory() {

            @Override
            public Worker create(Complex[] row, int max_iterations) {
                return new JuliaWorker(row, max_iterations, c);
            }

        };
    }

}