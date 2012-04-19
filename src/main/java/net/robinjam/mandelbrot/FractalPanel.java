package net.robinjam.mandelbrot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import net.robinjam.mandelbrot.compute.Job;
import net.robinjam.mandelbrot.compute.JuliaWorker;
import net.robinjam.mandelbrot.compute.MandelbrotWorker;
import net.robinjam.mandelbrot.compute.WorkerFactory;

public class FractalPanel extends JPanel implements ActionListener, SelectionListener.Callback {
    
    Job job;
    Timer timer;
    BufferedImage image;
    Viewport viewport;
    int max_iterations;
    SelectionListener select;
    WorkerFactory factory;
    
    public FractalPanel(int width, int height, int max_iterations, WorkerFactory factory) {
        this.max_iterations = max_iterations;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        
        viewport = new Viewport();
        
        timer = new Timer(100, this);
        
        select = new SelectionListener(this);
        addMouseListener(select);
        addMouseMotionListener(select);
        this.factory = factory;
    }
    
    public void startJob() {
        if (job != null)
            job.cancel();
        job = new Job(factory, viewport, getWidth(), getHeight(), max_iterations);
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
        viewport.setCenter(viewport.getPixel((int) selection.getCenterX(), (int) selection.getCenterY(), getWidth(), getHeight()));
        viewport.zoom(ratio);
        startJob();
    }

    @Override
    public void selectionMoved() {
        repaint();
    }
    
}
