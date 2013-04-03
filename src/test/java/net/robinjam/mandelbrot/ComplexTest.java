package net.robinjam.mandelbrot;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ComplexTest {

	@Test
	public void testSquare() {
		Complex instance = new Complex(1, 2);
		instance.square();
		assertEquals(-3, instance.getRe(), 0.1);
		assertEquals(4, instance.getIm(), 0.0001);
	}

	@Test
	public void testModulusSquared() {
		double result = new Complex(1, 2).modulusSquared();
		assertEquals(5, result, 0.0001);
	}

	@Test
	public void testModulus() {
		double result = new Complex(1, 2).modulus();
		assertEquals(Math.sqrt(5), result, 0.0001);
	}

	@Test
	public void testAdd() {
		Complex instance = new Complex(1, 2);
		instance.add(new Complex(3, 4));
		assertEquals(4, instance.getRe(), 0.0001);
		assertEquals(6, instance.getIm(), 0.0001);
	}
}
