package net.robinjam.mandelbrot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
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
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FractalPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                startJob();
            }

        });
    }
    
    public void startJob() {
        if (job != null)
            job.cancel();
        job = new Job(factory, viewport, getWidth(), getHeight(), max_iterations);
        timer.start();
    }
    
    @Override
    public void paint(Graphics graphics) {
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
    
    public static void main(String[] args) {
        final JFrame window = new JFrame("Fractal explorer");

        final FractalPanel mandelbrotPanel = new FractalPanel(800, 640, 1000, MandelbrotWorker.getFactory());
        mandelbrotPanel.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {
                Complex c = mandelbrotPanel.viewport.getPixel(me.getX(), me.getY(), mandelbrotPanel.getWidth(), mandelbrotPanel.getHeight());
                FractalPanel juliaPanel = new FractalPanel(400, 320, 1000, JuliaWorker.getFactory(c));
                JDialog juliaWindow = new JDialog(window, "Julia Set for point " + c);
                juliaWindow.add(juliaPanel);
                juliaWindow.pack();
                juliaWindow.setLocationRelativeTo(window);
                juliaWindow.setResizable(false);
                juliaWindow.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
            
        });
        
        window.add(mandelbrotPanel);
        window.pack();
        
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        window.setVisible(true);
    }

    @Override
    public void selectionCreated(Rectangle selection) {
        double ratio = 1.0 / Math.max((double) selection.width / getWidth(), (double) selection.height / getHeight());
        viewport.setCenter(viewport.getPixel((int) selection.getCenterX(), (int) selection.getCenterY(), getWidth(), getHeight()));
        viewport.setZoom(ratio * viewport.getZoom());
        startJob();
    }

    @Override
    public void selectionMoved() {
        repaint();
    }
    
}
