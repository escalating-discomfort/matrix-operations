package com.szhorvath;

import java.util.ArrayList;
import java.util.List;

/**
 * The Matrix class encapsulates a list of vectors making up a matrix, and allows the execution of various matrix
 * operations.
 */
public class Matrix {
    private List<Vector> rows;

    /**
     * Empty constructor. Initializes the object with an empty list of vectors.
     */
    public Matrix() {
        rows = new ArrayList<>();
    }

    /**
     * Constructor to hard copy vectors from a given list into the object to initialize the object.
     * Will create an empty matrix if the given list contains vectors of different sizes.
     * @param rows Vector list to copy from.
     */
    public Matrix(List<Vector> rows) {
        this.rows = new ArrayList<>();
        int firstRowSize = rows.get(0).getSize();
        for (int i = 1; i < rows.size(); ++i) {
            if (rows.get(i).getSize() != firstRowSize) {
                return;
            }
        }
        for (Vector v : rows) {
            this.rows.add(new Vector(v));
        }
    }

    public int getRowCount() {
        return rows.size();
    }

    public int getColumnCount() {
        return rows.get(0).getSize();
    }

    public boolean isSquareMatrix() {
        if (getRowCount() > 0) {
            return getRowCount() == getColumnCount();
        } else {
            return false;
        }
    }

    /**
     * Adds a new vector to the matrix.
     * Ineffective if the vector to be added contains more or less elements than the matrix's first row (therefore
     * all rows).
     * @param v Vector to be added.
     */
    public void add(Vector v) {
        if (rows.size() > 0) {
            if (v.getSize() == rows.get(0).getSize()) {
                rows.add(new Vector(v));
            }
        } else {
            rows.add(new Vector(v));
        }
    }

    /**
     * Adds vectors in a list to the matrix.
     * Ineffective if even one vector isn't equal in length to the matrix's rows.
     * @param rows List containing vectors to be added.
     */
    public void add(List<Vector> rows) {
        int firstRowSize = rows.get(0).getSize();
        for (Vector row : rows) {
            if (row.getSize() != firstRowSize) {
                return;
            }
        }
        this.rows.addAll(rows);
    }

    public void removeIndex(int index) {
        rows.remove(index);
    }

    public void removeFromTo(int indexStart, int indexEnd) {
        for (int i = indexStart; i <= indexEnd; ++i) {
            rows.remove(i--);
            --indexEnd;
        }
    }

    /**
     * Matrix addition. Adds a given matrix to this matrix.
     * The matrices have to have the same dimensions. (NxM + NxM)
     * @param m Matrix to add to this matrix.
     * @return Returns a new matrix which is the sum of the two.
     * @throws MatrixSizeMismatchException Throws MatrixSizeMismatchException if the two matrices have different
     * dimensions.
     */
    public Matrix addMatrix(Matrix m) throws MatrixSizeMismatchException {
        if ((rows.size() == m.rows.size()) && (rows.get(0).getSize() == m.rows.get(0).getSize())) {
            Matrix result = new Matrix();
            for (int i = 0; i < rows.size(); ++i) {
                Vector temp = new Vector();
                for (int j = 0; j < rows.get(i).getSize(); ++j) {
                    temp.add(rows.get(i).getValues().get(j) + m.rows.get(i).getValues().get(j));
                }
                result.add(temp);
            }

            return result;
        } else {
            throw new MatrixSizeMismatchException("Addition of different sized matrices.");
        }
    }

    /**
     * Matrix subtraction. Subtracts a given matrix from this matrix.
     * The matrices have to have the same dimensions. (NxM - NxM)
     * @param m Matrix to subtract from this matrix.
     * @return Returns a new matrix which is the subtraction of the two.
     * @throws MatrixSizeMismatchException Throws MatrixSizeMismatchException if the two matrices have different
     * dimensions.
     */
    public Matrix subtractMatrix(Matrix m) throws MatrixSizeMismatchException {
        if ((rows.size() == m.rows.size()) && (rows.get(0).getSize() == m.rows.get(0).getSize())) {
            Matrix result = new Matrix();
            for (int i = 0; i < rows.size(); ++i) {
                Vector temp = new Vector();
                for (int j = 0; j < rows.get(i).getSize(); ++j) {
                    temp.add(rows.get(i).getValues().get(j) - m.rows.get(i).getValues().get(j));
                }
                result.add(temp);
            }

            return result;
        } else {
            throw new MatrixSizeMismatchException("Subtraction of different sized matrices.");
        }
    }

    /**
     * Matrix multiplication. Multiplies this matrix by a given matrix.
     * This matrix's column count has to match the given matrix's row count. (NxM * MxB)
     * @param m Matrix to multiply this matrix by.
     * @return Returns a new matrix which is the multiplication of this matrix and the given matrix.
     * @throws MatrixSizeMismatchException Throws MatrixSizeMismatchException if the first matrix's column count isn't
     * equal to the second matrix's row count.
     */
    public Matrix multiplyMatrix(Matrix m) throws MatrixSizeMismatchException {
        if (this.getColumnCount() == m.getRowCount()) {
            Matrix result = new Matrix();
            for (int i = 0; i < this.getRowCount(); ++i) {
                Vector temp = new Vector();
                for (int j = 0; j < m.getColumnCount(); ++j) {
                    double dotProduct = 0.0;
                    for (int k = 0; k < m.getRowCount(); ++k) {
                        dotProduct += rows.get(i).getValues().get(k) * m.rows.get(k).getValues().get(j);  // Dot product
                    }                                                                                     // of a row of
                    temp.add(dotProduct);                                                                 // the first
                }                                                                                         // and column
                result.add(temp);                                                                         // of the
            }                                                                                             // second
            // matrix.
            return result;
        } else {
            throw new MatrixSizeMismatchException("Multiplication of matrices where the first matrix's column count isn't equal to the second's row count.");
        }
    }

    public Matrix scalarProduct(double scalar) {
        Matrix result = new Matrix();
        for (Vector row : rows) {
            result.add(new Vector(row.scalarProduct(scalar).getValues()));
        }

        return result;
    }

    /**
     * Computes the determinant of this matrix.
     * The matrix has to be a square matrix. (NxN)
     * @return Returns a real value which is the determinant of this matrix.
     * @throws MatrixSizeMismatchException Throws MatrixSizeMismatchException if the matrix is empty, or is a non-square
     * matrix.
     */
    public double determinant() throws MatrixSizeMismatchException {
        if (isSquareMatrix()) {
            if (getRowCount() == 1) {  // 1x1 matrix: determinant is the only value contained.
                return rows.get(0).getValues().get(0);
            } else if (getRowCount() == 2) {  // 2x2 matrix: determinant is top-left-to-bottom-right diagonal product -
                // top-right-to-bottom-left diagonal product.
                return (rows.get(0).getValues().get(0) * rows.get(1).getValues().get(1)) -
                        (rows.get(0).getValues().get(1) * rows.get(1).getValues().get(0));
            } else {  // NxN matrix: produce lower square matrices, recursively compute their determinants, then add
                // them together with alternating signs (even is positive, odd is negative).
                List<Matrix> lowerSquareMatrices = new ArrayList<>();
                for (int i = 0; i < getColumnCount(); ++i) {
                    Matrix tempMatrix = new Matrix();
                    for (int j = 1; j < getRowCount(); ++j) {
                        Vector tempVector = new Vector();
                        for (int k = 0; k < getColumnCount(); ++k) {
                            if (k != i) {
                                tempVector.add(rows.get(j).getValues().get(k));
                            }
                        }
                        tempMatrix.add(tempVector);
                    }
                    lowerSquareMatrices.add(tempMatrix);
                }

                boolean odd = true;
                double result = 0.0;
                for (int i = 0; i < getColumnCount(); ++i) {
                    if (odd) {
                        result += rows.get(0).getValues().get(i) * lowerSquareMatrices.get(i).determinant();
                        odd = false;
                    } else {
                        result -= rows.get(0).getValues().get(i) * lowerSquareMatrices.get(i).determinant();
                        odd = true;
                    }
                }

                return result;
            }
        } else {
            throw new MatrixSizeMismatchException("Determinant of non-square or empty matrix.");
        }
    }

    /**
     * Computes the transpose of this matrix.
     * @return Returns a new matrix whose rows are this matrix's columns.
     */
    public Matrix transpose() {
        Matrix result = new Matrix();
        for (int i = 0; i < getColumnCount(); ++i) {
            Vector temp = new Vector();
            for (int j = 0; j < getRowCount(); ++j) {
                temp.add(rows.get(j).getValues().get(i));
            }
            result.add(temp);
        }

        return result;
    }

    /**
     * Computes the inverse of this matrix.
     * The matrix's determinant has to be non-zero, which implies it has to be a non-empty square matrix.
     * @return Returns a new matrix which is the inverse of this matrix.
     * @throws MatrixOperationException Throws MatrixOperationException if this matrix's determinant is zero.
     * Additionally, the determinant computation might throw MatrixSizeMismatchException if the matrix is either empty,
     * or a non-square matrix.
     */
    public Matrix inverse() throws MatrixOperationException {
        if (this.determinant() != 0.0) {
            List<Matrix> minorMatrices = new ArrayList<>(); // First, produce new minor matrices, equal in number to
            // this matrix's size (sum of elements).
            // Where this matrix is NxN, its minor matrices are N-1xN-1,
            // each missing a row and a column of the original.
            for (int i = 0; i < getRowCount() * getColumnCount(); ++i) {
                minorMatrices.add(new Matrix(this.rows));
                minorMatrices.get(i).rows.remove(i / getColumnCount());
                for (int j = 0; j < minorMatrices.get(i).getRowCount(); ++j) {
                    minorMatrices.get(i).rows.get(j).getValues().remove(i % getColumnCount());
                }
            }

            Matrix adjugateMatrix = new Matrix();  // Secondly, produce the adjugate matrix. The adjugate matrix
            // contains the determinants of each minor matrix, positions
            // corresponding to which element of the original matrix was used to
            // remove the row and column it was in. It is then transposed, and
            // its elements are multiplied by 1 with alternating signs. (Sum of
            // coordinates is even means positive, otherwise negative.)
            Vector temp = new Vector();
            for (int i = 0; i < getRowCount() * getColumnCount(); ++i) {
                temp.add(minorMatrices.get(i).determinant());
                if (temp.getSize() == getColumnCount()) {
                    adjugateMatrix.add(temp);
                    temp = new Vector();
                }
            }
            adjugateMatrix = adjugateMatrix.transpose();
            for (int i = 0; i < adjugateMatrix.getRowCount(); ++i) {
                for (int j = 0; j < adjugateMatrix.getColumnCount(); ++j) {
                    if ((i + j) % 2 == 1) {
                        adjugateMatrix.rows.get(i).getValues().set(j, -adjugateMatrix.rows.get(i).getValues().get(j));
                    }
                }
            }

            return adjugateMatrix.scalarProduct(1.0 / this.determinant());  // Lastly, the inverse matrix is produced by
            // the scalar product of the adjugate matrix
            // and 1 / determinant.
        } else {
            throw new MatrixOperationException("Inverse of zero determinant matrix.");
        }
    }

    /**
     * Performs Cholesky decomposition on this matrix.
     * @return Returns a new matrix which is L in the following formula: A = L * LT, where A is this matrix, and LT is
     * the transpose of L.
     * @throws MatrixOperationException Throws MatrixOperationException if the matrix is non-symmetrical, or isn't
     * positive semi-definite.
     */
    public Matrix choleskyDecomposition() throws MatrixOperationException {
        if (this.transpose().equals(this)) {
            Matrix result = new Matrix();
            Vector v = new Vector();
            for (int i = 0; i < this.getColumnCount(); ++i) {
                v.add(0.0);
            }
            for (int i = 0; i < this.getRowCount(); ++i) {
                result.add(v);
            }
            for (int i = 0; i < this.getRowCount(); ++i) {
                for (int j = 0; j <= i; ++j) {
                    double sum = 0.0;
                    for (int k = 0; k < j; ++k) {
                        sum += result.rows.get(i).getValues().get(k) * result.rows.get(j).getValues().get(k);
                    }
                    if (i == j) {
                        result.rows.get(j).getValues().set(j, Math.sqrt(this.rows.get(j).getValues().get(j) - sum));
                    } else {
                        result.rows.get(i).getValues().set(j, (1.0 / result.rows.get(j).getValues().get(j)) *
                                (this.rows.get(i).getValues().get(j) - sum));
                    }
                }
                if (result.rows.get(i).getValues().get(i) <= 0.0) {
                    throw new MatrixOperationException("Cholesky decomposition of non-positive semi-definite matrix.");
                }
            }

            return result;
        } else {
            throw new MatrixOperationException("Cholesky decomposition of non-symmetrical matrix.");
        }
    }

    @Override
    public boolean equals(Object o) {
        double delta = 0.00001;

        if (o == this) {
            return true;
        }

        if (!(o instanceof Matrix)) {
            return false;
        }

        Matrix m = (Matrix) o;
        if ((this.getRowCount() != m.getRowCount()) || (this.getColumnCount() != m.getColumnCount())) {
            return false;
        }
        for (int i = 0; i < m.getRowCount(); ++i) {
            for (int j = 0; j < m.getColumnCount(); ++j) {
                if (Math.abs(this.rows.get(i).getValues().get(j) - m.rows.get(i).getValues().get(j)) >= delta) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder();
        for (Vector row : rows) {
            strb.append(row.toString());
            strb.append("\n");
        }

        return strb.toString();
    }
}
