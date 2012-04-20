package net.robinjam.mandelbrot;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import net.robinjam.mandelbrot.compute.JuliaWorker;
import net.robinjam.mandelbrot.compute.MandelbrotWorker;

/**
 * The main window for the GUI, and the entry point for the application.
 *
 * @author James Robinson
 */
public class MainWindow extends JFrame implements MouseListener, MainMenu.Callback {
    
    FractalPanel mandelbrotPanel = new FractalPanel(800, 640, 1000, MandelbrotWorker.getFactory());

    MainWindow() {
        super("Fractal Viewer");

        mandelbrotPanel.addMouseListener(this);
        add(mandelbrotPanel);
        pack();

        setMenuBar(new MainMenu(this));
        setResizable(false);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        final Complex c = mandelbrotPanel.viewport.getPixel(me.getX(), me.getY(), mandelbrotPanel.getWidth(), mandelbrotPanel.getHeight());
        new JuliaWindow(c);
    }


    @Override
    public void maxIterationsChanged(int max_iterations) {
        mandelbrotPanel.max_iterations = max_iterations;
        mandelbrotPanel.startJob();
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
    
    // Unused listeners
    
    
    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
    
}
