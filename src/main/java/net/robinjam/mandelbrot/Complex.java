package net.robinjam.mandelbrot;

public class Complex {
    
    private double re, im;
    
    public Complex() {
        this(0, 0);
    }
    
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }
    
    public double getRe() {
        return re;
    }
    
    public double getIm() {
        return im;
    }
    
    public void setRe(double re) {
        this.re = re;
    }
    
    public void setIm(double im) {
        this.im = im;
    }
    
    public void square() {
        double new_re = re * re - im * im;
        im = 2 * re * this.im;
        re = new_re;
    }
    
    public double modulusSquared() {
        return re * re + im * im;
    }
    
    public double modulus() {
        return Math.sqrt(modulusSquared());
    }
    
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
