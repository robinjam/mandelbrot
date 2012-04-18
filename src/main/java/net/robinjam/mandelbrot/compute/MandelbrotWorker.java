package net.robinjam.mandelbrot.compute;

import net.robinjam.mandelbrot.Complex;

public class MandelbrotWorker extends Worker {
    
    public MandelbrotWorker(Complex[] row, int max_iterations) {
        super(row, max_iterations);
    }
    
    @Override
    public MandelbrotWorker.Pixel[] call() {
        MandelbrotWorker.Pixel[] result = new MandelbrotWorker.Pixel[row.length];
        for (int i = 0; i < row.length; i++) {
            Complex z = row[i];
            Complex zn = new Complex();
            int n;
            for (n = 0; zn.modulusSquared() < 4 && n < max_iterations; n++) {
                zn.square();
                zn.add(z);
            }
            result[i] = new Pixel(n, zn);
        }
        return result;
    }
    
    public static WorkerFactory getFactory() {
        return new WorkerFactory() {

            @Override
            public Worker create(Complex[] row, int max_iterations) {
                return new MandelbrotWorker(row, max_iterations);
            }

        };
    }
    
}
