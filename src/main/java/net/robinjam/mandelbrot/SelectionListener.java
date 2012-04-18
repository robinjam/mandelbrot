package net.robinjam.mandelbrot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SelectionListener implements MouseListener, MouseMotionListener {
    
    private Point start;
    private Point end;
    private Callback callback;
    
    public SelectionListener(Callback callback) {
        this.callback = callback;
    }
    
    public void mouseClicked(MouseEvent me) {
    }

    public void mousePressed(MouseEvent me) {
        start = me.getPoint();
        end = me.getPoint();
    }

    public void mouseReleased(MouseEvent me) {
        int x1 = start.x;
        int x2 = end.x;
        int y1 = start.y;
        int y2 = end.y;
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int width = Math.abs(x1 - x2);
        int height = Math.abs(y1 - y2);
        callback.selected(new Point(x, y), new Point(x + width, y + height));
        start = null;
        end = null;
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    public void mouseDragged(MouseEvent me) {
        if (start != null) {
            end = me.getPoint();
            callback.moved();
        }
    }

    public void mouseMoved(MouseEvent me) {
    }
    
    public void paint(Graphics g) {
        if (start != null && end != null) {
            g.setColor(new Color(0.0f, 0.2f, 0.8f, 0.5f));
            int x1 = start.x;
            int x2 = end.x;
            int y1 = start.y;
            int y2 = end.y;
            int x = Math.min(x1, x2);
            int y = Math.min(y1, y2);
            int width = Math.abs(x1 - x2);
            int height = Math.abs(y1 - y2);
            g.fillRect(x, y, width, height);
            g.setColor(new Color(0.0f, 0.2f, 0.8f, 0.8f));
            g.drawRect(x, y, width, height);
        }
    }
    
    public static interface Callback {

        public void moved();
        public void selected(Point lower, Point upper);
        
    }
    
}
