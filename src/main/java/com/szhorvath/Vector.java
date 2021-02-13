package com.szhorvath;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The Vector class contains a list of real values which make up a vector, and allows several operations to be executed
 * on it.
 */
public class Vector {
    private List<Double> values;

    /**
     * Empty constructor. Initializes the object with an empty list of doubles.
     */
    public Vector() {
        values = new ArrayList<>();
    }

    /**
     * Constructor to hard copy values from a list of doubles to the object.
     * @param values List of double values to copy from.
     */
    public Vector(List<Double> values) {
        this.values = new ArrayList<>();
        this.values.addAll(values);
    }

    public Vector(Vector v) {
        this(v.getValues());
    }

    public List<Double> getValues() {
        return values;
    }

    public int getSize() {
        return values.size();
    }

    public void add(double value) {
        values.add(value);
    }

    public void add(List<Double> values) {
        this.values.addAll(values);
    }

    /**
     * Removes the first value matching the given parameter from this vector.
     * @param value Value to match and remove.
     */
    public void removeFirst(Double value) {
        for (int i = 0; i < values.size(); ++i) {
            if (values.get(i).equals(value)) {
                values.remove(i);

                return;
            }
        }
    }

    public void removeIndex(int index) {
        values.remove(index);
    }

    /**
     * Removes all values matching the given parameter from this vector.
     * @param value Value to match and remove.
     */
    public void removeAllMatching(Double value) {
        values.removeIf(val -> val.equals(value));
    }

    public void removeAll() {
        values.clear();
    }

    public void removeFromTo(int indexStart, int indexEnd) {
        for (int i = indexStart; i <= indexEnd; ++i) {
            values.remove(i--);
            --indexEnd;
        }
    }

    /**
     * Performs addition on this vector and the given vector.
     * The two vectors must contain the same amount of elements.
     * @param v Vector to add to this vector.
     * @return Returns a new vector which is the sum of the two.
     * @throws VectorSizeMismatchException Throws VectorSizeMismatchException if the two vectors have different sizes.
     */
    public Vector addVector(Vector v) throws VectorSizeMismatchException {
        if (values.size() == v.values.size()) {
            Vector result = new Vector();
            for (int i = 0; i < values.size(); ++i) {
                result.add(values.get(i) + v.values.get(i));
            }

            return result;
        } else {
            throw new VectorSizeMismatchException("Addition of different sized vectors.");
        }
    }

    /**
     * Subtracts the given vector from this vector.
     * The two vectors must contain the same amount of elements.
     * @param v Vector to subtract from this vector.
     * @return Returns a new vector which is the result of the subtraction.
     * @throws VectorSizeMismatchException Throws VectorSizeMismatchException if the two vectors have different sizes.
     */
    public Vector subtractVector(Vector v) throws VectorSizeMismatchException {
        if (values.size() == v.values.size()) {
            Vector result = new Vector();
            for (int i = 0; i < values.size(); ++i) {
                result.add(values.get(i) - v.values.get(i));
            }

            return result;
        } else {
            throw new VectorSizeMismatchException("Subtraction of different sized vectors.");
        }
    }

    public Vector scalarProduct(Double scalar) {
        Vector result = new Vector();
        for (Double i : values) {
            result.add(scalar * i);
        }

        return result;
    }

    /**
     * Computes the dot product of this vector and a given vector.
     * The two vectors must contain the same amount of elements.
     * @param v Vector to compute the dot product with.
     * @return Returns a real value representing the two vector's dot product.
     * @throws VectorSizeMismatchException Throws VectorSizeMismatchException if the two vectors have different sizes.
     */
    public Double dotProduct(Vector v) throws VectorSizeMismatchException {
        if (values.size() == v.values.size()) {
            double result = 0.0;
            for (int i = 0; i < values.size(); ++i) {
                result += values.get(i) * v.values.get(i);
            }

            return result;
        } else {
            throw new VectorSizeMismatchException("Dot product of different sized vectors.");
        }
    }

    @Override
    public boolean equals(Object o) {
        double delta = 0.00001;

        if (o == this) {
            return true;
        }

        if (!(o instanceof Vector)) {
            return false;
        }

        Vector v = (Vector) o;
        if (this.getSize() != v.getSize()) {
            return false;
        }
        for (int i = 0; i < v.getSize(); ++i) {
            if (Math.abs(this.values.get(i) - v.values.get(i)) >= delta) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        if (values != null) {
            NumberFormat df = DecimalFormat.getInstance();
            df.setMinimumFractionDigits(4);
            df.setMaximumFractionDigits(4);
            df.setRoundingMode(RoundingMode.HALF_UP);

            StringBuilder strb = new StringBuilder("( ");
            for (int i = 0; i < values.size(); ++i) {
                strb.append(df.format(values.get(i)));
                if (i + 1 != values.size()) {
                    strb.append(" , ");
                } else {
                    strb.append(" )");
                }
            }
            return strb.toString();
        } else {
            return null;
        }
    }
}
