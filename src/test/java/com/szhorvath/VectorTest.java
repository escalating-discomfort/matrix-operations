package com.szhorvath;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class VectorTest {
    private Vector vector;

    @BeforeEach
    public void testSetup() {
        vector = new Vector();
    }

    @Test
    @DisplayName("Vector::addVector exception test")
    public void testAddVectorException() {
        vector.add(15.0);
        vector.add(20.0);
        vector.add(25.0);
        List<Double> values = new ArrayList<>();
        values.add(5.0);
        values.add(10.0);
        Vector v = new Vector(values);

        Exception exception = assertThrows(VectorSizeMismatchException.class, () ->
                vector.addVector(v));
        assertEquals("Addition of different sized vectors.", exception.getMessage());
    }

    @RepeatedTest(10)
    @DisplayName("Vector::addVector repeated test")
    public void testAddVectorRepeated() {
        vector.add(15.0);
        vector.add(20.0);
        vector.add(25.0);
        Vector v = new Vector();
        v.add(5.0);
        v.add(10.0);
        v.add(15.0);
        Vector result = new Vector();
        result.add(20.0);
        result.add(30.0);
        result.add(40.0);

        assertEquals(result, vector.addVector(v), "Vector::addVector produced wrong result.\n");
    }

    @RepeatedTest(10)
    @DisplayName("Vector::addVector negative test")
    public void testAddVectorNegative() {
        vector.add(1.0);
        vector.add(2.0);
        Vector v = new Vector();
        v.add(-7.0);
        v.add(-11.0);
        Vector result = new Vector();
        result.add(-6.0);
        result.add(-5.0);

        assertEquals(result, vector.addVector(v), "Vector::addVector should produce wrong result.\n");
    }

    @Test
    @DisplayName("Vector::subtractVector exception test")
    public void testSubtractVectorException() {
        vector.add(0.0);
        Vector v = new Vector();
        v.add(1.0);
        v.add(2.0);

        Exception exception = assertThrows(VectorSizeMismatchException.class, () ->
                vector.subtractVector(v));
        assertEquals("Subtraction of different sized vectors.", exception.getMessage());
    }

    @Test
    @DisplayName("Vector::subtractVector test")
    public void testSubtractVector() {
        vector.add(-7.5);
        vector.add(0.0);
        vector.add(447.3);
        vector.add(-50.2);
        Vector v = new Vector();
        v.add(0.0);
        v.add(-77.3);
        v.add(591.8);
        v.add(-66123.1);
        Vector result = new Vector();
        result.add(-7.5);
        result.add(77.3);
        result.add(-144.5);
        result.add(66072.9);

        assertEquals(result, vector.subtractVector(v), "Vector::subtractVector produced wrong result.\n");
    }

    @RepeatedTest(10)
    @DisplayName("Vector::scalarProduct repeated test")
    public void testScalarProductRepeated() {
        vector.add(334.9);
        vector.add(0.8);
        vector.add(-25.0);
        vector.add(8214.7);
        vector.add(-2.6);
        double scalar = -79.3;
        Vector result = new Vector();
        result.add(-26557.57);
        result.add(-63.44);
        result.add(1982.5);
        result.add(-651425.71);
        result.add(206.18);

        assertEquals(result, vector.scalarProduct(scalar), "Vector::scalarProduct produced wrong result.\n");
    }

    @Test
    @DisplayName("Vector::dotProduct exception test")
    public void testDotProductException() {
        vector.add(0.0);
        Vector v = new Vector();
        v.add(1.0);
        v.add(2.0);

        Exception exception = assertThrows(VectorSizeMismatchException.class, () ->
                vector.dotProduct(v));
        assertEquals("Dot product of different sized vectors.", exception.getMessage());
    }

    @Test
    @DisplayName("Vector::dotProduct test")
    public void testDotProduct() {
        vector.add(76735.5);
        vector.add(-0.1);
        vector.add(44.9);
        vector.add(-504.9);
        vector.add(-5803.1);
        Vector v = new Vector();
        v.add(0.0);
        v.add(-7.1);
        v.add(50145.8);
        v.add(123.4);
        v.add(0.0);
        double result = 2189242.47;

        assertEquals(result, vector.dotProduct(v), 0.00001, "Vector::dotProduct produced wrong result.\n");
    }
}
