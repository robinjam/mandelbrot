package net.robinjam.mandelbrot;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import net.robinjam.mandelbrot.compute.MandelbrotWorker;

/**
 * The main window for the GUI, and the entry point for the application.
 *
 * @author James Robinson
 */
public class MainWindow extends JFrame implements MouseListener, MainMenu.Callback {
    
	private static final long serialVersionUID = 1L;
	
    FractalPanel mandelbrotPanel = new FractalPanel(800, 640, MandelbrotWorker.getFactory());

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
        final Complex c = mandelbrotPanel.getPixel(me.getX(), me.getY());
        new JuliaWindow(this, c);
    }
    
    @Override
    public void resetZoom() {
        mandelbrotPanel.resetZoom();
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

    @Override
    public void changeFractal(FractalType type) {
        mandelbrotPanel.changeFractal(type);
    }
    
}
