package net.robinjam.mandelbrot.compute;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import net.robinjam.mandelbrot.Complex;
import net.robinjam.mandelbrot.RenderSettings;
import net.robinjam.mandelbrot.Viewport;

/**
 * Handles rendering a fractal using a multithreaded implementation of the normalised iteration count algorithm.
 * 
 * @author James Robinson
 */
public class Renderer {
    
    private static ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    
    private Viewport viewport;
    private Future<Worker.Pixel[]>[] rows;
    
    /**
     * Starts a render job with a number of threads equal to the number of available processor cores.
     * 
     * @param factory A {@link WorkerFactory} used to instantiate the workers used to render the fractal.
     * @param viewport The current viewport settings.
     */
    public Renderer(final WorkerFactory factory, final Viewport viewport) {
        this.viewport = viewport;
        rows = new Future[viewport.getHeight()];

        // Iterate over every row in the fractal
        for (int y = 0; y < viewport.getHeight(); y++) {
            // Add the worker to the queue
            rows[y] = service.submit(factory.create(viewport, y));
        }
    }
    
    /**
     * Draws only the rows that have completely finished rendering to a BufferedImage and returns the result without blocking.
     * 
     * @return A BufferedImage containing the fully rendered rows.
     */
    public BufferedImage getImage() {
        BufferedImage result = new BufferedImage(viewport.getWidth(), viewport.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y < rows.length; y++) {
            // If the row is not finished rendering, skip it
            if (!rows[y].isDone()) continue;
            
            MandelbrotWorker.Pixel[] row = null;
            
            try {
                row = rows[y].get();
            } catch (InterruptedException ex) {
                continue;
            } catch (ExecutionException ex) {
                continue;
            }
            
            // Draw each pixel in the current row to the image
            for (int x = 0; x < row.length; x++) {
                result.setRGB(x, y, calculateColour(row[x].getZn(), row[x].getN(), RenderSettings.getInstance().getMaxIterations()).getRGB());
            }
        }
        
        return result;
    }
    
    /**
     * Cancels the render task.
     */
    public void cancel() {
        for (Future row : rows)
            row.cancel(false);
    }
    
    /**
     * @return True if each row has finished processing, false otherwise.
     */
    public boolean isDone() {
        int num_complete = 0;
        for (Future f : rows)
            if (f.isDone()) num_complete++;
        return num_complete == rows.length;
    }
    
    private Color calculateColour(Complex zn, int n, int max_iterations) {
        double mu = n - Math.log(Math.log(zn.modulus()) / Math.log(max_iterations)) / Math.log(2);
        
        if (n < max_iterations)
            return Color.getHSBColor((float) mu * 0.01f + 0.5f, 0.6f, 1.0f);
        else
            return Color.BLACK;
    }
    
}
