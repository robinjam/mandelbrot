package net.robinjam.mandelbrot;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Handles storing the user's favourite Julia sets, for display in the Favourites menu.
 * 
 * @author James Robinson
 */
class Favourites extends Observable {

	private static Favourites instance;

	private List<Complex> favourites = new ArrayList<Complex>();

	// Prevent instantiation of this class
	private Favourites() {
	}

	/**
	 * @return The singleton instance of this class.
	 */
	public static Favourites getInstance() {
		if (instance == null)
			instance = new Favourites();
		return instance;
	}

	/**
	 * Adds the Julia set for the given Complex number to the user's favourites.
	 * 
	 * @param c The Complex number for the Julia set to add.
	 */
	public void add(Complex c) {
		favourites.add(c);
		setChanged();
		notifyObservers();
	}

	/**
	 * @return A List containing the Complex numbers for each Julia set in the user's favourites.
	 */
	public List<Complex> getFavourites() {
		return favourites;
	}

}
