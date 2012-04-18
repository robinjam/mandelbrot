package net.robinjam.mandelbrot.compute;

import java.util.concurrent.Callable;
import net.robinjam.mandelbrot.Complex;

public abstract class Worker implements Callable<Worker.Pixel[]> {

    protected Complex[] row;
    protected int max_iterations;

    public Worker(Complex[] row, int max_iterations) {
        this.row = row;
        this.max_iterations = max_iterations;
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
