package net.robinjam.mandelbrot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Timer;
import net.robinjam.mandelbrot.compute.Job;
import net.robinjam.mandelbrot.compute.WorkerFactory;

/**
 * Renders and displays a fractal.
 * 
 * @author James Robinson
 */
public class FractalPanel extends JPanel implements ActionListener, SelectionListener.Callback, Viewport {
    
    Job job;
    Timer timer;
    BufferedImage image;
    int max_iterations;
    SelectionListener select;
    WorkerFactory factory;
    
    // The complex number represented by the pixel at the centre of the viewport
    private Complex center = new Complex();
    
    // The current zoom level
    private double zoom = 1;
    
    @Override
    public Complex getPixel(int x, int y) {
        // Calculate a scale factor such that the region (-2 - 1.6i) to (2 + 1.6i) is always fully visible when the zoom level is 1
        double scale = Math.min(getWidth() / 4, getHeight() / 3.2);
        
        double re = (x - getWidth() / 2) / zoom / scale + center.getRe();
        double im = -(y - getHeight() / 2) / zoom / scale + center.getIm();
        return new Complex(re, im);
    }
    
    /**
     * Creates a new fractal with the given settings.
     * 
     * @param width The width of the panel.
     * @param height The height of the panel.
     * @param max_iterations The maximum number of iterations for each pixel.
     * @param factory The factory used to create each worker.
     */
    public FractalPanel(int width, int height, int max_iterations, WorkerFactory factory) {
        this.max_iterations = max_iterations;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        
        timer = new Timer(100, this);
        
        select = new SelectionListener(this);
        addMouseListener(select);
        addMouseMotionListener(select);
        this.factory = factory;
    }
    
    /**
     * (re)starts the render job, cancelling the existing one if necessary.
     */
    public void startJob() {
        if (job != null)
            job.cancel();
        job = new Job(factory, this, max_iterations);
        timer.start();
    }
    
    @Override
    public void paint(Graphics graphics) {
        if (job == null) startJob();
        Graphics2D g = (Graphics2D) graphics;
        g.drawImage(image, null, this);
        select.paint(g);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        image = job.getImage();
        repaint();
        if (job.isDone()) {
            timer.stop();
        }
    }

    @Override
    public void selectionCreated(Rectangle selection) {
        double ratio = 1.0 / Math.max((double) selection.width / getWidth(), (double) selection.height / getHeight());
        center = getPixel((int) selection.getCenterX(), (int) selection.getCenterY());
        zoom *= ratio;
        startJob();
    }

    @Override
    public void selectionMoved() {
        repaint();
    }

    void resetZoom() {
        zoom = 1;
        center = new Complex();
        startJob();
    }
    
}
