package net.robinjam.mandelbrot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import net.robinjam.mandelbrot.compute.Job;

public class MandelbrotGUI extends JPanel implements ActionListener, SelectionListener.Callback {
    
    Job job;
    Timer timer;
    BufferedImage image;
    Viewport viewport;
    int max_iterations;
    SelectionListener select;
    
    public MandelbrotGUI(int width, int height, int max_iterations) {
        this.max_iterations = max_iterations;
        setSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        
        viewport = new Viewport();
        
        timer = new Timer(100, this);
        
        select = new SelectionListener(this);
        addMouseListener(select);
        addMouseMotionListener(select);
        startJob();
    }
    
    private void startJob() {
        job = new Job(viewport, getWidth(), getHeight(), max_iterations);
        timer.start();
    }
    
    /*@Override
    public void update(Graphics g) {
        paint(g);
    }*/
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, null, this);
        select.paint(g);
        //for (Component c : getComponents())
        //    c.paint(g);
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
        JFrame window = new JFrame("Fractal explorer");
        
        window.add(new MandelbrotGUI(800, 600, 1000));
        window.pack();
        
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        window.setVisible(true);
    }

    public void selected(Point lower, Point upper) {
        int cx = (upper.x + lower.x) / 2;
        int cy = (upper.y + lower.y) / 2;
        double dx = (double) (upper.x - lower.x) / getWidth();
        double dy = (double) (upper.y - lower.y) / getHeight();
        double ratio = 1.0 / Math.max(dx, dy);
        viewport.setCenter(viewport.getPixel(cx, cy, getWidth(), getHeight()));
        viewport.setZoom(ratio * viewport.getZoom());
        startJob();
    }

    @Override
    public void moved() {
        repaint();
    }
    
}
