package net.robinjam.mandelbrot;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

class Favourites extends Observable {
    
    private static Favourites instance;
    private List<Complex> favourites = new ArrayList<Complex>();
    
    private Favourites() {}
    
    public static Favourites getInstance() {
        if (instance == null)
            instance = new Favourites();
        return instance;
    }
    
    public void add(Complex c) {
        favourites.add(c);
        setChanged();
        notifyObservers();
    }
    
    public List<Complex> getFavourites() {
        return favourites;
    }
    
}
