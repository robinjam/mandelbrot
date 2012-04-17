package net.robinjam.mandelbrot.compute;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import net.robinjam.mandelbrot.Complex;

public class Worker implements Callable<Worker.Pixel[]> {
    
    private Complex[] row;
    private int max_iterations;
    private CountDownLatch countdown;
    
    public Worker(Complex[] row, int max_iterations, CountDownLatch countdown) {
        this.row = row;
        this.max_iterations = max_iterations;
        this.countdown = countdown;
    }
    
    @Override
    public Worker.Pixel[] call() {
        Worker.Pixel[] result = new Worker.Pixel[row.length];
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
        countdown.countDown();
        return result;
    }
    
    public static class Pixel {
        
        int n;
        Complex zn;
        
        Pixel(int n, Complex zn) {
            this.n = n;
            this.zn = zn;
        }
        
        public int getN() {
            return n;
        }
        
        public Complex getZn() {
            return zn;
        }
        
    }
    
}
