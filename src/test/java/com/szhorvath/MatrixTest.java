package com.szhorvath;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MatrixTest {
    private Matrix matrix;

    @BeforeEach
    public void testSetup() {
        List<Vector> rows = new ArrayList<>();
        Vector v = new Vector();
        v.add(1.0);
        v.add(66.2);
        v.add(-0.7);
        v.add(84566.1);
        rows.add(v);
        v = new Vector();
        v.add(-851.4);
        v.add(3.3);
        v.add(75.1);
        v.add(-0.9);
        rows.add(v);
        v = new Vector();
        v.add(567.8);
        v.add(-324748.0);
        v.add(0.0);
        v.add(1.6);
        rows.add(v);
        v = new Vector();
        v.add(0.0);
        v.add(0.5);
        v.add(7856.2);
        v.add(-61.0);
        rows.add(v);
        matrix = new Matrix(rows);
    }

    @Test
    @DisplayName("Matrix::addMatrix exception test")
    public void testAddMatrixException() {
        Vector v = new Vector();
        v.add(0.0);
        v.add(4365.1);
        v.add(1.0);
        Matrix m = new Matrix();
        m.add(v);

        Exception exception = assertThrows(MatrixSizeMismatchException.class, () ->
                matrix.addMatrix(m));
        assertEquals("Addition of different sized matrices.", exception.getMessage());
    }

    @Test
    @DisplayName("Matrix::addMatrix negative test")
    public void testAddMatrixNegative() {
        Vector v = new Vector();
        v.add(0.0);
        v.add(49.1);
        v.add(160.0);
        v.add(6.0);
        Matrix m = new Matrix();
        m.add(v);
        v = new Vector();
        v.add(0.0);
        v.add(0.0);
        v.add(1.0);
        v.add(-77.2);
        m.add(v);
        v = new Vector();
        v.add(547.1);
        v.add(3461.0);
        v.add(-0.8);
        v.add(34612.1);
        m.add(v);
        v = new Vector();
        v.add(0.0);
        v.add(1.0);
        v.add(-1.0);
        v.add(-0.2);
        m.add(v);
        Matrix result = new Matrix();
        v = new Vector();
        v.add(0.0);
        m.add(v);

        assertEquals(result, matrix.addMatrix(m), "Matrix::addMatrix should produce wrong result.\n");
    }

    @Test
    @DisplayName("Matrix::subtractMatrix exception test")
    public void testSubtractMatrixException() {
        Vector v = new Vector();
        v.add(0.0);
        v.add(0.1);
        v.add(-1.0);
        v.add(-0.1);
        Matrix m = new Matrix();
        m.add(v);

        Exception exception = assertThrows(MatrixSizeMismatchException.class, () ->
                matrix.subtractMatrix(m));
        assertEquals("Subtraction of different sized matrices.", exception.getMessage());
    }

    @Test
    @DisplayName("Matrix::multiplyMatrix exception test")
    public void testMultiplyMatrixException() {
        Vector v = new Vector();
        v.add(0.0);
        v.add(456.9);
        v.add(-11.2);
        v.add(0.0);
        Matrix m = new Matrix();
        m.add(v);
        v = new Vector();
        v.add(12.0);
        v.add(4521.9);
        v.add(-0.2);
        v.add(-34.3);
        m.add(v);

        Exception exception = assertThrows(MatrixSizeMismatchException.class, () ->
                matrix.multiplyMatrix(m));
        assertEquals("Multiplication of matrices where the first matrix's column count isn't equal to the second's row count.", exception.getMessage());
    }

    @Test
    @DisplayName("Matrix::multiplyMatrix test")
    public void testMultiplyMatrix() {
        Vector v = new Vector();
        v.add(0.0);
        v.add(1.0);
        Matrix m = new Matrix();
        m.add(v);
        m.add(v);
        m.add(v);
        m.add(v);
        Matrix result = new Matrix();
        v = new Vector();
        v.add(0.0);
        v.add(84632.6);
        result.add(v);
        v.getValues().set(1, -773.9);
        result.add(v);
        v.getValues().set(1, -324178.6);
        result.add(v);
        v.getValues().set(1, 7795.7);
        result.add(v);

        assertEquals(result, matrix.multiplyMatrix(m), "Matrix::addMatrix produced wrong result.\n");
    }

    @RepeatedTest(10)
    @DisplayName("Matrix::scalarProduct repeated test")
    public void testScalarProductRepeated() {
        Vector v = new Vector();
        v.add(0.0);
        v.add(0.0);
        v.add(0.0);
        v.add(0.0);
        Matrix result = new Matrix();
        result.add(v);
        result.add(v);
        result.add(v);
        result.add(v);

        assertEquals(result, matrix.scalarProduct(0.0), "Matrix::scalarProduct produced wrong result\n");
    }

    @Test
    @DisplayName("Matrix::determinant exception test")
    public void testDeterminantException() {
        Matrix m = new Matrix();
        Exception exception = assertThrows(MatrixSizeMismatchException.class, m::determinant);
        assertEquals("Determinant of non-square or empty matrix.", exception.getMessage());

        Vector v = new Vector();
        v.add(-0.1);
        v.add(4463.1);
        m.add(v);
        exception = assertThrows(MatrixSizeMismatchException.class, m::determinant);
        assertEquals("Determinant of non-square or empty matrix.", exception.getMessage());
    }

    @Test
    @DisplayName("Matrix::determinant test")
    public void testDeterminant() {
        assertEquals(-183690204241426566.2, matrix.determinant(), 0.00001, "Matrix::determinant produced wrong result.\n");
    }

    @RepeatedTest(10)
    @DisplayName("Matrix::transpose repeated test")
    public void testTransposeRepeated() {
        List<Vector> rows = new ArrayList<>();
        Vector v = new Vector();
        v.add(1.0);
        v.add(-851.4);
        v.add(567.8);
        v.add(0.0);
        rows.add(v);
        v = new Vector();
        v.add(66.2);
        v.add(3.3);
        v.add(-324748.0);
        v.add(0.5);
        rows.add(v);
        v = new Vector();
        v.add(-0.7);
        v.add(75.1);
        v.add(0.0);
        v.add(7856.2);
        rows.add(v);
        v = new Vector();
        v.add(84566.1);
        v.add(-0.9);
        v.add(1.6);
        v.add(-61.0);
        rows.add(v);
        Matrix result = new Matrix(rows);

        assertEquals(result, matrix.transpose(), "Matrix::transpose produced wrong result.\n");
    }

    @Test
    @DisplayName("Matrix::inverse exception test")
    public void testInverseException() {
        Matrix m = new Matrix();
        Vector v = new Vector();
        v.add(0.0);
        v.add(416.7);
        m.add(v);
        v = new Vector();
        v.add(0.0);
        v.add(-5.1);
        m.add(v);

        Exception exception = assertThrows(MatrixOperationException.class, m::inverse);
        assertEquals("Inverse of zero determinant matrix.", exception.getMessage());
    }

    @RepeatedTest(10)
    @DisplayName("Matrix::inverse repeated test")
    public void testInverseRepeated() {
        Vector v = new Vector();
        v.add(12.0);
        v.add(24.0);
        v.add(12.0);
        Matrix m = new Matrix();
        m.add(v);
        v = new Vector();
        v.add(0.0);
        v.add(6.0);
        v.add(3.0);
        m.add(v);
        v = new Vector();
        v.add(7.0);
        v.add(0.0);
        v.add(14.0);
        m.add(v);
        v = new Vector();
        v.add(0.0833333);
        v.add(-0.3333333);
        v.add(0.0);
        Matrix result = new Matrix();
        result.add(v);
        v = new Vector();
        v.add(0.0208333);
        v.add(0.0833333);
        v.add(-0.0357143);
        result.add(v);
        v = new Vector();
        v.add(-0.0416667);
        v.add(0.1666667);
        v.add(0.0714286);
        result.add(v);

        assertEquals(result, m.inverse(), "Matrix::inverse produced wrong result.\n");
    }

    @Test
    @DisplayName("Matrix::choleskyDecomposition exception test")
    public void testCholeskyDecompositionException() {
        Exception exception = assertThrows(MatrixOperationException.class, matrix::choleskyDecomposition);
        assertEquals("Cholesky decomposition of non-symmetrical matrix.", exception.getMessage());
    }

    @Test
    @DisplayName("Matrix::choleskyDecomposition test")
    public void testCholeskyDecomposition() {
        Vector v = new Vector();
        v.add(1.0);
        v.add(-1.0);
        v.add(1.0);
        Matrix m = new Matrix();
        m.add(v);
        v.getValues().set(0, -1.0);
        v.getValues().set(1, 2.0);
        v.getValues().set(2, -1.0);
        m.add(v);
        v.getValues().set(0, 1.0);
        v.getValues().set(1, -1.0);
        v.getValues().set(2, 5.0);
        m.add(v);
        v.getValues().set(0, 1.0);
        v.getValues().set(1, 0.0);
        v.getValues().set(2, 0.0);
        Matrix result = new Matrix();
        result.add(v);
        v.getValues().set(0, -1.0);
        v.getValues().set(1, 1.0);
        v.getValues().set(2, 0.0);
        result.add(v);
        v.getValues().set(0, 1.0);
        v.getValues().set(1, 0.0);
        v.getValues().set(2, 2.0);
        result.add(v);

        assertEquals(result, m.choleskyDecomposition(), "Matrix::choleskyDecomposition produced wrong result.\n");
    }
}
