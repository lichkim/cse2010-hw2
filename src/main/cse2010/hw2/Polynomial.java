package cse2010.hw2;

/**
 * A Polynomial ADT
 * DO NOT MODIFY!!
 */
public interface Polynomial {
    /**
     * Returns the degree of this polynomial.
     * @return the degree of this polynomial
     */
    int degree();

    /**
     * Returns the number of terms in this polynomial.
     * @return the number of terms in this polynomial
     */
    int getTermCount();

    /**
     * Returns the coefficient of the term with the given exponent.
     * @param exponent the exponent
     * @return the coefficient of the term with the given exponent
     */
    int getCoefficient(int exponent);

    /**
     * Insert a new term into a given polynomial.
     * @param coef the coefficient
     * @param exponent the exponent
     */
    void addTerm(int coef, int exponent);

    /**
     * Returns a new polynomial that is the sum of this polynomial and the given polynomial.
     * @param rhs the given polynomial
     * @return a new polynomial that is the sum of this polynomial and the given polynomial
     */
    Polynomial add(Polynomial rhs);

    /**
     * Returns a new polynomial that is the product of this polynomial and the given polynomial.
     * @param rhs the given polynomial
     * @return a new polynomial that is the product of this polynomial and the given polynomial
     */
    Polynomial mult(Polynomial rhs);

    /**
     * Returns the value of this polynomial for the given value of x.
     * @param x the value of x
     * @return the value of this polynomial for the given value of x
     */
    double eval(double x);
}
