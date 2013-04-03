package net.robinjam.mandelbrot;

/**
 * A complex number.
 * 
 * @author James Robinson
 */
public class Complex {

	private double re, im;

	/**
	 * Creates a new instance with an initial value of 0.
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Creates a new instance with the given value.
	 * 
	 * @param re The real part of the number.
	 * @param im The imaginary part of the number.
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * @return The real part of this number.
	 */
	public double getRe() {
		return re;
	}

	/**
	 * @return The imaginary part of this number.
	 */
	public double getIm() {
		return im;
	}

	/**
	 * Sets the real part of this number to the given value.
	 */
	public void setRe(double re) {
		this.re = re;
	}

	/**
	 * Sets the imaginary part of this number to the given value.
	 */
	public void setIm(double im) {
		this.im = im;
	}

	/**
	 * Squares this number.
	 */
	public void square() {
		double temp = re * re - im * im;
		im = 2 * re * this.im;
		re = temp;
	}

	/**
	 * @return The square of the modulus of this number.
	 */
	public double modulusSquared() {
		return re * re + im * im;
	}

	/**
	 * Computes the modulus of this number. Note: For performance reasons, please use {@link #modulusSquared()} whenever possible.
	 * 
	 * @return The modulus of this number.
	 */
	public double modulus() {
		return Math.sqrt(modulusSquared());
	}

	/**
	 * Adds the given number to this number.
	 * 
	 * @param other
	 */
	public void add(final Complex other) {
		re += other.re;
		im += other.im;
	}

	@Override
	public String toString() {
		if (im > 0)
			return re + " + " + im + "i";
		else
			return re + " - " + -im + "i";
	}

}
