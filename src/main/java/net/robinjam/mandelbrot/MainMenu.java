package net.robinjam.mandelbrot;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Observable;
import java.util.Observer;
import net.robinjam.mandelbrot.render.NormalColouring;
import net.robinjam.mandelbrot.render.SmoothColouring;

/**
 * The main menu for the application.
 * 
 * @author James Robinson
 */
public class MainMenu extends MenuBar implements Observer {
    
	private static final long serialVersionUID = 1L;
	
    Menu favourites = new Menu("Favourites");
    
    public MainMenu(final Callback callback) {
        // Max. Iterations menu
        Menu maxIterations = new Menu("Max. Iterations");
        NumberFormat f = NumberFormat.getInstance();
        for (final int i : new int[] { 10, 50, 100, 200, 500, 1000, 5000, 10000, 100000 }) {
            MenuItem item = new MenuItem(f.format(i));
            item.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    RenderSettings.getInstance().setMaxIterations(i);
                }
            
            });
            maxIterations.add(item);
        }
        
        // View menu
        Menu view = new Menu("View");
        MenuItem mandelbrot = new MenuItem("Mandelbrot Set");
        mandelbrot.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                callback.changeFractal(Callback.FractalType.MANDELBROT);
            }
        
        });
        MenuItem burningShip = new MenuItem("Burning Ship Fractal");
        burningShip.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                callback.changeFractal(Callback.FractalType.BURNING_SHIP);
            }
        
        });
        MenuItem tricorn = new MenuItem("Tricorn Fractal");
        tricorn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                callback.changeFractal(Callback.FractalType.TRICORN);
            }
        
        });
        MenuItem normalColouring = new MenuItem("Normal colouring");
        normalColouring.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                RenderSettings.getInstance().setColourScheme(new NormalColouring());
            }
        
        });
        MenuItem smoothColouring = new MenuItem("Smooth colouring");
        smoothColouring.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                RenderSettings.getInstance().setColourScheme(new SmoothColouring());
            }
        
        });
        MenuItem resetZoom = new MenuItem("Reset Zoom");
        resetZoom.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                callback.resetZoom();
            }
        
        });
        view.add(mandelbrot);
        view.add(burningShip);
        view.add(tricorn);
        view.addSeparator();
        view.add(normalColouring);
        view.add(smoothColouring);
        view.addSeparator();
        view.add(resetZoom);
        
        add(maxIterations);
        add(view);
        
        Favourites.getInstance().addObserver(this);
    }

    @Override
    public void update(Observable o, Object o1) {
        favourites.removeAll();
        for (final Complex c : Favourites.getInstance().getFavourites()) {
            MenuItem item = new MenuItem(c.toString());
            item.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    new JuliaWindow((Window) getParent(), c);
                }
            
            });
            favourites.add(item);
            add(favourites);
        }
    }
    
    /**
     * Defines an interface that objects must implement in order to be notified when menu items are selected.
     */
    public static interface Callback {
        
        /**
         * Called when the user requests the zoom level to be reset.
         */
        public void resetZoom();
        
        public void changeFractal(FractalType type);
        
        public enum FractalType { MANDELBROT, BURNING_SHIP, TRICORN }
        
    }
    
}
