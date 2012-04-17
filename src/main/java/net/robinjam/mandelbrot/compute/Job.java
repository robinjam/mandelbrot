package net.robinjam.mandelbrot.compute;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.robinjam.mandelbrot.Complex;
import net.robinjam.mandelbrot.Viewport;

public class Job {
    
    int width, height;
    Future<Worker.Pixel[]>[] rows;
    int max_iterations;
    CountDownLatch countdown;
    
    public Job(Viewport viewport, int width, int height, int max_iterations) {
        this.width = width;
        this.height = height;
        this.max_iterations = max_iterations;
        
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        rows = new Future[height];
        countdown = new CountDownLatch(height);
        
        for (int y = 0; y < height; y++) {
            Complex[] row = new Complex[width];
            for (int x = 0; x < width; x++) {
                row[x] = viewport.getPixel(x, y, width, height);
            }
            rows[y] = service.submit(new Worker(row, max_iterations, countdown));
        }
        
        service.shutdown();
    }
    
    public BufferedImage getImage() {
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y < rows.length; y++) {
            if (!rows[y].isDone()) continue;
            Worker.Pixel[] row = null;
            
            try {
                row = rows[y].get();
            } catch (InterruptedException ex) {
                Logger.getLogger(Job.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(Job.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for (int x = 0; x < row.length; x++) {
                result.setRGB(x, y, calculateColour(row[x].getZn(), row[x].getN(), max_iterations).getRGB());
            }
        }
        
        return result;
    }
    
    public double getProgress() {
        return (double) countdown.getCount() / rows.length;
    }
    
    public boolean isDone() {
        return countdown.getCount() == 0;
    }
    
    private Color calculateColour(Complex zn, int n, int max_iterations) {
        double mu = n - Math.log(Math.log(zn.modulus()) / Math.log(max_iterations)) / Math.log(2);
        
        if (n < max_iterations)
            return Color.getHSBColor((float) mu * 0.01f + 0.5f, 0.6f, 1.0f);
        else
            return Color.BLACK;
    }
    
}
