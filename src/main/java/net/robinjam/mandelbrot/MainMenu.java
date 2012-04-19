package net.robinjam.mandelbrot;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

/**
 * The main menu for the application.
 * 
 * @author James Robinson
 */
public class MainMenu extends MenuBar {
    
    public MainMenu(final Callback callback) {
        Menu maxIterations = new Menu("Max. Iterations");
        NumberFormat f = NumberFormat.getInstance();
        for (final int i : new int[] { 10, 50, 100, 200, 500, 1000, 5000, 10000, 100000 }) {
            MenuItem item = new MenuItem(f.format(i));
            item.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    callback.maxIterationsChanged(i);
                }
            
            });
            maxIterations.add(item);
        }
        
        add(maxIterations);
    }
    
    /**
     * Defines an interface that objects must implement in order to be notified when menu items are selected.
     */
    public static interface Callback {
        
        /**
         * Called when the user changes the maximum number of iterations.
         */
        public void maxIterationsChanged(int max_iterations);
        
    }
    
}
