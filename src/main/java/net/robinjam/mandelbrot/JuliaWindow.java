package net.robinjam.mandelbrot;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import net.robinjam.mandelbrot.compute.JuliaWorker;

public class JuliaWindow extends JDialog {
    
	private static final long serialVersionUID = 1L;

    public JuliaWindow(Window parent, final Complex c) {
        super(parent, "Julia Set for point " + c);
        setLayout(new BorderLayout());
        FractalPanel juliaPanel = new FractalPanel(400, 320, JuliaWorker.getFactory(c));
        add(juliaPanel, BorderLayout.CENTER);
        JButton favouritesButton = new JButton("Add to favourites");
        favouritesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Favourites.getInstance().add(c);
            }
        
        });
        add(favouritesButton, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
    }
    
}
