package net.robinjam.mandelbrot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SelectionListener implements MouseListener, MouseMotionListener {
    
    private Rectangle selection;
    private Callback callback;
    
    public SelectionListener(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void mousePressed(MouseEvent me) {
        selection = new Rectangle(me.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (hasSelection()) {
            callback.selected(selection);
            selection = null;
        }
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        // TODO: Allow the user to select in any direction
        selection.setSize(me.getX() - selection.x, me.getY() - selection.y);
        callback.moved();
    }
    
    public void paint(Graphics2D g) {
        if (hasSelection()) {
            g.setColor(new Color(0.0f, 0.2f, 0.8f, 0.5f));
            g.fill(selection);
            g.setColor(new Color(0.0f, 0.2f, 0.8f, 0.8f));
            g.draw(selection);
        }
    }
    
    public boolean hasSelection() {
        return selection != null && (selection.getSize().width > 0 || selection.getSize().height > 0);
    }
    
    public static interface Callback {

        public void moved();
        public void selected(Rectangle selection);
        
    }
    
    // Unused listeners
    
    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

    @Override
    public void mouseMoved(MouseEvent me) {}
    
}
